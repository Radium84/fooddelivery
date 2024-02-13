package ru.edu.sberbank.services;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.jwt.CustomUserPrincipal;
import ru.edu.sberbank.jwt.JwtProvider;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public OurUser signInAndReturnJwt(OurUser signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getAuth().getUsername(),
                        signInRequest.getAuth().getPassword()));

        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(customUserPrincipal);

        OurUser signInUser = customUserPrincipal.getUser();
        signInUser.setToken(jwt);

        return signInUser;
    }
}