package com.seven.test.controller;

import com.seven.test.model.User;
import com.seven.test.service.CompanyService;
import com.seven.test.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = "/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);

        modelAndView.addObject("companies", companyService.getAll());

        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("companies", companyService.getAll());
        User userExists = userService.findByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors() || Objects.isNull(user.getCompany())) {
            modelAndView.setViewName("registration");
        } else {
            userService.save(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

   @GetMapping(value = "/admin/home")
    public ModelAndView home() throws NotFoundException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastname() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @GetMapping(value = "/user/home_user")
    public ModelAndView user_home() throws NotFoundException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastname() + " (" + user.getEmail() + ")");
        modelAndView.addObject("userMessage", "Content Available Only for Users with USER Role");
        modelAndView.setViewName("user/home_user");
        return modelAndView;
    }
/*    @GetMapping(value = "/user/home_user")
    public ModelAndView displayHomePage(ModelAndView modelAndView, Principal principal, HttpServletRequest request) {

        // Throws exception here
        //UserService userDetails = principal;

        //System.out.println(userDetails.get());

        // Tried this and it also throws exception
        // User cannot be cast to CustomUserDetails
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal1 = auth.getPrincipal();
        UserService user = (principal instanceof UserService) ? (UserService) principal : null;

        // Render template located at
        // src/main/resources/templates/dashboard.html
        modelAndView.setViewName("user/home_user");

        return modelAndView;
    }*/
}
