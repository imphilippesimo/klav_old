package com.klav.service.ext;

import com.klav.config.Constants;
import com.klav.domain.Address;
import com.klav.domain.Authority;
import com.klav.domain.KlavUser;
import com.klav.domain.User;
import com.klav.repository.AuthorityRepository;
import com.klav.repository.UserRepository;
import com.klav.repository.ext.KlavUserRepositoryExtended;
import com.klav.security.AuthoritiesConstants;
import com.klav.security.SecurityUtils;
import com.klav.service.UserService;
import com.klav.service.dto.KlavUserDTO;
import com.klav.service.dto.UserDTO;
import com.klav.service.mapper.KlavUserMapper;
import com.klav.service.mapper.UserMapper;
import com.klav.service.util.RandomUtil;
import com.klav.web.rest.errors.EmailAlreadyUsedException;
import com.klav.web.rest.errors.LoginAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class KlavUserServiceExtended extends UserService {

    private final Logger log = LoggerFactory.getLogger(KlavUserServiceExtended.class);
    private final KlavUserRepositoryExtended klavUserRepositoryExtended;

    private static KlavUserMapper klavUserMapper;
    private static UserMapper userMapper;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;


    public KlavUserServiceExtended(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, CacheManager cacheManager, KlavUserRepositoryExtended klavUserRepository, KlavUserMapper klavUserMapper, UserRepository userRepository1, PasswordEncoder passwordEncoder1, AuthorityRepository authorityRepository1, CacheManager cacheManager1) {
        super(userRepository, passwordEncoder, authorityRepository, cacheManager);
        this.klavUserMapper = klavUserMapper;
        this.klavUserRepositoryExtended = klavUserRepository;
        this.userMapper = userMapper;

        this.userRepository = userRepository1;
        this.passwordEncoder = passwordEncoder1;
        this.authorityRepository = authorityRepository1;
        this.cacheManager = cacheManager1;
    }


    public Optional<KlavUser> activateKlavUserRegistration(String key) {
        Optional<User> user = super.activateRegistration(key);
        return klavUserRepositoryExtended.findDistinctByPersonId(user.get().getId());
    }


    public Optional<KlavUser> completePasswordResetForKlavUser(String newPassword, String key) {
        Optional<User> user = super.completePasswordReset(newPassword, key);
        return klavUserRepositoryExtended.findDistinctByPersonId(user.get().getId());

    }


    public Optional<KlavUser> requestPasswordResetForKlavUser(String mail) {
        Optional<User> user = super.requestPasswordReset(mail);
        return klavUserRepositoryExtended.findDistinctByPersonId(user.get().getId());
    }


    public KlavUser registerKlavUser(KlavUserDTO klavUserDTO, String password) {
        User registeredPerson = registerUser(klavUserDTO, password);
        UserDTO registeredPersonDTO = userMapper.userToUserDTO(registeredPerson);
        klavUserDTO.setPerson(registeredPersonDTO);
        final KlavUser klavUser = klavUserMapper.klavUserDTOToKlavUser(klavUserDTO);
        return klavUserRepositoryExtended.save(klavUser);

    }

    public User registerUser(KlavUserDTO klavUserDTO, String password) {

        UserDTO userDTO = klavUserDTO.getPerson();
        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new LoginAlreadyUsedException();
            }
        });


        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail().toLowerCase());
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.getActivated()) {
            return false;
        }
        klavUserRepositoryExtended.findDistinctByPersonLogin(existingUser.getLogin()).ifPresent(existingKlavUser -> {
            klavUserRepositoryExtended.delete(existingKlavUser);
            klavUserRepositoryExtended.flush();
        });
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }


    public KlavUser createUser(KlavUserDTO klavUserDTO) {
        super.createUser(klavUserDTO.getPerson());
        return klavUserRepositoryExtended.save(klavUserMapper.klavUserDTOToKlavUser(klavUserDTO));
    }


    /**
     * Update basic information (first name, last name, email, language) for the current user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey,String phoneNumber, Instant birthdate, String selfDescription, String gender, String nationality, Address address) {
        super.updateUser(firstName, lastName, email, langKey, null);
        SecurityUtils.getCurrentUserLogin()
            .flatMap(klavUserRepositoryExtended::findDistinctByPersonLogin)
            .ifPresent(klavUser -> {
                klavUser.birthdate(birthdate).gender(gender).phoneNumber(phoneNumber).nationality(nationality).livesAt(address).selfDescription(selfDescription);
                log.debug("Changed Information for Klav User: {}", klavUser);
            });
    }


    @Override
    public void deleteUser(String login) {

        super.deleteUser(login);

        klavUserRepositoryExtended.findDistinctByPersonLogin(login).ifPresent(user -> {
            klavUserRepositoryExtended.delete(user);
            log.debug("Deleted Klav User: {}", user);
        });
    }

    @Override
    public void changePassword(String currentClearTextPassword, String newPassword) {
        super.changePassword(currentClearTextPassword, newPassword);
    }

    @Transactional(readOnly = true)
    public Page<KlavUserDTO> getAllManagedKlavUsers(Pageable pageable) {

        return klavUserRepositoryExtended.findAllByPersonLoginNot(pageable, Constants.ANONYMOUS_USER).map(klavUser -> {
            return klavUserMapper.klavUserToKlavUserDTO(klavUser);
        });

    }

    public Optional<KlavUser> getKlavUserWithAuthoritiesByLogin(String login) {
        Optional<User> user = super.getUserWithAuthoritiesByLogin(login);
        return klavUserRepositoryExtended.findDistinctByPersonId(user.get().getId());

    }


    public Optional<KlavUser> getKlavUserWithAuthorities(Long id) {

        Optional<User> user = super.getUserWithAuthorities(id);
        return klavUserRepositoryExtended.findDistinctByPersonId(user.get().getId());
    }


    public Optional<KlavUser> getKlavUserWithAuthorities() {

        Optional<User> user = super.getUserWithAuthorities();
        return klavUserRepositoryExtended.findDistinctByPersonId(user.get().getId());
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        klavUserRepositoryExtended
            .findAllByPersonActivatedIsFalseAndPersonCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(klavUser -> {
                log.debug("Deleting not activated user {}", klavUser.getPerson().getLogin());
                klavUserRepositoryExtended.delete(klavUser);
            });
    }

    /**
     * @return a list of all the authorities
     */
    @Override
    public List<String> getAuthorities() {
        return super.getAuthorities();
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }
}
