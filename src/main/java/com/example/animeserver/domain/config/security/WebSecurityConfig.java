package com.example.animeserver.domain.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                .requestMatchers(
                        "/api/v1/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                ).permitAll()

                .requestMatchers(HttpMethod.DELETE,"/api/user/delete").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/user/update").hasRole("USER")
                .requestMatchers(HttpMethod.POST,"/api/user/create").permitAll()

                .requestMatchers(HttpMethod.POST,"/api/anime/add").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/anime").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/anime/category").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/anime/name").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/anime/delete/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/anime/addCategory/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/anime/removeCategory/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/anime/update/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/anime/comment/**").hasAnyRole("ADMIN","USER")


                .requestMatchers(HttpMethod.POST,"/api/category/add").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/category").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/api/category/delete/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET,"/api/comment/all").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.DELETE,"/api/comment/delete/**").hasAnyRole("ADMIN","USER")

                .requestMatchers(HttpMethod.POST,"/api/episodes/create").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/episode/delete/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/episode/like").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.POST,"/api/episode/addComment").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.PUT,"/api/episode/update/**").hasRole("ADMIN")

                .anyRequest().authenticated().and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}
