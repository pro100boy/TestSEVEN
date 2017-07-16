package com.seven.test.controller;

import com.seven.test.model.User;
import com.seven.test.service.ReportService;
import com.seven.test.service.UserService;
import com.seven.test.util.Patterns;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyController companyController;

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = {"/", "/main"})
    public String main(Model model) throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getName() + " " + user.getLastname() + " (" + user.getEmail() + ")");
        //model.addAttribute("users", userService.getAll());
        //model.addAttribute("reports", reportService.getAll());
        model.addAttribute("companies", companyController.getCompanies());

        // for modal forms
        model.addAttribute("emailpattern", Patterns.EMAIL_PATTERN);
        model.addAttribute("phonepattern", Patterns.PHONE_PATTERN);
        model.addAttribute("passwdpattern", Patterns.PASSWORD_PATTERN);
        model.addAttribute("user", new User());
        // TODO потом добавить Company и Report
        return "main";
    }
}
