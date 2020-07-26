package com.seven.test.service;

import com.seven.test.model.Report;
import com.seven.test.repository.CompanyRepository;
import com.seven.test.repository.ReportRepository;
import com.seven.test.util.exception.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seven.test.util.ValidationUtil.checkNotFoundWithId;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service("reportService")
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public Report save(@NonNull Report report, int companyId) {
        log.info("save: " + report);

        if (report.isNotNew() && isNull(this.get(report.getId(), companyId))) {
            return null;
        }

        report.setCompany(companyRepository.getOne(companyId));
        return reportRepository.save(report);
    }

    @Override
    @Transactional
    public Report update(@NonNull Report report, int companyId) throws NotFoundException {
        // проверка, чтоб не обновил отчет не своей компании
        return checkNotFoundWithId(save(report, companyId), report.getId());
    }

    @Override
    public void delete(int id, int companyId) throws NotFoundException {
        log.info("delete id = " + id);
        // проверка, чтоб не удалил отчет не своей компании
        checkNotFoundWithId(reportRepository.delete(id, companyId) != 0, id);
    }

    @Override
    public Report getWithCompany(int id, int companyId) throws NotFoundException {
        log.info("get id = " + id);
        return reportRepository.getWithCompany(id, companyId);
    }

    @Override
    public Report get(int id, int companyId) throws NotFoundException {
        log.info("get id = " + id);
        Report report = reportRepository.findOne(id);
        return checkNotFoundWithId(nonNull(report) && report.getCompany().getId() == companyId ? report : null, id);
    }

    @Override
    public List<Report> getAll() {
        log.info("get all");
        return reportRepository.findAllByOrderByDateDesc();
    }

    @Override
    public List<Report> getAllByCompany(int companyId) {
        log.info("getAllByCompany for company: " + companyId);
        return reportRepository.getAllByCompany(companyId);
    }
}
