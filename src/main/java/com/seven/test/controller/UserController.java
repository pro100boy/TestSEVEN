package com.seven.test.controller;

import com.seven.test.model.User;
import com.seven.test.service.UserService;
import com.seven.test.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.seven.test.util.UserUtil.createNewFromTo;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public void updateOrCreate(@Valid UserTo userTo) {
        if (userTo.isNew()) {
            userService.save(createNewFromTo(userTo));
        } else {
            userService.update(userTo, userTo.getId());
        }
    }

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
        return userService.getAll();
    }
}
