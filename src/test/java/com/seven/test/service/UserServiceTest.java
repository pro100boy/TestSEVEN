package com.seven.test.service;

import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static testdata.CompanyTestData.COMPANY1;
import static testdata.CompanyTestData.COMPANY2_ID;
import static testdata.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    public void testSave() throws Exception {
        User newUser = new User(null, "New", "New", "new@gmail.com", "newPass", "+12354654", Collections.singleton(Role.COMPANY_EMPLOYER));
        newUser.setCompany(COMPANY1);

        User created = service.save(newUser);
        newUser.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(USER3, newUser, USER1, USER5, USER2, USER4), service.getAll());
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

    @Test
    public void testUpdate() throws Exception {
        User updated = USER1;
        updated.setName("UpdatedName");
        updated.setCompany(COMPANY1);

        service.update(updated);
        MATCHER.assertEquals(updated, service.get(USER1_ID));
    }

    @Test
    public void getAllOwner() throws Exception {
        List<User> list = service.getAllOwner(COMPANY2_ID);
        MATCHER.assertEquals(USER5, list.get(0));
    }

    @Test
    public void testValidation() throws Exception {
        // empty name
        validateRootCause(() -> service.save(new User(null, " ", "Ivanov", "ivanov@gmail.com", "password", "+380509876543", Role.COMPANY_OWNER)), ConstraintViolationException.class);
        // empty email
        validateRootCause(() -> service.save(new User(null, "Sidor", "Ivanov", " ", "password", "+380509876543", Role.COMPANY_OWNER)), ConstraintViolationException.class);
        // empty lastname
        validateRootCause(() -> service.save(new User(null, "Sidor", "", "ivanov@gmail.com", "password", "+380509876543", Role.COMPANY_OWNER)), ConstraintViolationException.class);
        // empty password
        validateRootCause(() -> service.save(new User(null, "Sidor", "Ivanov", "ivanov@gmail.com", "", "+380509876543", Role.COMPANY_OWNER)), ConstraintViolationException.class);
        // short phone
        validateRootCause(() -> service.save(new User(null, "Sidor", "Ivanov", "ivanov@gmail.com", "password", "+658", Role.COMPANY_OWNER)), ConstraintViolationException.class);
    }
}