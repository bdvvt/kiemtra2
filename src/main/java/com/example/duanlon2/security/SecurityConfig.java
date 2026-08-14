package com.example.duanlon2.security;

import com.example.duanlon2.security.exceptions.AccessDenied;
import com.example.duanlon2.security.jwt.JwtTokenFilter;
import com.example.duanlon2.security.exceptions.JwtEntryPoint;
import com.example.duanlon2.security.principal.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JwtEntryPoint entryPoint;
    private final AccessDenied accessDenied;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http
                 .csrf(AbstractHttpConfigurer::disable)
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers(
                                 "/api/auth/active-user",
                                 "/api/auth/login",
                                 "/api/auth/register",
                                 "/api/auth/logout"
                         ).permitAll()
                         .requestMatchers("/api/auth/me/**").authenticated()
                         .requestMatchers("/api/auth/verify/**").authenticated()
                         .requestMatchers("/api/users/**").hasAuthority("ADMIN")
                         .requestMatchers(HttpMethod.POST, "/api/courses").hasAuthority("ADMIN")
                         .requestMatchers(HttpMethod.POST, "/api/courses/*/lessons").hasAnyAuthority("ADMIN","TEACHER")
                         .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasAuthority("ADMIN")
                         .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasAuthority("ADMIN")
                         .requestMatchers(HttpMethod.GET,"/api/courses/*/lessons").authenticated()
                         .requestMatchers(HttpMethod.GET, "/api/lessons/**").authenticated()
                         .requestMatchers(HttpMethod.PUT, "/api/lessons/**").hasAnyAuthority("ADMIN","TEACHER")
                         .requestMatchers(HttpMethod.DELETE, "/api/lessons/**").hasAnyAuthority("ADMIN","TEACHER")
                         .requestMatchers("/api/enrollments/**").hasAuthority("STUDENT")
                         .requestMatchers(HttpMethod.PUT, "/api/enrollments/*/complete_lesson/*").hasAuthority("STUDENT")
                         .requestMatchers(HttpMethod.PUT, "/api/users/*/password").hasAnyAuthority("ADMIN","OWNER")
                         .requestMatchers(HttpMethod.PUT, "/api/users").hasAnyAuthority("ADMIN","OWNER")
                         .requestMatchers(HttpMethod.GET, "/api/courses/**").authenticated()
                         .requestMatchers(HttpMethod.POST, "/api/notifications/**").hasAuthority("ADMIN")
                         .requestMatchers(HttpMethod.GET, "/api/notifications/**").authenticated()
                         .requestMatchers(HttpMethod.PUT, "/api/notifications/*/read").authenticated()
                 )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDenied))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
