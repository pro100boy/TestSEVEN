package com.seven.test.controller;

import com.seven.test.model.BaseEntity;
import com.seven.test.model.User;
import com.seven.test.service.UserService;
import com.seven.test.to.UserTo;
import com.seven.test.util.UserUtil;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testdata.CompanyTestData.COMPANY1;
import static testdata.UserTestData.*;

public class UserControllerTest extends AbstractControllerTest {
    @Autowired
    private UserService userService;

    private static final String REST_URL = UserController.REST_URL + '/';

    /**
     * https://stackoverflow.com/a/40884509/7203956
     */
    @Test
    @Transactional
    public void testCreate() throws Exception {
        UserTo expected = new UserTo(null, "New", "New", "new@gmail.com", "newPass", "+12354654", COMPANY1);

        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", "",
                        "name", expected.getName(),
                        "lastname", expected.getLastname(),
                        "email", expected.getEmail(),
                        "password", expected.getPassword(),
                        "phone", expected.getPhone(),
                        "company", String.valueOf(expected.getCompany().getId()));

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(ADMIN))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isOk());

        User returned = userService.findByEmail(expected.getEmail());
        assertTrue(Objects.nonNull(returned));

        List<Integer> collect = userService.getAll().stream().map(BaseEntity::getId).collect(Collectors.toList());
        assertEquals(6, collect.size());
        assertThat(collect, everyItem(lessThanOrEqualTo(returned.getId())));
    }

    @Test
    @Transactional
    public void testCreateInvalid() throws Exception {
        UserTo expected = new UserTo(null, "", "", "new@gmail.com", "newPass", "+12354654", COMPANY1);

        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", "",
                        "name", expected.getName(),
                        "lastname", expected.getLastname(),
                        "email", expected.getEmail(),
                        "password", expected.getPassword(),
                        "phone", expected.getPhone(),
                        "company", String.valueOf(expected.getCompany().getId()));

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(ADMIN))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        UserTo updatedTo = UserUtil.asTo(userService.findByEmail(USER1.getEmail()));

        // change name and last name
        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", String.valueOf(updatedTo.getId()),
                        "name", "newName",
                        "lastname", "newLastName",
                        "email", updatedTo.getEmail(),
                        "password", updatedTo.getPassword(),
                        "phone", updatedTo.getPhone(),
                        "company", String.valueOf(updatedTo.getCompany().getId()));

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(ADMIN))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isOk());

        User returned = userService.findByEmail(USER1.getEmail());
        assertEquals("newLastName", returned.getLastname());
        assertEquals("newName", returned.getName());
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        UserTo updatedTo = UserUtil.asTo(userService.findByEmail(USER1.getEmail()));

        // change name and last name
        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", String.valueOf(updatedTo.getId()),
                        "name", "",
                        "lastname", "",
                        "email", updatedTo.getEmail(),
                        "password", updatedTo.getPassword(),
                        "phone", updatedTo.getPhone(),
                        "company", String.valueOf(updatedTo.getCompany().getId()));

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(ADMIN))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDuplicate() throws Exception {
        UserTo expected = new UserTo(null, "New", "New", USER1.getEmail(), "newPass", "+12354654", COMPANY1);

        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", "",
                        "name", expected.getName(),
                        "lastname", expected.getLastname(),
                        "email", expected.getEmail(),
                        "password", expected.getPassword(),
                        "phone", expected.getPhone(),
                        "company", String.valueOf(expected.getCompany().getId()));

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
        mockMvc.perform(delete(REST_URL + USER1_ID).with(csrf())
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(USER3, USER5, USER2, USER4), userService.getAll());
    }

    @Test
    public void testGetEmployee() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userAuth(USER2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MATCHER.contentListMatcher(USER2));
    }

    @Test
    public void testGetEmployees() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userAuth(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MATCHER.contentListMatcher(USER5));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MATCHER.contentListMatcher(USER3, USER1, USER5, USER2, USER4));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userAuth(ADMIN)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}