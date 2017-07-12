package com.seven.test.configuration;

import com.seven.test.util.converter.DateTimeFormatters;
import com.seven.test.util.json.JacksonObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MimeType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public StringHttpMessageConverter customStringHttpMessageConverter()
    {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(
                new ArrayList(
                        Arrays.asList(
                                MimeType.valueOf("text/plain;charset=UTF-8"),
                                MimeType.valueOf("text/html;charset=UTF-8"))
                )
        );
        return stringHttpMessageConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateTimeFormatters.LocalDateFormatter());
        registry.addFormatter(new DateTimeFormatters.LocalTimeFormatter());
        super.addFormatters(registry);
    }

/*    @Bean
    public FormattingConversionServiceFactoryBean conversionServiceFactoryBean(){
        FormattingConversionServiceFactoryBean conversionService = new FormattingConversionServiceFactoryBean();
        conversionService.setFormatters(new HashSet<>(Arrays.asList(
                localDateFormatter,
                localTimeFormatter )));
        return conversionService;
    }*/
}
