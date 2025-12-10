package cl.web2.prueba3.prueba3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .logout().disable()
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/seleccionar-perfil").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/logout").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/estudiante/**").permitAll()
                .requestMatchers("/profesor/**").permitAll()
                .requestMatchers("/carrera/**").permitAll()
                .requestMatchers("/empresa/**").permitAll()
                .requestMatchers("/jefe/**").permitAll()
                .requestMatchers("/practica/**").permitAll()
                .anyRequest().permitAll()
            )
            .headers().frameOptions().disable();
        
        return http.build();
    }
}
