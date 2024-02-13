package ru.edu.sberbank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.repository.AuthRepository;

@Service
public class AuthService {
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    @Transactional(readOnly = true)
    public Auth findAuthByUsername(String username) {
        return authRepository.findByUsername(username);
    }
}
