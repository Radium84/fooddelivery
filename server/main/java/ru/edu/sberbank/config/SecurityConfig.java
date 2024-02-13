package ru.edu.sberbank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.repository.AuthRepository;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AuthRepository authRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Auth auth = authRepository.findByUsername(username);
            if (auth == null) {
                throw new UsernameNotFoundException("Пользователь " + username + " не найден.");
            }
            return new org.springframework.security.core.userdetails.User(
                    auth.getUsername(),
                    auth.getPassword(),
                    auth.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .toList());
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()  // Включаем поддержку CORS и используем конфигурацию по умолчанию для всех эндпоинтов
                .csrf().disable() // Отключаем CSRF защиту
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().permitAll()) // Разрешаем все запросы без аутентификации
                .httpBasic(Customizer.withDefaults()); // Включаем HTTP Basic аутентификацию (если требуется)
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.setAllowCredentials(true); // Если вам нужно поддерживать куки сессий / авторизации
        config.addAllowedOriginPattern("*"); // Разрешаем все источники
        config.addAllowedHeader("*"); // Разрешаем все заголовки
        config.addAllowedMethod("*"); // Разрешаем все методы запросов
        source.registerCorsConfiguration("/**", config); // Применяем конфигурацию ко всем путям
        return new CorsFilter(source);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
