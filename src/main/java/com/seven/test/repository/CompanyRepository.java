package com.seven.test.repository;

import com.seven.test.model.Company;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Transactional
    @Modifying
    @Override
    //@Query("DELETE FROM Company c WHERE c.id=:id")
    void delete(Integer id);

    @Override
    @Transactional
    Company save(Company company);

    @Override
    Company findOne(Integer id);

    @Override
    List<Company> findAll(Sort sort);

    Company getByEmail(String email);
}
