package com.seven.test.service;

import com.seven.test.model.Report;
import com.seven.test.util.exception.NotFoundException;

import java.util.List;

public interface ReportService {
    Report save(Report report);

    Report update(Report report, int companyId) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    Report get(int id) throws NotFoundException;

    List<Report> getAllByCompany(int companyId);

    List<Report> getAll();
}
