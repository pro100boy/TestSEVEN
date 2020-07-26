package com.seven.test.controller;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.Company;
import com.seven.test.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.seven.test.AuthorizedUser.userHasAuthority;
import static com.seven.test.controller.CompanyController.REST_URL;
import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = REST_URL, produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class CompanyController {
    public static final String REST_URL = "/companies";

    private final CompanyService companyService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMPANY_OWNER')")
    @PostMapping
    public void updateOrCreate(@Valid Company company) {
        if (company.isNew()) {
            companyService.save(company);
        } else
            companyService.update(company, company.getId());
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
        if (userHasAuthority("ADMIN"))
            return companyService.getAll();
        else {
            return singletonList(companyService.get(AuthorizedUser.companyId()));
        }
    }
}
