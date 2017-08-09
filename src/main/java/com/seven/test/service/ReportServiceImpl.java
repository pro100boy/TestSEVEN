package com.seven.test.service;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.Report;
import com.seven.test.repository.ReportRepository;
import com.seven.test.util.exception.NotFoundException;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seven.test.AuthorizedUser.userHasAuthority;
import static com.seven.test.util.ValidationUtil.checkIdConsistent;
import static com.seven.test.util.ValidationUtil.checkNotFoundWithId;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportRepository reportRepository;

    @Override
    @Transactional
    public Report save(@NonNull Report report) {
        log.info("save: " + report);
        report.setCompany(AuthorizedUser.company()); // gets ID only
        return reportRepository.save(report);
    }

    @Override
    @Transactional
    public Report update(@NonNull Report report, int reportId) throws NotFoundException {
        log.info("update: " + report);
        checkIdConsistent(report, reportId);
        report.setCompany(AuthorizedUser.company());
        // проверка, чтоб не обновил отчет не своей компании
        return checkNotFoundWithId(reportRepository.save(report), report.getId());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        log.info("delete id = " + id);
        int companyId = AuthorizedUser.companyId();
        // проверка, чтоб не удалил отчет не своей компании
        checkNotFoundWithId(reportRepository.delete(id, companyId) != 0, id);
    }

    @Override
    public Report get(int id) throws NotFoundException {
        log.info("get id = " + id);
        int companyId = AuthorizedUser.companyId();
        return reportRepository.findOne(id, companyId);
    }

    @Override
    public List<Report> getAll() {
        log.info("get all");
        if (userHasAuthority("ADMIN"))
            return reportRepository.findAllByOrderByDateDesc();
        else
            return reportRepository.getAllByCompany(AuthorizedUser.companyId());
    }
}
