package ru.edu.sberbank.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.edu.sberbank.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class OurUserRegisterDTO {
    private String firstname;
    private String middlename;
    private String lastname;
    private String address;
    private LocalDateTime birthday;
    private String username;
    private String password;


}
