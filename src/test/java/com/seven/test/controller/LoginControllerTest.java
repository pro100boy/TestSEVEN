package com.seven.test.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.seven.test.TestUtil.userAuth;
import static com.seven.test.util.Patterns.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static testdata.UserTestData.ADMIN;

public class LoginControllerTest extends AbstractControllerTest {
    @Test
    public void testLogin() {
        LoginController contactsController = new LoginController();
        Assert.assertEquals("login", contactsController.login());
    }

    @Test
    public void testUnAuth() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testMainLogged() throws Exception {
        mockMvc.perform(get("/")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attributeExists("emailpattern", "phonepattern", "passwdpattern"))
                .andExpect(model().attribute("emailpattern", equalTo(EMAIL_PATTERN)))
                .andExpect(model().attribute("phonepattern", equalTo(PHONE_PATTERN)))
                .andExpect(model().attribute("passwdpattern", equalTo(PASSWORD_PATTERN)));
    }

    @Test
    public void testGetMessages() throws Exception {
        mockMvc.perform(get("/i18n")
                .with(userAuth(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$", hasKey("common.cancel")));
    }

}