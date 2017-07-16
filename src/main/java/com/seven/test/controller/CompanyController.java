package com.seven.test.controller;

import com.seven.test.model.Company;
import com.seven.test.service.CompanyService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/companies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CompanyController {
    @Getter
    private List<Company> companyList = new ArrayList<>();

    @PostConstruct
    private void setCompanyList()
    {
        companyList = companyService.getAll();
    }

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public void updateOrCreate(@Valid Company company, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            companyService.save(company);
        }
    }

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
        return getCompanyList();
    }
}
