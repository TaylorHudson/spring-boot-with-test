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
    private static final String ADMIN = "ADMIN";
    private static final String MANAGER = "MANAGER";
    private static final String EMPLOYEE = "EMPLOYEE";
    private static final String[] EMPLOYEE_MANAGER_ADMIN = {EMPLOYEE,MANAGER,ADMIN};
    private static final String[] MANAGER_ADMIN = {MANAGER, ADMIN};

    @Bean
    public UserDetailsManager userDetailsManager(DataSource source) {
        return new JdbcUserDetailsManager(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic();
        http.csrf().disable();

        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, BASE_URI_EMPLOYEES).hasAnyRole(EMPLOYEE_MANAGER_ADMIN)
            .requestMatchers(HttpMethod.GET, BASE_URI_EMPLOYEES +"/**").hasAnyRole(EMPLOYEE_MANAGER_ADMIN)
            .requestMatchers(HttpMethod.POST, BASE_URI_EMPLOYEES).hasAnyRole(MANAGER_ADMIN)
            .requestMatchers(HttpMethod.PUT, BASE_URI_EMPLOYEES).hasAnyRole(MANAGER_ADMIN)
            .requestMatchers(HttpMethod.DELETE, BASE_URI_EMPLOYEES + "/{id}").hasAnyRole(ADMIN);

        return http.build();
    }

}
