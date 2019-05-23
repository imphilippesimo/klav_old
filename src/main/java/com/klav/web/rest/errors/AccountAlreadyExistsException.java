package com.klav.web.rest.errors;

import java.net.URI;

public class AccountAlreadyExistsException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public AccountAlreadyExistsException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, entityName, errorKey);
    }
}
