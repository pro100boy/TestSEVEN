package com.seven.test.configuration;

import com.seven.test.util.formatter.DateTimeFormatters;
import com.seven.test.util.json.JacksonObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
        converters.add(customStringHttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(JacksonObjectMapper.getMapper());
        return jsonConverter;
    }

    @Bean
    public StringHttpMessageConverter customStringHttpMessageConverter() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(
                Arrays.asList(
                        new MediaType("text", "plain", StandardCharsets.UTF_8),
                        new MediaType("text", "html", StandardCharsets.UTF_8))
        );
        return stringHttpMessageConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateTimeFormatters.LocalDateFormatter());
        registry.addFormatter(new DateTimeFormatters.LocalTimeFormatter());
        super.addFormatters(registry);
    }

    // Internationalization beans
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/locale/messages");
        messageSource.setCacheSeconds(60);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

/*
    @Autowired
    private Environment env;

    @Bean
    public JavaMailSenderImpl emailSender() {
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        emailSender.setHost(env.getProperty("spring.mail.host"));
        emailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));
        emailSender.setUsername(env.getProperty("spring.mail.username"));
        emailSender.setPassword(env.getProperty("spring.mail.password"));
        emailSender.setProtocol(env.getProperty("spring.mail.protocol"));

        Properties mailProps = new Properties();
        //mailProps.setProperty("spring.mail.default-encoding", env.getProperty("spring.mail.default-encoding"));
        //mailProps.setProperty("mail.transport.protocol",env.getProperty("spring.mail.protocol"));
        mailProps.setProperty("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth"));
        mailProps.setProperty("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
        mailProps.setProperty("mail.smtp.starttls.required", env.getProperty("spring.mail.properties.mail.smtp.starttls.required"));
        mailProps.setProperty("mail.debug", "false");
        emailSender.setJavaMailProperties(mailProps);

        return emailSender;
    }*/
}
