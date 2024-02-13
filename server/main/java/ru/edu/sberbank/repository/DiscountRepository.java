package ru.edu.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.sberbank.entity.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}