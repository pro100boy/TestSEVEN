package com.seven.test.service;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.User;
import com.seven.test.repository.UserRepository;
import com.seven.test.to.UserTo;
import com.seven.test.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.seven.test.util.UserUtil.prepareToSave;
import static com.seven.test.util.UserUtil.updateFromTo;
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
        return repository.findAllByOrderByNameAscEmailAsc();
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(prepareToSave(user));
    }

    @Transactional
    @Override
    public void update(UserTo userTo) {
        User user = updateFromTo(get(userTo.getId()), userTo);
        repository.save(prepareToSave(user));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = repository.getByEmail(email.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(u);
    }

    @Override
    public List<User> getAllByCompany(int companyId) {
        return repository.getAllByCompany(companyId);
    }
}
