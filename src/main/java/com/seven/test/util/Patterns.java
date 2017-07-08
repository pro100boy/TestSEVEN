package com.seven.test.util;

import com.seven.test.util.validation.EnsureEmailValidator;

public class Patterns {
    // ISBN 978-0-596-52068-7. Paragraph 4.1 -- doesn't work in Thymeleaf ((
    //public static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[A-Z0-9-]+\\.)+[A-Z]{2,6}$";

    public static final String EMAIL_PATTERN = EnsureEmailValidator.getEMAIL_PATTERN();
    // + and 6-14 digits
    public static final String PHONE_PATTERN = "^\\+(?:[0-9] ?){6,14}[0-9]$";//"-?[0-9]+";
    public static final String PASSWORD_PATTERN = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";

    // Password must contains latin symbols (in upper and lower case) and digits
    //@Getter
    // private static final Pattern BCRYPT_PATTERN = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");

    private Patterns() {
    }
}
