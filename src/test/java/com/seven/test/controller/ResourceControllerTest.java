package com.seven.test.controller;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractControllerTest {
    @Test
    public void testResources() throws Exception {
        mockMvc.perform(get("/css/login.css"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.valueOf("text/css")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/js/userDatatables.js"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.valueOf("text/javascript")))
                .andExpect(status().isOk());
    }
}
