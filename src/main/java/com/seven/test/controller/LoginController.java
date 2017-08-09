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

import javax.annotation.PostConstruct;
import java.util.*;

@Controller
public class LoginController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MessageSource messageSource;

    private static Map<String, String> i18n;

    @PostConstruct
    void i18nInit() {
        i18n = Collections.unmodifiableMap(new HashMap<String, String>() {{
            put("common.saved", messageSource.getMessage("common.saved", null, LocaleContextHolder.getLocale()));
            put("common.deleted", messageSource.getMessage("common.deleted", null, LocaleContextHolder.getLocale()));
            put("common.cancel", messageSource.getMessage("common.cancel", null, LocaleContextHolder.getLocale()));
            put("company.select", messageSource.getMessage("company.select", null, LocaleContextHolder.getLocale()));
            put("common.delete", messageSource.getMessage("common.delete", null, LocaleContextHolder.getLocale()));
            put("common.captiondlg", messageSource.getMessage("common.captiondlg", null, LocaleContextHolder.getLocale()));
        }});
    }

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

    @GetMapping(value = "/i18n")
    @ResponseBody
    public Map<String, String> getMyAjaxMessageMap() {
        log.info("i18n");
        return i18n;
    }
}