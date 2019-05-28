package com.klav.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ActivationKeyNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public ActivationKeyNotFoundException() {
        super(ErrorConstants.ACTIVATION_KEY_NOT_FOUND_TYPE, "No user was found for this activation key", Status.NOT_FOUND);
    }
}
