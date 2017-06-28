package com.seven.test.util;

import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.to.UserTo;

public class UserUtil {

    public static User createNewFromTo(UserTo newUser) {
        return new User(null, newUser.getName(), newUser.getLastname(), newUser.getEmail().toLowerCase(), newUser.getPassword(), newUser.getPhone(), Role.COMPANY_EMPLOYER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getPassword(), user.getPhone());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setLastname(userTo.getLastname());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPhone(userTo.getPhone());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
