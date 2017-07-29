package com.seven.test.configuration;

import com.seven.test.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// based on https://medium.com/@gustavo.ponce.ch/spring-boot-spring-mvc-spring-security-mysql-a5d8545d837d
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationSuccessHandler successHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(PasswordUtil.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                    .antMatchers("/login", "/registration").permitAll()
                    .antMatchers("/main", "/fragments/**", "/user/**").hasAnyAuthority("COMPANY_OWNER", "COMPANY_EMPLOYER", "ADMIN")
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .csrf().disable()
                .formLogin()
                    .loginPage("/login").failureUrl("/login?error=true")
                    .defaultSuccessUrl("/main")
                    // раскидать разные роли по разным страницам
                    //.successHandler(successHandler)
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login")
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/webjars/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }


}