package ru.edu.sberbank.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    Long OurUserID;
    HashMap<Long, Double> productWithDiscount;
    Long orderId;
    Double totalPrice;

}
