package com.seven.test.service;

import com.seven.test.model.Company;
import com.seven.test.repository.CompanyRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.seven.test.util.ValidationUtil.checkNotFound;
import static com.seven.test.util.ValidationUtil.checkNotFoundWithId;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService{
    @Autowired
    private CompanyRepository repository;

    @Override
    @Transactional
    public Company save(Company company) {
        Assert.notNull(company, "company must not be null");
        return repository.save(company);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public Company get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findOne(id), id);
    }

    @Override
    public List<Company> getByName(String name) throws NotFoundException {
        Assert.notNull(name, "name must not be null");
        return checkNotFound(repository.findByNameIgnoreCaseStartingWith(name), "name=" + name);
    }

    @Override
    public List<Company> getAll() {
        return repository.findAll();
    }

    @Override
    public void update(Company company) {
        Assert.notNull(company, "company must not be null");
        repository.save(company);
    }
}
