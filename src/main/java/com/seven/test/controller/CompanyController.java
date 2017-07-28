package com.seven.test.controller;

import com.seven.test.model.Company;
import com.seven.test.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/companies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private MessageSource messageSource;

    @PostMapping
    public void updateOrCreate(@Valid Company company, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            companyService.save(company);
        }
    }

/*    @PostMapping
    public ResponseEntity<?> updateOrCreate(@Valid CompanyTo company, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                System.out.println(company);
                return new ResponseEntity<>(company, HttpStatus.OK);
            } else return new ResponseEntity<>("Binding error!", HttpStatus.BAD_REQUEST);
        }catch (DataIntegrityViolationException e)
        {
            return new ResponseEntity<>(messageSource.getMessage("exception.users.duplicate_email", null, LocaleContextHolder.getLocale()), HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(ValidationUtil.getRootCause(ex).getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

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
