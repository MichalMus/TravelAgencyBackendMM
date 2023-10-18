//package com.example.demo.auth;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class AuthConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.httpBasic()
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/home").permitAll()
//                .antMatchers("/user").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
//                .antMatchers("/admin").hasAnyAuthority("ROLE_ADMIN");
//        return httpSecurity.build();
//    }
//
//
//}
