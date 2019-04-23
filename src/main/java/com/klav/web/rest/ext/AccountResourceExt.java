package com.klav.web.rest.ext;

import com.codahale.metrics.annotation.Timed;
import com.klav.domain.KlavUser;
import com.klav.domain.User;
import com.klav.repository.UserRepository;
import com.klav.security.SecurityUtils;
import com.klav.service.MailService;
import com.klav.service.UserService;
import com.klav.service.dto.KlavUserDTO;
import com.klav.service.dto.PasswordChangeDTO;
import com.klav.service.dto.UserDTO;
import com.klav.service.ext.KlavUserServiceExtended;
import com.klav.web.rest.errors.*;
import com.klav.web.rest.vm.KeyAndPasswordVM;
import com.klav.web.rest.vm.ManagedUserVM;
import com.klav.web.rest.vm.ext.AccountVM;
import com.klav.web.rest.vm.ext.ManagedAccountVM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/ext")
public class AccountResourceExt {

    private final Logger log = LoggerFactory.getLogger(com.klav.web.rest.AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final KlavUserServiceExtended klavUserServiceExtended;

    private final MailService mailService;

    public AccountResourceExt(UserRepository userRepository, UserService userService, KlavUserServiceExtended klavUserServiceExtended, MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.klavUserServiceExtended = klavUserServiceExtended;
        this.mailService = mailService;
    }


    @PostMapping("/register")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedAccountVM managedAccountVM) {
        if (!checkPasswordLength(managedAccountVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        KlavUserDTO klavUserDTO = new KlavUserDTO();
        klavUserDTO.setPhoneNumber(managedAccountVM.getPhoneNumber());
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(managedAccountVM.getEmail());
        userDTO.setLangKey(managedAccountVM.getLangKey());
        userDTO.setLastName(managedAccountVM.getLastName());
        userDTO.setFirstName(managedAccountVM.getFirstName());
        userDTO.setLogin(managedAccountVM.getLogin());
        klavUserDTO.setPerson(userDTO);

        KlavUser klavUser = klavUserServiceExtended.registerKlavUser(klavUserDTO, managedAccountVM.getPassword());
        mailService.sendActivationEmail(klavUser.getPerson());
        mailService.sendActivationEmail(klavUser.getPerson());
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    @Timed
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this activation key");
        }
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }


    @GetMapping("/account")
    @Timed
    public AccountVM getAccount() {
        return klavUserServiceExtended.getKlavUserWithAuthorities()
            .map(klavUser -> {
                AccountVM accountVM = new AccountVM();
                accountVM.setEmail(klavUser.getPerson().getEmail());
                accountVM.setFirstName(klavUser.getPerson().getFirstName());
                accountVM.setLastName(klavUser.getPerson().getLastName());
                accountVM.setLogin(klavUser.getPerson().getLogin());
                accountVM.setPhoneNumber(klavUser.getPhoneNumber());
                accountVM.setLangKey(klavUser.getPerson().getLangKey());
                return accountVM;
            })
            .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
    }

    /**
     * PUT  /account : update the current user information.
     *
     * @param managedAccountVM the current user information
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws RuntimeException          500 (Internal Server Error) if the user login wasn't found
     */
    @PutMapping("/account")
    @Timed
    public void saveAccount(@Valid @RequestBody ManagedAccountVM managedAccountVM) {
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(managedAccountVM.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("User could not be found");
        }
        klavUserServiceExtended.updateUser(managedAccountVM.getFirstName(), managedAccountVM.getLastName(), managedAccountVM.getEmail(), managedAccountVM.getLangKey()
            , managedAccountVM.getPhoneNumber(), null, null, null, null, null);

    }

    /**
     * POST  /account/change-password : changes the current user's password
     *
     * @param passwordChangeDto current and new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @PostMapping(path = "/account/change-password")
    @Timed
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset-password/init")
    @Timed
    public void requestPasswordReset(@RequestBody String mail) {
        mailService.sendPasswordResetMail(
            userService.requestPasswordReset(mail)
                .orElseThrow(EmailNotFoundException::new)
        );
    }

    /**
     * POST   /account/reset-password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws RuntimeException         500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset-password/finish")
    @Timed
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
