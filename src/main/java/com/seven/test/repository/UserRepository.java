package com.seven.test.repository;

import com.seven.test.model.Role;
import com.seven.test.model.User;
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
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    @Query("SELECT u FROM User u JOIN FETCH u.company")
    List<User> findAllWithParams();

    // null if not found
    User findByEmail(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.company WHERE u.id=:id")
    User findOne(@Param("id") int id);

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    @Query("SELECT u FROM User u JOIN FETCH u.company WHERE u.company.id=:id AND :role member u.roles")
    List<User> findAllByCompanyAndRoles(@Param("id") int id, @Param("role") Role role);
}
