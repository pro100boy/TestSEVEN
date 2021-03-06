package com.seven.test.repository;

import com.seven.test.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

@Transactional(readOnly = true)
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Report r WHERE r.id=:id AND r.company.id = :companyId")
    int delete(@Param("id") int id, @Param("companyId") int companyId);

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    List<Report> findAllByOrderByDateDesc();

    @Query("SELECT r FROM Report r JOIN FETCH r.company WHERE r.id=?1 and r.company.id=?2")
    Report getWithCompany(int id, int companyId);

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    @Query("SELECT r FROM Report r WHERE r.company.id = ?1 ORDER BY r.date DESC")
    List<Report> getAllByCompany(int companyId);
}
