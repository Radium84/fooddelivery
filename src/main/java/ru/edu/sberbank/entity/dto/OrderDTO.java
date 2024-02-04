package ru.edu.sberbank.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    Long OurUserID;
    HashMap<Long, Integer> productQuantity;
}
