package com.seven.test.controller;

import com.seven.test.model.User;
import com.seven.test.service.CompanyService;
import com.seven.test.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("companies", companyService.getAll());
        return "registration";
    }

    // http://codetutr.com/2013/05/28/spring-mvc-form-validation/
    // the BindingResult has to be immediately after the object with @Valid
    @PostMapping(value = "/registration")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model){
        model.addAttribute("companies", companyService.getAll());
        User userExists = userService.findByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }

        if (!bindingResult.hasErrors() && !Objects.isNull(user.getCompany())) {
            userService.save(user);
            model.addAttribute("successMessage", "User has been registered successfully");
            model.addAttribute("user", new User());
        }

        return "registration";
    }

    @GetMapping(value = "/admin/home")
    public String home(Model model) throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getName() + " " + user.getLastname() + " (" + user.getEmail() + ")");
        model.addAttribute("adminMessage", "Content Available Only for Users with Admin Role");
        return "admin/home";
    }

    @GetMapping(value = "/user/home_user")
    public String user_home(Model model) throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getName() + " " + user.getLastname() + " (" + user.getEmail() + ")");
        model.addAttribute("userMessage", "Content Available Only for Users with USER Role");
        return "user/home_user";
    }
}
