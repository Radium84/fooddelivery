package ru.edu.sberbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "our_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OurUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "middlename")
    private String middlename;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "birthday", nullable = false)
    private LocalDateTime birthday;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_favorite_products", // Имя промежуточной таблицы
            joinColumns = @JoinColumn(name = "user_id"), // Столбец в промежуточной таблице, соответствующий ID пользователя
            inverseJoinColumns = @JoinColumn(name = "product_id") // Столбец в промежуточной таблице, соответствующий ID продукта
    )
    private List<Product> favoriteProducts; // Поле будет содержать список любимых товаров пользователя

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_id", referencedColumnName = "id")
    private Auth auth; // добавленное поле для связи с записью Auth


}