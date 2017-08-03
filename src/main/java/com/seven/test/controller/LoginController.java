package com.seven.test.controller;

import com.seven.test.util.Patterns;
import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = {"/", "/main"})
    public String main(Model model) throws NotFoundException {
        // for modal forms
        model.addAttribute("emailpattern", Patterns.EMAIL_PATTERN);
        model.addAttribute("phonepattern", Patterns.PHONE_PATTERN);
        model.addAttribute("passwdpattern", Patterns.PASSWORD_PATTERN);
        return "main";
    }
}
