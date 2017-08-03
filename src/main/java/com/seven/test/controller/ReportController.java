package com.seven.test.controller;

import com.seven.test.model.Report;
import com.seven.test.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasAnyAuthority('COMPANY_OWNER', 'COMPANY_EMPLOYER')")
    @PostMapping
    public void updateOrCreate(@Valid Report report, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            if (report.isNew()) {
                reportService.save(report);
            } else {
                reportService.update(report, report.getId());
            }
        } else throw new ValidationException();
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_OWNER', 'COMPANY_EMPLOYER')")
    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable("id") Integer id) {
        reportService.delete(id);
        return String.valueOf(id);
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_OWNER', 'COMPANY_EMPLOYER')")
    @GetMapping(value = "/{id}")
    public Report getReport(@PathVariable("id") Integer id) {
        return reportService.get(id);
    }

    @GetMapping
    public List<Report> getReports() {
        return reportService.getAll();
    }
}
