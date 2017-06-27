package com.seven.test.service;

import com.seven.test.model.User;
import com.seven.test.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.seven.test.util.UserUtil.prepareToSave;
import static com.seven.test.util.ValidationUtil.checkNotFound;
import static com.seven.test.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Sort SORT_NAME_EMAIL = new Sort("name", "email");

    @Autowired
    private UserRepository repository;

    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareToSave(user));
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findOne(id), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
