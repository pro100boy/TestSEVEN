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

    List<User> getAllByCompany(int companyId);
}
