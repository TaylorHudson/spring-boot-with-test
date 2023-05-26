package com.springcoursesecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class WebConfiguration {

    private static final String BASE_URI_EMPLOYEES = "/api/v1/employees";

    @Bean
    public UserDetailsManager userDetailsManager(DataSource source) {
        return new JdbcUserDetailsManager(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic();
        http.csrf().disable();

        http.authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, BASE_URI_EMPLOYEES).hasRole("EMPLOYEE")
            .requestMatchers(HttpMethod.GET, BASE_URI_EMPLOYEES +"/**").hasRole("EMPLOYEE")
            .requestMatchers(HttpMethod.POST, BASE_URI_EMPLOYEES).hasRole("MANAGER")
            .requestMatchers(HttpMethod.PUT, BASE_URI_EMPLOYEES).hasRole("MANAGER")
            .requestMatchers(HttpMethod.DELETE, BASE_URI_EMPLOYEES + "/{id}").hasRole("ADMIN");

        return http.build();
    }

}
