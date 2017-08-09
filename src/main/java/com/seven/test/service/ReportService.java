package com.seven.test.service;

import com.seven.test.model.Report;
import com.seven.test.util.exception.NotFoundException;

import java.util.List;

public interface ReportService {
    Report save(Report report, int companyId);

    Report update(Report report, int companyId) throws NotFoundException;

    void delete(int id, int companyId) throws NotFoundException;

    Report getWithCompany(int id, int companyId) throws NotFoundException;

    Report get(int id, int companyId) throws NotFoundException;

    List<Report> getAll();

    List<Report> getAllByCompany(int companyId);
}
