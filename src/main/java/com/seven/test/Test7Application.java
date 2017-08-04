package com.seven.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Test7Application {
    @Autowired
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        SpringApplication.run(Test7Application.class, args);

    }

    // see all beans in context
    //https://keyholesoftware.com/2017/01/30/spring-boot-the-right-boot-for-you/
/*    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }*/
}
