package com.seven.test.util;

public class Patterns {
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    // + and 6-14 digits
    public static final String PHONE_PATTERN = "^\\+(?:[0-9] ?){6,14}[0-9]$";//"-?[0-9]+";
    public static final String PASSWORD_PATTERN = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";

    // Password must contains latin symbols (in upper and lower case) and digits
    //@Getter
    // private static final Pattern BCRYPT_PATTERN = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");

    private Patterns() {
    }
}
