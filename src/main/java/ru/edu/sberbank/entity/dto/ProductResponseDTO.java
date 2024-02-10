package ru.edu.sberbank.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.edu.sberbank.entity.Category;
import ru.edu.sberbank.entity.Discount;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String categoryName;
    private String composition;
    private String description;
    private Integer price;
    private Boolean isDiscount;
    private String discountName;
    private Integer calculatedPrice;

}
