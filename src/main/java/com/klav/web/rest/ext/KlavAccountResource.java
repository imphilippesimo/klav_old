package com.klav.web.rest.ext;

import com.codahale.metrics.annotation.Timed;
import com.klav.domain.KlavUser;
import com.klav.service.errors.KlavUserEmailAlreadyUsedException;
import com.klav.service.errors.KlavUserNotActivatedException;
import com.klav.service.errors.PhoneNumberAlreadyUsedException;
import com.klav.service.ext.IKlavUserService;
import com.klav.service.ext.NotificationContext;
import com.klav.service.ext.NotificationService;
import com.klav.web.rest.errors.AccountAlreadyExistsException;
import com.klav.web.rest.errors.ErrorConstants;
import com.klav.web.rest.vm.CreateAccountVM;
import com.klav.web.rest.vm.mapper.CreateAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ext")
public class KlavAccountResource {

    private final Logger log = LoggerFactory.getLogger(KlavAccountResource.class);

    private final IKlavUserService klavUserService;

    private final CreateAccountMapper createAccountMapper;

    private final NotificationService notificationService;

    public KlavAccountResource(IKlavUserService klavUserService, CreateAccountMapper createAccountMapper,
                               NotificationService notificationService) {
        this.klavUserService = klavUserService;
        this.createAccountMapper = createAccountMapper;
        this.notificationService = notificationService;
    }

    /**
     * POST  /register : register a klav user.
     *
     * @param createAccountVM the account user View Model
     * @throws AccountAlreadyExistsException 400 (Bad Request) if the account already exists
     */
    @PostMapping("/register")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody CreateAccountVM createAccountVM) {
        KlavUser klavUser;

        try {
            klavUser = klavUserService.createKlavUser(createAccountMapper.vmToDto(createAccountVM));

        } catch (PhoneNumberAlreadyUsedException e) {
            throw new AccountAlreadyExistsException(
                ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Phone number is already in use!", "", "phone-number-exists");
        } catch (KlavUserNotActivatedException e) {
            throw new AccountAlreadyExistsException(
                ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Account already exists but not activated", "", "account-not-activated");
        } catch (KlavUserEmailAlreadyUsedException e) {
            throw new AccountAlreadyExistsException(
                ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Email is already in use!", "", "email-exists");
        }

        NotificationContext<KlavUser> notificationContext = new NotificationContext<>();
        notificationContext.setTo(createAccountVM.getEmail());
        notificationContext.setBoundedContext(klavUser);
        notificationService.pushAccountValidation(notificationContext);
    }
}
