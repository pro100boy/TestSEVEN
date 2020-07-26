package com.seven.test.controller;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.Report;
import com.seven.test.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.seven.test.AuthorizedUser.userHasAuthority;
import static com.seven.test.controller.ReportController.REST_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = REST_URL, produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ReportController {
    public static final String REST_URL = "/reports";

    private final ReportService reportService;

    @PreAuthorize("hasAnyAuthority('COMPANY_OWNER', 'COMPANY_EMPLOYER')")
    @PostMapping
    public void updateOrCreate(@Valid Report report) {
        if (report.isNew())
            reportService.save(report, AuthorizedUser.companyId());
        else
            reportService.update(report, AuthorizedUser.companyId());
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_OWNER', 'COMPANY_EMPLOYER')")
    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable("id") Integer id) {
        reportService.delete(id, AuthorizedUser.companyId());
        return String.valueOf(id);
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_OWNER', 'COMPANY_EMPLOYER')")
    @GetMapping(value = "/{id}")
    public Report getReport(@PathVariable("id") Integer id) {
        return reportService.get(id, AuthorizedUser.companyId());
    }

    @GetMapping
    public List<Report> getReports() {
        if (userHasAuthority("ADMIN"))
            return reportService.getAll();
        else
            return reportService.getAllByCompany(AuthorizedUser.companyId());
    }
}
