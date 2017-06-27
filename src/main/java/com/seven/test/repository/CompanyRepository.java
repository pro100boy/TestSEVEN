package com.seven.test.repository;

import com.seven.test.model.Company;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Company c WHERE c.id=:id")
    int delete(@Param("id") int id);

    @Override
    Company save(Company company);

    @Override
    Company findOne(Integer id);

    @Override
    List<Company> findAll(Sort sort);

    @EntityGraph(value = Company.GRAPH_WITH_USERS_REPORTS)
    List<Company> findByNameIgnoreCaseStartingWith(String name);
}
