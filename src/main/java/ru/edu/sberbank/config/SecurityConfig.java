package ru.edu.sberbank.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.edu.sberbank.jwt.JwtAuthorizationFilter;
import ru.edu.sberbank.jwt.JwtProviderImpl;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
<<<<<<< HEAD:server/main/java/ru/edu/sberbank/config/SecurityConfig.java
                .cors().and()  // Включаем поддержку CORS и используем конфигурацию по умолчанию для всех эндпоинтов
                .csrf().disable() // Отключаем CSRF защиту
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().permitAll()) // Разрешаем все запросы без аутентификации
                .httpBasic(Customizer.withDefaults()); // Включаем HTTP Basic аутентификацию (если требуется)
=======
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/authentication/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/api/authentication/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/discounts", "/api/products", "/api/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/discounts/*", "/api/products/*", "/api/categories/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/discounts", "/api/products", "/api/categories").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/discounts/*", "/api/products/*", "/api/categories/*").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/discounts/*", "/api/products/*", "/api/categories/*").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/orders").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/api/orders/*").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/api/orders").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users/*").hasAuthority("ROLE_USER")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
>>>>>>> master:src/main/java/ru/edu/sberbank/config/SecurityConfig.java
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
<<<<<<< HEAD:server/main/java/ru/edu/sberbank/config/SecurityConfig.java
=======

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(new JwtProviderImpl(JWT_SECRET, JWT_EXPIRATION_IN_MS));
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
>>>>>>> master:src/main/java/ru/edu/sberbank/config/SecurityConfig.java
}
