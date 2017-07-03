package com.seven.test.repository;

import com.seven.test.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Report r WHERE r.id=:id AND r.company.id = :companyId")
    int delete(@Param("id") int id, @Param("companyId") int companyId);

    @Override
    Report save(Report report);

    @Override
    Report findOne(Integer id);

    List<Report> findAllByOrderByDateDesc();

    @Query("SELECT r FROM Report r WHERE r.company.id = ?1 ORDER BY r.date DESC")
    List<Report> getAllByCompany(int companyId);
}
