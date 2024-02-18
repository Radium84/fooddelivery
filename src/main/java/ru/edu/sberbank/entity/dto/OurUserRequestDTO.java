package ru.edu.sberbank.entity.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class OurUserRequestDTO {
    private String firstname;
    private String middlename;
    private String lastname;
    private String address;
    private LocalDateTime birthday;
    private String username;
    private String password;

}
