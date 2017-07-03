package com.seven.test.service;

import com.seven.test.model.Report;
import com.seven.test.repository.CompanyRepository;
import com.seven.test.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.seven.test.util.exception.NotFoundException;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.seven.test.util.ValidationUtil.checkNotFoundWithId;

@Service("reportService")
public class ReportServiceImpl implements ReportService{
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    @Transactional
    public Report save(Report report, int companyId) {
        if (!report.isNew() && get(report.getId(), companyId) == null) {
            report = null;
        }
        else {
            report.setCompany(companyRepository.getOne(companyId));
        }
        Assert.notNull(report, "report must not be null");
        return reportRepository.save(report);
    }

    @Override
    @Transactional
    public Report update(Report report, int companyId) throws NotFoundException {
        return checkNotFoundWithId(save(report, companyId), report.getId());
    }

    @Override
    public void delete(int id, int companyId) throws NotFoundException {
        checkNotFoundWithId(reportRepository.delete(id, companyId) != 0, id);
    }

    @Override
    public Report get(int id, int companyId) throws NotFoundException {
        Report report = reportRepository.findOne(id);
        return checkNotFoundWithId(report != null && report.getCompany().getId() == companyId ? report : null, id);
    }

    @Override
    public List<Report> getAllByCompany(int companyId) {
        return reportRepository.getAllByCompany(companyId);
    }

    @Override
    public List<Report> getAll() {
        return reportRepository.findAllByOrderByNameAsc();
    }
}
