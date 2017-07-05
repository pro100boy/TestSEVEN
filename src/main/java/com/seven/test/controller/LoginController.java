package com.seven.test.controller;

import com.seven.test.model.User;
import com.seven.test.service.CompanyService;
import com.seven.test.service.ReportService;
import com.seven.test.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ReportService reportService;

    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    private final String PHONE_PATTERN = "^\\+(?:[0-9] ?){6,14}[0-9]$";//"-?[0-9]+";

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
        model.addAttribute("emailpattern", EMAIL_PATTERN);
        model.addAttribute("phonepattern", PHONE_PATTERN);
        model.addAttribute("user", new User());
        // TODO потом добавить Company и Report
        return "main";
    }

/*    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("companies", companyService.getAll());
        return "registration";
    }*/

    // http://codetutr.com/2013/05/28/spring-mvc-form-validation/
    // the BindingResult has to be immediately after the object with @Valid
    @PostMapping(value = "/registration")
    public String createNewUser(@ModelAttribute /*@Valid*/ User user, BindingResult bindingResult, Model model){
        model.addAttribute("companies", companyService.getAll());
        User userExists = userService.findByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }

        if (!bindingResult.hasErrors() && !Objects.isNull(user.getCompany())) {
            userService.save(user);
            //model.addAttribute("successMessage", "User has been registered successfully");
            //model.addAttribute("user", new User());
        }

        return "redirect:/main";
    }

/*    @GetMapping(value = "/admin/home")
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
    }*/
}
