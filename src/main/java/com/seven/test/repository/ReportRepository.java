package com.seven.test.repository;

import com.seven.test.model.Report;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Transactional
    @Modifying
    @Override
    //@Query("DELETE FROM Report r WHERE r.id=:id")
    void delete(Integer id);

    @Override
    @Transactional
    Report save(Report report);

    @Override
    Report findOne(Integer id);

    @Override
    List<Report> findAll(Sort sort);
}
