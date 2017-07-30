package com.seven.test.service;

import com.seven.test.model.Company;
import com.seven.test.repository.CompanyRepository;
import com.seven.test.util.exception.NotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seven.test.util.ValidationUtil.*;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService{
    @Autowired
    private CompanyRepository repository;

    @Override
    @Transactional
    public Company save(@NonNull Company company) {
        checkNew(company);
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
    public List<Company> getByName(@NonNull String name) throws NotFoundException {
        return checkNotFound(repository.findByNameIgnoreCaseStartingWith(name), "name=" + name);
    }

    @Override
    public List<Company> getAll() {
        return repository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }

    @Override
    public void update(@NonNull Company company, int id) {
        checkIdConsistent(company, id);
        repository.save(company);
    }
}
