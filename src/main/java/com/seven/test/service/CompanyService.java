package com.seven.test.service;

import com.seven.test.model.Company;
import com.seven.test.util.exception.NotFoundException;

import java.util.List;

public interface CompanyService {
    Company save(Company company);

    void delete(int id) throws NotFoundException;

    Company get(int id) throws NotFoundException;

    //List<Company> getByName(String name) throws NotFoundException;

    List<Company> getAll();

    void update(Company company, int id);
}
