package com.seven.test.controller;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.service.UserService;
import com.seven.test.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import static com.seven.test.AuthorizedUser.userHasAuthority;
import static com.seven.test.util.UserUtil.createNewFromTo;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
    static final String REST_URL = "/users";
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN','COMPANY_OWNER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateOrCreate(@Valid @RequestBody UserTo userTo) {
        if (userTo.isNew()) {
            userService.save(createNewFromTo(userTo));
        } else {
            userService.update(userTo, userTo.getId());
        }
    }

/*    @PreAuthorize("hasAnyAuthority('ADMIN','COMPANY_OWNER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateOrCreate(@Valid @RequestBody User userTo) {
        User saved = userService.save(userTo);
    }*/

    @PreAuthorize("hasAnyAuthority('ADMIN','COMPANY_OWNER')")
    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return String.valueOf(id);
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return userService.get(id);
    }

    @GetMapping
    public List<User> getUsers() {
        // ADMIN can CRUD any users
        if (userHasAuthority(Role.ADMIN.name()))
            return userService.getAll();
            // COMPANY_OWNER can CRUD only his company's employees
        else if (userHasAuthority(Role.COMPANY_OWNER.name()))
            return userService.getAllOwner(AuthorizedUser.companyId());
            // COMPANY_EMPLOYER can CRUD only own profile
        else return Collections.singletonList(userService.get(AuthorizedUser.id()));
    }
}
