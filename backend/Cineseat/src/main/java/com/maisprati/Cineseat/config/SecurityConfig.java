package com.maisprati.Cineseat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desativa  o CSRF
                .cors(cors -> {})             // habilita o CORS
                .logout(logout -> logout.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml" ).permitAll() // libera o Swagger e OpenAPI
                        .requestMatchers("/api/avaliacoes/create").authenticated() //a ser criado ainda
                        .requestMatchers("/api/users/register", "/api/users/login", "/api/users/logout").permitAll()
                        .anyRequest().permitAll() //qualquer outra pagina fica pública
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
