package com.seven.test.controller;

import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.service.AbstractServiceTest;
import com.seven.test.service.UserService;
import com.seven.test.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static testdata.UserTestData.*;
import static testdata.UserTestData.MATCHER;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    public void testSave() throws Exception {
        User newUser = new User(null, "New", "New", "new@gmail.com", "newPass", "+12354654", Collections.singleton(Role.COMPANY_EMPLOYER));

        User created = service.save(newUser);
        newUser.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(USER3, USER1, USER5, USER2, USER4, newUser), service.getAll());
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailSave() throws Exception {
        service.save(new User(null, "Paul", "Furman", "fur@gmail.com", "password", "+12354654", Role.COMPANY_EMPLOYER));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER1_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER3, USER5, USER2, USER4), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void testGet() throws Exception {
        User user = service.get(USER1_ID);
        MATCHER.assertEquals(USER1, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

   @Test
    public void testGetByEmail() throws Exception {
        User user = service.findByEmail("admin@gmail.com");
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<User> all = service.getAll();
        MATCHER.assertCollectionEquals(Arrays.asList(USER3, USER1, USER5, USER2, USER4), all);
    }
/*
    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        //updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(USER1_ID));
    }

    @Test
    public void testSetEnabledEquals() {
        service.enable(USER1_ID, false);
        Assert.assertFalse(service.get(USER1_ID).isEnabled());
        service.enable(USER1_ID, true);
        Assert.assertTrue(service.get(USER1_ID).isEnabled());
    }

    @Test
    public void testValidation() throws Exception {
        // empty name
        validateRootCause(() -> service.save(new User(null, "  ", "invalid@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        // empty email
        validateRootCause(() -> service.save(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        // empty password
        validateRootCause(() -> service.save(new User(null, "User", "invalid@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
    }*/
}