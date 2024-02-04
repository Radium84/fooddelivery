package ru.edu.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.sberbank.entity.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findByUsername(String username);
}
