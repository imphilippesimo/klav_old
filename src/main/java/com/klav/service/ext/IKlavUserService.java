package com.klav.service.ext;

import com.klav.domain.KlavUser;
import com.klav.service.dto.KlavUserDTO;
import com.klav.service.errors.KlavUserEmailAlreadyUsedException;
import com.klav.service.errors.KlavUserNotActivatedException;
import com.klav.service.errors.PhoneNumberAlreadyUsedException;

public interface IKlavUserService {

    /**
     * Create a new Klav user.
     *
     * @param klavUserDTO the klav user Data Transfert Object
     * @throws PhoneNumberAlreadyUsedException if the phone number is already used
     * @throws KlavUserNotActivatedException if the user exists but not yet activated
     * @throws KlavUserEmailAlreadyUsedException if the email is already used
     */
    KlavUser createKlavUser(final KlavUserDTO klavUserDTO)
        throws PhoneNumberAlreadyUsedException, KlavUserNotActivatedException, KlavUserEmailAlreadyUsedException;
}
