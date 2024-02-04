package ru.edu.sberbank.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private OurUser user;

    @Column(name = "orderdate", nullable = false)
    private LocalDateTime orderdate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "finalized")
    private Boolean finalized;


}