package ru.edu.sberbank.services;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.jwt.CustomUserPrincipal;
import ru.edu.sberbank.jwt.JwtProvider;


@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    @Override
    public OurUser signInAndReturnJwt(Auth signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),
                        signInRequest.getPassword()));

        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(customUserPrincipal);

        OurUser signInUser = customUserPrincipal.getUser();
        signInUser.setToken(jwt);

        signInUser.setIsAdmin(authService.isAdmin(signInRequest.getUsername()));


        return signInUser;
    }
}