package com.klav.web.rest.vm.ext;

import com.klav.config.ext.KlavConstants;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ManagedAccountVM extends AccountVM {


    public ManagedAccountVM() {
    }

    @Pattern(regexp = KlavConstants.PASSWORD_REGEX,
        message = "password should contain atleast " +
            " 1 small-case letter, 1 Capital letter, 1 digit," +
            " 1 special character " +
            " and the length should be between 8-10 characters")
    private String password;

    public ManagedAccountVM(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
