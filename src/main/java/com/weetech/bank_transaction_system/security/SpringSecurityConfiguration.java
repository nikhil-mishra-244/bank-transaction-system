package com.weetech.bank_transaction_system.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        Authenticate Every Request
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

//        not Authenticate web page shown
        http.httpBasic(withDefaults());

//      Disabled CSRF   => It not allowed to POST , PUT method
        http.csrf(csrf -> csrf.disable());


        return http.build();

    }

}
