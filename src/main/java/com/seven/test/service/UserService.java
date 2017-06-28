package com.seven.test.service;

import com.seven.test.model.User;
import com.seven.test.util.exception.NotFoundException;

import java.util.List;

public interface UserService {
    User save(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User findByEmail(String email);

    List<User> getAll();

    void update(User user);

    //void update(UserTo user);

    List<User> getAllByCompany(int companyId);
}
