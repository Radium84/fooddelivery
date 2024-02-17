package ru.edu.sberbank.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
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
@Log
public class OurUserController {

    private final OurUserService ourUserService;

    @Autowired
    public OurUserController(OurUserService ourUserService) {
        this.ourUserService = ourUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OurUserResponseDTO> getUserById(@PathVariable Long id) {
        OurUserResponseDTO ourUser = ourUserService.getUserById(id);
        return ResponseEntity.ok(ourUser);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OurUserResponseDTO> updateUser(@PathVariable Long id, @RequestBody OurUserRegisterDTO userDTO) {
        OurUserResponseDTO updatedUser = ourUserService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);

    }

}