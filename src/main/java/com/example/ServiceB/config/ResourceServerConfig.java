package com.example.ServiceB.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfig {

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http.authorizeRequests()
    //             .mvcMatchers("/employee/**").hasAnyAuthority("SCOPE_employee.read")
    //             .mvcMatchers("/employee/**").hasAnyAuthority("SCOPE_employee.admin")
    //             .mvcMatchers("/home/**").permitAll()
    //             .anyRequest().authenticated()
    //             .and()
    //             .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

    //     return http.build();
    // }
}
