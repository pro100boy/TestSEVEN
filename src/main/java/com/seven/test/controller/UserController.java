package com.seven.test.controller;

import com.seven.test.model.User;
import com.seven.test.service.UserService;
import com.seven.test.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private MessageSource messageSource;

    // http://codetutr.com/2013/05/28/spring-mvc-form-validation/
    // the BindingResult has to be immediately after the object with @Valid
    @PostMapping
    public ResponseEntity<?> updateOrCreate(@Valid User user, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors() && !Objects.isNull(user.getCompany())) {
                userService.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else return new ResponseEntity<>("Binding error!", HttpStatus.BAD_REQUEST);
        }catch (DataIntegrityViolationException e)
        {
            return new ResponseEntity<>(messageSource.getMessage("exception.users.duplicate_email", null, LocaleContextHolder.getLocale()), HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(ValidationUtil.getRootCause(ex).getLocalizedMessage(), HttpStatus.BAD_REQUEST);
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
