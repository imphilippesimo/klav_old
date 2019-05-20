package com.klav.web.rest.vm.ext;

import com.klav.config.Constants;
import com.klav.config.ext.KlavConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountVM {

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

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

    @Size(min = 2, max = 6)
    @NotBlank
    private String langKey;


    @NotBlank
    @Pattern(regexp = KlavConstants.INTL_PHONE_NUMBER_REGEX, message = "The phone number must be a valid international phone number")
    private String phoneNumber;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

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

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }
}
