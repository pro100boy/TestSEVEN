package com.seven.test.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.seven.test.util.Patterns.*;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MessageSource messageSource;

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = {"/", "/main"})
    public String main(Model model) {
        // for modal forms
        model.addAttribute("emailpattern", EMAIL_PATTERN);
        model.addAttribute("phonepattern", PHONE_PATTERN);
        model.addAttribute("passwdpattern", PASSWORD_PATTERN);
        return "main";
    }

    @GetMapping(value = "/i18n", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, String> getMessages() {
        log.info("i18n");
        return Collections.unmodifiableMap(new HashMap<String, String>() {{
            put("common.saved", messageSource.getMessage("common.saved", null, LocaleContextHolder.getLocale()));
            put("common.deleted", messageSource.getMessage("common.deleted", null, LocaleContextHolder.getLocale()));
            put("common.cancel", messageSource.getMessage("common.cancel", null, LocaleContextHolder.getLocale()));
            put("company.select", messageSource.getMessage("company.select", null, LocaleContextHolder.getLocale()));
            put("common.delete", messageSource.getMessage("common.delete", null, LocaleContextHolder.getLocale()));
            put("common.caption", messageSource.getMessage("common.caption", null, LocaleContextHolder.getLocale()));
        }});
    }
}