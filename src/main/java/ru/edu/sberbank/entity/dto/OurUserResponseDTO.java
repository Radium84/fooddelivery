package ru.edu.sberbank.entity.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class OurUserResponseDTO {
    private Long id;
    private String firstname;
    private String middlename;
    private String lastname;
    private String address;
    private LocalDateTime birthday;
    private List<Long> favoriteProducts;
    private String username;


}
