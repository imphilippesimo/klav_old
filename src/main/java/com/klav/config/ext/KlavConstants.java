package com.klav.config.ext;

public class KlavConstants {
    public static final String DATE_REGEX = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";


    /**
     * This regular expression match
     * can be used for validating strong password.
     * It expects atleast
     * 1 small-case letter,
     * 1 Capital letter,
     * 1 digit,
     * 1 special character
     * and the length should be between 8-10 characters.
     * The sequence of the characters is not important.
     */
    public static final String PASSWORD_REGEX =
        "(?=^.{8,10}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "fr";
    /**
     * A regular expression to match phone numbers,
     * allowing for an international dialing code
     * at the start and hyphenation and spaces that are sometimes entered
     */
    public static final String INTL_PHONE_NUMBER_REGEX = "^(\\(?\\+?[0-9]*\\)?)?[0-9_\\- \\(\\)]*$";

    private KlavConstants() {


    }

}
