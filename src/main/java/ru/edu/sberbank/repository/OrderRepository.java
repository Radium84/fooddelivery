package ru.edu.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.sberbank.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}