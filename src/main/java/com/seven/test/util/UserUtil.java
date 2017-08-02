package com.seven.test.util;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.to.UserTo;

import java.util.Collections;

import static com.seven.test.AuthorizedUser.userHasAuthority;

public class UserUtil {
    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static UserTo asTo(User user) {
        //Integer id, String name, String lastname, String email, String password, String phone, Company company
        return new UserTo(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getPassword(), user.getPhone(), user.getCompany());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setLastname(userTo.getLastname());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        user.setPhone(userTo.getPhone());
        if (userHasAuthority("ADMIN"))
            user.setCompany(userTo.getCompany());
        else if (userHasAuthority("COMPANY_OWNER"))
            user.setCompany(AuthorizedUser.company());
        return user;
    }

    public static User createNewFromTo(UserTo newUser) {
        final User user = new User();
        // setup default lowest role
        user.setRoles(Collections.singleton(Role.COMPANY_EMPLOYER));
        return updateFromTo(user, newUser);
    }
}
