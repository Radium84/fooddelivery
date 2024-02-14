package ru.edu.sberbank.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.jwt.CustomUserPrincipal;
import ru.edu.sberbank.jwt.SecurityUtil;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthService authService;
    private final OurUserService ourUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth = authService.findAuthByUsername(username);
        OurUser ourUser = ourUserService.findOurUserByAuth(auth).get();

        Set<GrantedAuthority> authorities = auth.getRoles().stream()
                .map(role -> SecurityUtil.convertToAuthority(role.getName()))
                .collect(Collectors.toSet());

        return CustomUserPrincipal.builder()
                .user(ourUser)
                .id(ourUser.getId())
                .username(username)
                .password(auth.getPassword())
                .authorities(authorities)
                .build();
    }
}