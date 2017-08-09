package testdata;

import com.seven.test.model.Role;
import com.seven.test.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTestData {
    private static final Logger LOG = LoggerFactory.getLogger(UserTestData.class);

/*
INSERT INTO `users` (companyid, name, lastname, email, phone, password) VALUES
 200_000 (null, 'Ivan', 'Petrov', 'admin@gmail.com', '+380971234567', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
 200_001 (100001, 'Sidor', 'Ivanov', 'ivanov@gmail.com', '+380509876543', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
 200_002 (100002, 'Petr', 'Sidoroff', 'sid@gmail.com', '+14084567890', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
 200_003 (100000, 'Petr', 'Smirnoff', 'Smir@gmail.com', '+14087896410', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
 200_004 (100002, 'Andre', 'Tan', 'tan@gmail.com', '+14081111154', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
 200_005 (100001, 'Paul', 'Furman', 'fur@gmail.com', '+12354654', '$2a$10$Habgs3AQoWMKcMbIlLLbM.pV2DdPxrRSItlbeySrdHAdzqQEl7vee');

INSERT INTO `user_roles` (user_id, role) VALUES
  (200000, "ADMIN"),
  (200000, "ACTUATOR"),
  (200001, "COMPANY_OWNER"),
  (200004, "COMPANY_OWNER"),
  (200002, "COMPANY_EMPLOYER"),
  (200003, "COMPANY_OWNER"),
  (200005, "COMPANY_EMPLOYER");
 */
    public static final int ADMIN_ID = 200_000;
    public static final int USER1_ID = 200_001;
    public static final int USER2_ID = 200_002;
    public static final int USER3_ID = 200_003;
    public static final int USER4_ID = 200_004;
    public static final int USER5_ID = 200_005;

    public static final User ADMIN = new User(ADMIN_ID, "Ivan", "Petrov", "admin@gmail.com", "admin", "+380971234567", Role.ADMIN, Role.ACTUATOR);
    // TODO продолжить
}
