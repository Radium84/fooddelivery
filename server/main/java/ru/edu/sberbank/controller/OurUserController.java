package ru.edu.sberbank.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.entity.dto.OurUserRegisterDTO;
import ru.edu.sberbank.entity.dto.OurUserResponseDTO;
import ru.edu.sberbank.services.OurUserService;

@RestController
@RequestMapping("/api/users")
public class OurUserController {

    private final OurUserService ourUserService;

    @Autowired
    public OurUserController(OurUserService ourUserService) {
        this.ourUserService = ourUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OurUserResponseDTO> getUserById(@PathVariable Long id) {
        OurUserResponseDTO ourUser;
        try {
            ourUser = ourUserService.getUserById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ourUser);
    }

    @PostMapping("/register")
    public ResponseEntity<OurUser> registerUser(@RequestBody OurUserRegisterDTO userDTO) {
        OurUser registeredUser = ourUserService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
}