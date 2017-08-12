package com.seven.test.controller;

import com.seven.test.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;

public class ReportControllerTest /*extends AbstractControllerTest*/ {
    @Autowired
    private ReportService reportService;

    private static final String REST_URL = ReportController.REST_URL + '/';
}
