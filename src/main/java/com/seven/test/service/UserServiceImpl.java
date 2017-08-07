package com.seven.test.service;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.repository.UserRepository;
import com.seven.test.to.UserTo;
import com.seven.test.util.exception.NotFoundException;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.seven.test.AuthorizedUser.userHasAuthority;
import static com.seven.test.util.UserUtil.prepareToSave;
import static com.seven.test.util.UserUtil.updateFromTo;
import static com.seven.test.util.ValidationUtil.*;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public User save(@NonNull User user) {
        log.info("save: " + user);
        checkNew(user);
        return repository.save(prepareToSave(user));
    }

    @Override
    @Transactional
    public void update(UserTo userTo, int id) {
        log.info("update: " + userTo);
        checkIdConsistent(userTo, id);
        User user = updateFromTo(get(id), userTo);
        repository.save(prepareToSave(user));
    }

    @Override
    public void delete(int id) throws NotFoundException {
        log.info("delete id = " + id);
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        log.info("get id = " + id);
        return checkNotFoundWithId(repository.findOne(id), id);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<User> getAll() {
        log.info("get all");
        // ADMIN can CRUD any users
        if (userHasAuthority(Role.ADMIN.name()))
            return repository.findAllWithParams();
        // COMPANY_OWNER can CRUD only his company's employees
        else if (userHasAuthority(Role.COMPANY_OWNER.name()))
            return repository.findAllByCompanyAndRoles(AuthorizedUser.companyId(), Role.COMPANY_EMPLOYER);
        // COMPANY_EMPLOYER can CRUD only own profile
        else return Collections.singletonList(get(AuthorizedUser.id()));
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = findByEmail(email.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        log.info("logged: " + u);
        return new AuthorizedUser(u);
    }
}
