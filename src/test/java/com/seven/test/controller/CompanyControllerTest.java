package com.seven.test.controller;

import com.seven.test.model.BaseEntity;
import com.seven.test.model.Company;
import com.seven.test.repository.CompanyRepository;
import com.seven.test.service.CompanyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.seven.test.TestUtil.buildUrlEncodedFormEntity;
import static com.seven.test.TestUtil.userAuth;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testdata.CompanyTestData.*;
import static testdata.CompanyTestData.COMPANY3;
import static testdata.UserTestData.ADMIN;
import static testdata.UserTestData.USER1;
import static testdata.UserTestData.USER2;

public class CompanyControllerTest extends AbstractControllerTest {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository repository;

    private static final String REST_URL = CompanyController.REST_URL + '/';

    /**
     * https://stackoverflow.com/a/40884509/7203956
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void testCreate() throws Exception {
        Company expected = new Company(null, "SOFT company", "soft@test.com", "address of SOFT company");

        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", "",
                        "name", expected.getName(),
                        "email", expected.getEmail(),
                        "address", expected.getAddress());

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(ADMIN))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isOk());

        Company returned = repository.findByEmail(expected.getEmail());
        assertTrue(Objects.nonNull(returned));

        List<Integer> collect = companyService.getAll().stream().map(BaseEntity::getId).collect(Collectors.toList());
        assertTrue(collect.size() == 4);
        assertThat(collect, everyItem(lessThanOrEqualTo(returned.getId())));
    }

    @Test
    public void testCreateDenied() throws Exception {
        Company expected = new Company(null, "SOFT company", "soft@test.com", "address of SOFT company");

        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", "",
                        "name", expected.getName(),
                        "email", expected.getEmail(),
                        "address", expected.getAddress());

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(USER2))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testCreateInvalid() throws Exception {
        Company expected = new Company(null, "SO", "", "address of SOFT company");

        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", "",
                        "name", expected.getName(),
                        "email", expected.getEmail(),
                        "address", expected.getAddress());

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(ADMIN))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Company updated = repository.findByEmail(COMPANY1.getEmail());

        // change address
        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", String.valueOf(updated.getId()),
                        "name", updated.getName(),
                        "email", updated.getEmail(),
                        "address", "new address");

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(ADMIN))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isOk());

        Company returned = repository.findByEmail(COMPANY1.getEmail());
        assertTrue(returned.getAddress().equals("new address"));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Company updated = repository.findByEmail(COMPANY1.getEmail());

        // remove address
        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", "",
                        "name", updated.getName(),
                        "email", updated.getEmail(),
                        "address", "");

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(ADMIN))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDuplicate() throws Exception {
        Company updated = COMPANY1;

        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", "",
                        "name", updated.getName(),
                        "email", updated.getEmail(),
                        "address", updated.getAddress());

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(ADMIN))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + COMPANY1_ID).with(csrf())
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(COMPANY2, COMPANY3), companyService.getAll());
    }

    @Test
    public void testDeleteDenied() throws Exception {
        mockMvc.perform(delete(REST_URL + COMPANY1_ID).with(csrf())
                .with(userAuth(USER2)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MATCHER.contentListMatcher(COMPANY1, COMPANY2, COMPANY3));
    }

    @Test
    public void testGetAllNonAdmin() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userAuth(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MATCHER.contentListMatcher(COMPANY2));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userAuth(ADMIN)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}

