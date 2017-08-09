package testdata;

import com.seven.test.matcher.ModelMatcher;
import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class UserTestData {
    private static final Logger LOG = LoggerFactory.getLogger(UserTestData.class);

    public static final int ADMIN_ID = 200_000;
    public static final int USER1_ID = 200_001;
    public static final int USER2_ID = 200_002;
    public static final int USER3_ID = 200_003;
    public static final int USER4_ID = 200_004;
    public static final int USER5_ID = 200_005;

    public static final User ADMIN = new User(ADMIN_ID, "Ivan", "Petrov", "admin@gmail.com", "admin", "+380971234567", Role.ADMIN, Role.ACTUATOR);
    public static final User USER1 = new User(USER1_ID, "Sidor", "Ivanov", "ivanov@gmail.com", "password", "+380509876543", Role.COMPANY_OWNER);
    public static final User USER2 = new User(USER2_ID, "Petr", "Sidoroff", "sid@gmail.com", "password", "+14084567890", Role.COMPANY_EMPLOYER);
    public static final User USER3 = new User(USER3_ID, "Petr", "Smirnoff", "Smir@gmail.com", "password", "+14087896410", Role.COMPANY_OWNER);
    public static final User USER4 = new User(USER4_ID, "Andre", "Tan", "tan@gmail.com", "password", "+14081111154", Role.COMPANY_OWNER);
    public static final User USER5 = new User(USER5_ID, "Paul", "Furman", "fur@gmail.com", "password", "+12354654", Role.COMPANY_EMPLOYER);

    public static final ModelMatcher<User> MATCHER = ModelMatcher.of(User.class,
            (expected, actual) -> expected == actual ||
                    (comparePassword(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getLastname(), actual.getLastname())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && Objects.equals(expected.getPhone(), actual.getPhone())
                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );

    private static boolean comparePassword(String rawOrEncodedPassword, String password) {
        if (PasswordUtil.isEncoded(rawOrEncodedPassword)) {
            return rawOrEncodedPassword.equals(password);
        } else if (!PasswordUtil.isMatch(rawOrEncodedPassword, password)) {
            LOG.error("Password " + password + " doesn't match encoded " + password);
            return false;
        }
        return true;
    }
}
