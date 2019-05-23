package com.klav.web.rest.vm;

import com.klav.config.KlavConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateAccountVM {

    @Size(max = 50)
    @NotBlank
    private String firstName;

    @Size(max = 50)
    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    @Size(min = 5, max = 254)
    private String email;

    @NotBlank
    @Pattern(regexp = KlavConstants.INTL_PHONE_NUMBER_REGEX, message = KlavConstants.INTL_PHONE_NUMBER_REGEX_MSG)
    private String phoneNumber;

    @Pattern(regexp = KlavConstants.PASSWORD_REGEX, message = KlavConstants.PASSWORD_REGEX_MSG)
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
