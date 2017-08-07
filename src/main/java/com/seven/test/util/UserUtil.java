package com.seven.test.util;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.Company;
import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.to.UserTo;
import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static com.seven.test.AuthorizedUser.userHasAuthority;

public class UserUtil {
    public static final RandomStringGenerator RSG_PASSWD = new RandomStringGenerator.Builder().withinRange('!', 'z').build();
    public static final RandomStringGenerator RSG_LETTERS = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
    public static final RandomStringGenerator RSG_DIGITS = new RandomStringGenerator.Builder().withinRange('0', '9').build();

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
        newOwner.setName(RSG_LETTERS.generate(5));
        newOwner.setLastname(RSG_LETTERS.generate(10));
        newOwner.setPhone("+" + RSG_DIGITS.generate(10));
        // Generates a 15 code point string, using only ASCII symbols from A to z
        newOwner.setPassword(RSG_PASSWD.generate(15));

        LoggerFactory.getLogger(UserUtil.class).info(newOwner.getPassword());

        newOwner.setRoles(Collections.singleton(Role.COMPANY_OWNER));
        newOwner.setEmail(RSG_LETTERS.generate(5) + "." + company.getEmail());
        newOwner.setCompany(company);
        return newOwner;
    }
}
