package com.seven.test.util;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.Company;
import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.to.UserTo;

import java.util.Collections;

import static com.seven.test.AuthorizedUser.userHasAuthority;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class UserUtil {
    private static final int
            NAME_LENGTH = 5,
            LASTNAME_LENGTH = 8,
            PASSWORD_LENGTH = 10,
            PHONE_LENGTH = 10;

    private static final boolean
            USE_LETTERS = true,
            USE_NUMBERS = true;

    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getPassword(), user.getPhone(), user.getCompany());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setLastname(userTo.getLastname());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        user.setPhone(userTo.getPhone());
        // set the company chosen by Admin for User
        if (userHasAuthority(Role.ADMIN.name()))
            user.setCompany(userTo.getCompany());
            // set the owner's company for an employees
        else if (userHasAuthority(Role.COMPANY_OWNER.name()))
            user.setCompany(AuthorizedUser.company());
        // and employee don't change his company

        return user;
    }

    public static User createNewFromTo(UserTo newUser) {
        final User user = new User();
        // setup default lowest role
        user.setRoles(Collections.singleton(Role.COMPANY_EMPLOYER));
        return updateFromTo(user, newUser);
    }

    public static User createNewOwner(Company company) {
        final User newOwner = new User();
        newOwner.setName(randomAlphabetic(NAME_LENGTH));
        newOwner.setLastname(randomAlphabetic(LASTNAME_LENGTH));
        newOwner.setPhone("+" + random(PHONE_LENGTH, false, true));
        newOwner.setPassword(random(PASSWORD_LENGTH, USE_LETTERS, USE_NUMBERS));
        newOwner.setRoles(Collections.singleton(Role.COMPANY_OWNER));
        newOwner.setEmail(randomAlphabetic(5) + "." + company.getEmail());
        newOwner.setCompany(company);
        return newOwner;
    }

    public static User fromTo(UserTo userTo) {
        final User user = new User();
        user.setName(userTo.getName());
        user.setLastname(userTo.getLastname());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        user.setPhone(userTo.getPhone());
        user.setCompany(userTo.getCompany());
        user.setRoles(Collections.singleton(Role.COMPANY_EMPLOYER));
        return user;
    }
}
