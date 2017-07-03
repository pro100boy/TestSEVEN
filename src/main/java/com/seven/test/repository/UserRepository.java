package com.seven.test.repository;

import com.seven.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Override
    User save(User user);

    @Override
    User findOne(Integer id);

    List<User> findAllByOrderByLastnameAscEmailAsc();

    // null if not found
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.company.id = ?1 ORDER BY u.lastname, u.name")
    List<User> getAllByCompany(int companyId);
}
