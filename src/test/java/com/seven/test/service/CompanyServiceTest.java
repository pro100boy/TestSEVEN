package com.seven.test.service;

import com.seven.test.model.Company;
import com.seven.test.util.exception.NotFoundException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static testdata.CompanyTestData.*;

public class CompanyServiceTest extends AbstractServiceTest{
    @Autowired
    private CompanyService service;

    @Test
    @Ignore
    public void testSave() {
        Company newCompany = new Company(null, "New company", "new@test.com", "address");

        Company created = service.save(newCompany);
        newCompany.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(COMPANY1, COMPANY2, COMPANY3, newCompany), service.getAll());
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateNameMailSave() throws Exception {
        service.save(new Company(null, "ATB company", "atb.cmp@test.com", "address of ATB company"));
    }

    @Test
    public void testDelete() {
        service.delete(COMPANY1_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(COMPANY2, COMPANY3), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void testGet() {
        MATCHER.assertEquals(service.get(COMPANY1_ID), COMPANY1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testGetAll() {
        MATCHER.assertCollectionEquals(Arrays.asList(COMPANY1, COMPANY2, COMPANY3), service.getAll());
    }

    @Test
    public void testUpdate() {
        Company updated = COMPANY1;
        updated.setEmail("newEmail@email.com");
        service.update(COMPANY1, COMPANY1_ID);
        MATCHER.assertEquals(updated, service.get(COMPANY1_ID));
    }

    @Test
    public void testValidation() throws Exception {
        // empty name
        validateRootCause(() -> service.save(new Company(null, " ", "atb.cmp@test.com", "address of ATB company")), ConstraintViolationException.class);
        // empty email
        validateRootCause(() -> service.save(new Company(null, "ATB company", " ", "address of ATB company")), ConstraintViolationException.class);
        // wrong email
        validateRootCause(() -> service.save(new Company(null, "ATB company", "email", "address of ATB company")), ConstraintViolationException.class);
        // empty address
        validateRootCause(() -> service.save(new Company(null, "ATB company", "atb.cmp@test.com", " ")), ConstraintViolationException.class);
    }
}