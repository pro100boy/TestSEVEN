package com.seven.test.util;

import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.to.UserTo;

import java.util.Arrays;
import java.util.HashSet;

public class UserUtil {
    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setLastname(userTo.getLastname());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        user.setPhone(userTo.getPhone());
        user.setCompany(userTo.getCompany());
        return user;
    }

    public static User createNewFromTo(UserTo newUser) {
        final User user = new User();
        // setup default lowest role
        Role userRole = new Role("COMPANY_EMPLOYER");//roleRepository.findByRole("COMPANY_EMPLOYER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return updateFromTo(user, newUser);
    }
}
