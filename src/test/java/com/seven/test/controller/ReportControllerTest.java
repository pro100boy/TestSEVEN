package com.seven.test.controller;

import com.seven.test.model.BaseEntity;
import com.seven.test.model.Report;
import com.seven.test.service.ReportService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.seven.test.TestUtil.buildUrlEncodedFormEntity;
import static com.seven.test.TestUtil.userAuth;
import static java.time.LocalDateTime.of;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testdata.CompanyTestData.COMPANY3;
import static testdata.ReportTestData.*;
import static testdata.UserTestData.ADMIN;
import static testdata.UserTestData.USER4;

public class ReportControllerTest extends AbstractControllerTest {
    @Autowired
    private ReportService reportService;

    private static final String REST_URL = ReportController.REST_URL + '/';

    @Test
    @Transactional
    public void testCreate() throws Exception {
        Report expected = new Report(null, "New Report", of(2017, Month.DECEMBER, 01, 12, 00), "Content of New Report");
        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", "",
                        "name", expected.getName(),
                        "date", expected.getDate().toString().replace("T", " "),
                        "data", expected.getData());

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(USER4))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isOk());

        List<Integer> collect = reportService.getAll().stream().map(BaseEntity::getId).collect(Collectors.toList());
        assertEquals(5, collect.size());

        Report returned = reportService.get(Collections.max(collect), COMPANY3.getId());
        assertEquals("New Report", returned.getName());
    }

    @Test
    public void testCreateDenied() throws Exception {
        Report expected = new Report(null, "New Report", of(2017, Month.DECEMBER, 01, 12, 00), "Content of New Report");
        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", "",
                        "name", expected.getName(),
                        "date", expected.getDate().toString().replace("T", " "),
                        "data", expected.getData());

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(ADMIN))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Report expected = new Report(null, " ", of(2017, Month.DECEMBER, 01, 12, 00), "Content of New Report");
        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", "",
                        "name", expected.getName(),
                        "date", expected.getDate().toString().replace("T", " "),
                        "data", expected.getData());

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(USER4))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Report expected = REPORT1;
        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", String.valueOf(REPORT1.getId()),
                        "name", "updated name",
                        "date", expected.getDate().toString().replace("T", " "),
                        "data", expected.getData());

        mockMvc
                .perform(post(REST_URL).with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(userAuth(USER4))
                        .content(expectedEncoded))
                .andDo(print())
                .andExpect(status().isOk());

        Report returned = reportService.get(REPORT1.getId(), COMPANY3.getId());
        assertEquals("updated name", returned.getName());
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Report expected = REPORT1;
        String expectedEncoded =
                buildUrlEncodedFormEntity(
                        "id", String.valueOf(REPORT1.getId()),
                        "name", " ",
                        "date", expected.getDate().toString().replace("T", " "),
                        "data", expected.getData());


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
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + REPORT1_ID).with(csrf())
                .with(userAuth(USER4)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(REPORT2, REPORT4, REPORT3), reportService.getAll());
    }

    @Test
    public void testDeleteDenied() throws Exception {
        mockMvc.perform(delete(REST_URL + REPORT1_ID).with(csrf())
                .with(userAuth(ADMIN)))
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
                .andExpect(MATCHER.contentListMatcher(REPORT1, REPORT2, REPORT4, REPORT3));
    }

    @Test
    public void testGetAllNonAdmin() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userAuth(USER4)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MATCHER.contentListMatcher(REPORT1, REPORT4, REPORT3));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + REPORT1.getId())
                .with(userAuth(USER4)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(print()).andExpect(MATCHER.contentMatcher(REPORT1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userAuth(ADMIN)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
