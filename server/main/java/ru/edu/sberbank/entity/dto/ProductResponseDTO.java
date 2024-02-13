package ru.edu.sberbank.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Long categoryId;
    private String composition;
    private String description;
    private Integer price;
    private Boolean isDiscount;
    private String discountName;
    private Integer calculatedPrice;

}
