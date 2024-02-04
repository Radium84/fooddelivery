package ru.edu.sberbank.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.edu.sberbank.entity.Category;
import ru.edu.sberbank.entity.Discount;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Category category;
    private String composition;
    private String description;
    private Integer price;
    private Boolean isDiscount;
    private Discount discount;
    private Integer calculatedPrice;
}
