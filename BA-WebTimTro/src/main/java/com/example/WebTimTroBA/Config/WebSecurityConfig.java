package com.example.WebTimTroBA.Config;

import com.example.WebTimTroBA.Filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(
                                    "/login", // Cho phép /login truy cập không cần xác thực
                                    "/register",
                                    "/static/**",
                                    "/search/**",
                                    "/users/login",
                                    "/dashboard",
                                    "/motel/{id}",
                                    "/delete/**"
                            ).permitAll()
                            .requestMatchers(GET, "/admin/**").hasRole("ADMIN")
                            .requestMatchers(GET, "/get-info").hasRole("USER")
                            .requestMatchers(POST, "/account/**").hasRole("USER")
                            .requestMatchers(POST, "/changePassword").hasRole("USER")
                            .requestMatchers(POST, "/create").hasRole("USER")
                            .anyRequest().authenticated();
                })
                .authenticationProvider(authenticationProvider);

        return http.build();
    }
}
