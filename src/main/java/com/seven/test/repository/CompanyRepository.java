package com.seven.test.repository;

import com.seven.test.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Company c WHERE c.id=:id")
    int delete(@Param("id") int id);
}
