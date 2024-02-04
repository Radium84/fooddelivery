package ru.edu.sberbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.services.OurUserService;

import java.util.List;

@RestController
@RequestMapping("/api/our_users")
public class OurUserController {

    private final OurUserService ourUserService;

    @Autowired
    public OurUserController(OurUserService ourUserService) {
        this.ourUserService = ourUserService;
    }

    @GetMapping
    public ResponseEntity<List<OurUser>> getAllOurUsers() {
        List<OurUser> ourUsers = ourUserService.getAllOurUsers();
        return ResponseEntity.ok(ourUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OurUser> getOurUserById(@PathVariable Long id) {
        return ourUserService.getOurUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}