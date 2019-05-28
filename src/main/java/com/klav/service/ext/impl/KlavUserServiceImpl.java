package com.klav.service.ext.impl;

import com.klav.domain.KlavUser;
import com.klav.repository.ext.impl.JPAKlavUserRepository;
import com.klav.service.dto.KlavUserDTO;
import com.klav.service.errors.KlavUserEmailAlreadyUsedException;
import com.klav.service.errors.KlavUserNotActivatedException;
import com.klav.service.errors.PhoneNumberAlreadyUsedException;
import com.klav.service.ext.IKlavUserService;
import com.klav.service.mapper.KlavUserMapper;
import com.klav.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class KlavUserServiceImpl implements IKlavUserService {

    private final Logger log = LoggerFactory.getLogger(KlavUserServiceImpl.class);

    private JPAKlavUserRepository klavUserRepository;

    private KlavUserMapper klavUserMapper;

    private PasswordEncoder passwordEncoder;

    public KlavUserServiceImpl(JPAKlavUserRepository klavUserRepository, KlavUserMapper klavUserMapper,
                               PasswordEncoder passwordEncoder) {
        this.klavUserRepository = klavUserRepository;
        this.klavUserMapper = klavUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public KlavUser createKlavUser(KlavUserDTO klavUserDTO)
        throws PhoneNumberAlreadyUsedException, KlavUserNotActivatedException, KlavUserEmailAlreadyUsedException {

        checkDuplicatedUserByEmail(klavUserDTO.getEmail());
        checkDuplicatedUserByPhoneNumber(klavUserDTO.getPhoneNumber());

        KlavUser klavUser = klavUserMapper.klavUserDTOToKlavUser(klavUserDTO);
        klavUser.setPassword(passwordEncoder.encode(klavUser.getPassword()));
        klavUser.setActivated(false);
        klavUser.setActivationKey(RandomUtil.generateActivationKey());
        klavUser.setResetKey(RandomUtil.generateResetKey());

        klavUserRepository.createUser(klavUser);

        return klavUser;
    }

    @Override
    public Optional<KlavUser> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return klavUserRepository.findOneByActivationKey(key)
            .map(user -> {
                user.setActivated(true);
                user.setActivationKey(null);
                klavUserRepository.updateUser(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    private void checkDuplicatedUserByPhoneNumber(String phoneNumber)
        throws KlavUserNotActivatedException, PhoneNumberAlreadyUsedException {
        Optional<KlavUser> user = klavUserRepository.findOneByPhoneNumber(phoneNumber);
        if (user.isPresent()) {
            if (!user.get().isActivated()) {
                throw new KlavUserNotActivatedException();
            }
            throw new PhoneNumberAlreadyUsedException();
        }
    }

    private void checkDuplicatedUserByEmail(String email)
        throws KlavUserNotActivatedException, KlavUserEmailAlreadyUsedException {
        Optional<KlavUser> user = klavUserRepository.findOneByEmailIgnoreCase(email);
        if (user.isPresent()) {
            if (!user.get().isActivated()) {
                throw new KlavUserNotActivatedException();
            }
            throw new KlavUserEmailAlreadyUsedException();
        }
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        klavUserRepository
            .findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(klavUser -> {
                log.debug("Deleting not activated user {}", klavUser.getLogin());
                klavUserRepository.delete(klavUser);
            });
    }


}
