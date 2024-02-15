package ru.edu.sberbank.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.repository.AuthRepository;

import java.util.Optional;

@Service
public class AuthService {
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Transactional(readOnly = true)
    public Auth findAuthByUsername(String username) {
        return authRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Transactional(readOnly = true)
    public Boolean isAdmin(String username) {
        Optional<Auth> authOptional = authRepository.findByUsername(username);
        if (authOptional.isEmpty()) {
            return false;
        }
        Auth auth = authOptional.get();
        return auth.getRoles().stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
    }


}
