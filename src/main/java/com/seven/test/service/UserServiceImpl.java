package com.seven.test.service;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.repository.UserRepository;
import com.seven.test.to.UserTo;
import com.seven.test.util.exception.NotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.seven.test.AuthorizedUser.userHasAuthority;
import static com.seven.test.util.UserUtil.prepareToSave;
import static com.seven.test.util.UserUtil.updateFromTo;
import static com.seven.test.util.ValidationUtil.*;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public User save(@NonNull User user) {
        checkNew(user);
        return repository.save(prepareToSave(user));
    }

    @Override
    @Transactional
    public void update(UserTo userTo, int id) {
        checkIdConsistent(userTo, id);
        User user = updateFromTo(get(id), userTo);
        repository.save(prepareToSave(user));
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
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<User> getAll() {
        // ADMIN can CRUD any users
        if (userHasAuthority(Role.ADMIN.name()))
            return repository.findAllWithParams();//findAll();
        // COMPANY_OWNER can CRUD only his company's employees
        else if (userHasAuthority(Role.COMPANY_OWNER.name()))
            return repository.findAllByCompanyAndRoles(AuthorizedUser.companyId(), Role.COMPANY_EMPLOYER);
        // COMPANY_EMPLOYER can CRUD only own profile
        else return Arrays.asList(get(AuthorizedUser.id()));
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = findByEmail(email.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(u);
    }
}
