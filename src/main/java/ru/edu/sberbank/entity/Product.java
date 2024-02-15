package ru.edu.sberbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "composition")
    private String composition;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "is_discount",nullable = false)
    private Boolean isDiscount;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ManyToMany(mappedBy = "favoriteProducts",fetch = FetchType.EAGER)
    private List<OurUser> usersWhoFavorited;



}