package com.seven.test.controller;

import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.service.UserService;
import com.seven.test.util.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static com.seven.test.TestUtil.userAuth;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testdata.CompanyTestData.COMPANY1;
import static testdata.UserTestData.*;

public class UserControllerTest extends AbstractControllerTest{
    @Autowired
    private UserService userService;

    private static final String REST_URL = UserController.REST_URL + '/';

    @Test
    @Transactional
    public void testCreate() throws Exception {
        User expected = new User(null, "New", "New", "new@gmail.com", "newPass", "+12354654", Collections.singleton(Role.COMPANY_EMPLOYER));
        expected.setCompany(COMPANY1);
        //TODO разобраться
        ResultActions action = mockMvc
                .perform(post(REST_URL).with(csrf())
                .contentType(APPLICATION_JSON_UTF8)
                .with(userAuth(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        User returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(USER3, USER1, USER5, USER2, USER4, expected), userService.getAll());
    }

    @Test
    public void testUpdate() throws Exception {
        System.out.println("asf");
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

}