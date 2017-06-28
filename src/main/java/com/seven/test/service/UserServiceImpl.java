package com.seven.test.service;

import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.repository.RoleRepository;
import com.seven.test.repository.UserRepository;
import com.seven.test.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

import static com.seven.test.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        Role userRole = roleRepository.findByRole("COMPANY_EMPLOYER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return repository.save(user);
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
    public User findByEmail(String email)  {
        return repository.findByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return repository.findAllByOrderByNameAscEmailAsc();
    }

    @Override
    @Transactional
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        user.setEmail(user.getEmail().toLowerCase());
        repository.save(user);
    }

    @Override
    public List<User> getAllByCompany(int companyId) {
        return repository.getAllByCompany(companyId);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email.toLowerCase());
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
    }
}
