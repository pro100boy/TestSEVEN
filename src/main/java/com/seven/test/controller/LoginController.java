package com.seven.test.controller;

import com.seven.test.util.Patterns;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class LoginController {
    private final Logger log = LoggerFactory.getLogger(getClass());

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

    @Autowired
    MessageSource messageSource;

    @GetMapping(value = "/myajax")
    @ResponseBody
    public List<String> getMyAjaxMessage() {
        log.info("getMyAjaxMessage()");
        return Arrays.asList(
                messageSource.getMessage("user.save", null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("user.name", null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("user.lastname", null, LocaleContextHolder.getLocale())
        );
    }

    @GetMapping(value = "/myajaxmap")
    @ResponseBody
    public Map<String, String> getMyAjaxMessageMap() {
        log.info("getMyAjaxMessageMap()");
        return Collections.unmodifiableMap(new HashMap<String, String>() {{
            put("user.save", messageSource.getMessage("user.save", null, LocaleContextHolder.getLocale()));
            put("user.name", messageSource.getMessage("user.name", null, LocaleContextHolder.getLocale()));
            put("user.lastname", messageSource.getMessage("user.lastname", null, LocaleContextHolder.getLocale()));
        }});
    }
}
