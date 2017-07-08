package com.seven.test.controller;

import com.seven.test.model.User;
import com.seven.test.service.CompanyService;
import com.seven.test.service.ReportService;
import com.seven.test.service.UserService;
import com.seven.test.util.Patterns;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ReportService reportService;

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping(value = "/main")
    public String main(Model model) throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getName() + " " + user.getLastname() + " (" + user.getEmail() + ")");
        model.addAttribute("users", userService.getAll());
        model.addAttribute("reports", reportService.getAll());
        model.addAttribute("companies", companyService.getAll());

        // for modal forms
        model.addAttribute("emailpattern", Patterns.EMAIL_PATTERN);
        model.addAttribute("phonepattern", Patterns.PHONE_PATTERN);
        model.addAttribute("passwdpattern", Patterns.PASSWORD_PATTERN);
        model.addAttribute("user", new User());
        // TODO потом добавить Company и Report
        return "main";
    }

    // http://codetutr.com/2013/05/28/spring-mvc-form-validation/
    // the BindingResult has to be immediately after the object with @Valid
    @PostMapping(value = "/registration")
    public String updateOrCreate(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model){
        model.addAttribute("companies", companyService.getAll());
        if (user.isNew()) {
            User userExists = userService.findByEmail(user.getEmail());
            if (userExists != null) {
                bindingResult
                        .rejectValue("email", "error.user",
                                "There is already a user registered with the email provided");
            }
        }
        if (!bindingResult.hasErrors() && !Objects.isNull(user.getCompany())) {
            userService.save(user);
            //model.addAttribute("successMessage", "User has been registered successfully");
            //model.addAttribute("user", new User());
        }

        return "redirect:/main";
    }

    @DeleteMapping(value = "/users/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Integer id)
    {
        userService.delete(id);
        return String.valueOf(id);
    }

    @GetMapping(value = "/users/{id}")
    @ResponseBody
    public User getUser(@PathVariable("id") Integer id)
    {
        return userService.get(id);
    }

    @PutMapping(value = "/users/{id}")
    public String edit(@PathVariable("id") int id)
    {
        return "redirect:/main";
    }
}
