package com.srs.SpringSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  // overrides default spring security config
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    // defining custom security filter rather than default by spring
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(customizer -> customizer.disable()).
                authorizeHttpRequests(request -> request.
                        requestMatchers("/register", "/login").
                        permitAll().
                        anyRequest().authenticated()). // default auth - userPasswordAuthentication
                httpBasic(Customizer.withDefaults()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                // adding custom filters before
                addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).
                build();

//        http.csrf(customizer -> customizer.disable()); // disabling csrf, handling functional interface with lambda
//        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());  // localhost access denied without un and pwd
////      http.formLogin(Customizer.withDefaults());  // enable access to un and pwd for auth, else access denied even with un and pwd, gives login by default, no logout form
//        http.httpBasic(Customizer.withDefaults());  // for REST api access
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // once stateless, new session id every refresh, requires disable http.formLogin for route access

//        return http.build();
    }

//    @Bean  // UserDetailsService - default user management by spring
//    public UserDetailsService userDetailsService() {
//
//        // hardcoding users:
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder() // depricated method
//                .username("user1")
//                .password("user1pwd")
//                .roles("USER")
//                .build();   // returns object of UserDetails
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("user2")
//                .password("user2pwd")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2); // implements UserDetailsService
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // auth provider for database, overrides default:
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // no password encoder while verifying pwd for auth
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);  // creating custom userDetailsProvider
        return provider;
    }

    @Bean   // control auth manager, JWT implementation
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}


// without EnableWebSec and Bean tags spring sec will override (login page visible)