package com.seven.test.service;

import com.seven.test.model.Report;
import com.seven.test.model.User;
import com.seven.test.repository.CompanyRepository;
import com.seven.test.repository.ReportRepository;
import com.seven.test.util.exception.NotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seven.test.util.ValidationUtil.checkIdConsistent;
import static com.seven.test.util.ValidationUtil.checkNotFoundWithId;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Report save(@NonNull Report report) {
        int companyId = getCompanyId();
        report.setCompany(companyRepository.getOne(companyId)); // gets ID only
        return reportRepository.save(report);
    }

    @Override
    @Transactional
    public Report update(@NonNull Report report, int reportId) throws NotFoundException {
        checkIdConsistent(report, reportId);
        int companyId = getCompanyId();
        report.setCompany(companyRepository.getOne(companyId)); // gets ID only
        // проверка, чтоб не обновил отчет не своей компании
        return checkNotFoundWithId(reportRepository.save(report), report.getId());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        int companyId = getCompanyId();
        // проверка, чтоб не удалил отчет не своей компании
        checkNotFoundWithId(reportRepository.delete(id, companyId) != 0, id);
    }

    @Override
    public Report get(int id) throws NotFoundException {
        int companyId = getCompanyId();
        //return reportRepository.findOne(id);
        Report report = reportRepository.findOne(id);
        return checkNotFoundWithId(report != null && report.getCompany().getId() == companyId ? report : null, id);
    }

    @Override
    public List<Report> getAllByCompany(int companyId) {
        return reportRepository.getAllByCompany(companyId);
    }

    @Override
    public List<Report> getAll() {
        return reportRepository.findAllByOrderByDateDesc();
    }

    /**
     * extract company id from User data
     * @return companyId
     */
    private int getCompanyId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            User user = userService.findByEmail(auth.getName());
            return user.getCompany().getId();
        } else throw new UsernameNotFoundException("Can't get user credentials");
    }
}
