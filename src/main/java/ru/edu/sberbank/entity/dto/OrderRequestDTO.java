package ru.edu.sberbank.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private Long ourUsersId;
    private HashMap<Long, Integer> productQuantity;
}
