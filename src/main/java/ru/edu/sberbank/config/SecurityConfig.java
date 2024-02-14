package ru.edu.sberbank.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.jwt.JwtAuthorizationFilter;
import ru.edu.sberbank.jwt.JwtProviderImpl;
import ru.edu.sberbank.repository.AuthRepository;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AuthRepository authRepository;
    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/authentication/**").permitAll()
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
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // указываем адрес react приложения
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","OPTIONS"));
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
}
