package com.deltabook.security;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2Config {

    // workaround for Spring Boot 4.1.0
    @Bean
    public ServletRegistrationBean<?> h2Servlet() {
        ServletRegistrationBean<?> bean =
                new ServletRegistrationBean<>(new JakartaWebServlet());

        bean.addUrlMappings("/h2-console/*");
        return bean;
    }
}
