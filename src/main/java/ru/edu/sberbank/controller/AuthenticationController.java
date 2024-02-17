package ru.edu.sberbank.controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.entity.dto.OurUserRegisterDTO;
import ru.edu.sberbank.services.AuthenticationService;
import ru.edu.sberbank.services.OurUserService;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Log
public class AuthenticationController {
    private final OurUserService ourUserService;
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody Auth auth) {
        return new ResponseEntity<>(authenticationService.signInAndReturnJwt(auth), HttpStatus.OK);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<OurUser> registerUser(@RequestBody OurUserRegisterDTO userDTO) {
        OurUser registeredUser = ourUserService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
}
