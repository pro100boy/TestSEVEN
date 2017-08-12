package com.seven.test;

import com.seven.test.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * GKislin
 * 05.01.2015.
 */
public class TestUtil {

    public static ResultActions print(ResultActions action) throws UnsupportedEncodingException {
        System.out.println(getContent(action));
        return action;
    }

    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    public static void mockAuthorize(User user) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(new AuthorizedUser(user), null, user.getRoles()));
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

    public static RequestPostProcessor userAuth(User user) {
        return SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    }

    public static String buildUrlEncodedFormEntity(String... params) throws UnsupportedEncodingException {
        if ((params.length % 2) > 0) {
            throw new IllegalArgumentException("Need to give an even number of parameters");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < params.length; i += 2) {
            if (i > 0) result.append('&');
            result.append(URLEncoder.encode(params[i], "UTF-8"))
                    .append('=')
                    .append(URLEncoder.encode(params[i + 1], "UTF-8"));
        }
        return result.toString();
    }
}
