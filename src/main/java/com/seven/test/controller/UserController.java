package com.seven.test.controller;

import com.seven.test.model.User;
import com.seven.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
    @Autowired
    private UserService userService;

    // http://codetutr.com/2013/05/28/spring-mvc-form-validation/
    // the BindingResult has to be immediately after the object with @Valid
    @PostMapping
    public void updateOrCreate(@Valid User user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors() && !Objects.isNull(user.getCompany())) {
            userService.save(user);
        }
    }

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
