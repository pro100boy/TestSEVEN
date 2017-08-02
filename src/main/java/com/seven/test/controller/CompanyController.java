package com.seven.test.controller;

import com.seven.test.model.Company;
import com.seven.test.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping(value = "/companies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMPANY_OWNER')")
    @PostMapping
    public void updateOrCreate(@Valid Company company, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            if (company.isNew()) {
                companyService.save(company);
            } else {
                companyService.update(company, company.getId());
            }
        } else throw new ValidationException();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable("id") Integer id) {
        companyService.delete(id);
        return String.valueOf(id);
    }

    @GetMapping(value = "/{id}")
    public Company getCompany(@PathVariable("id") Integer id) {
        return companyService.get(id);
    }

    @GetMapping
    public List<Company> getCompanies() {
        return companyService.getAll();
    }
}
