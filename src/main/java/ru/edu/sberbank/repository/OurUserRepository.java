package ru.edu.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.entity.OurUser;

import java.util.Optional;

@Component
public interface OurUserRepository extends JpaRepository<OurUser, Long> {
    Optional<OurUser> findByAuth(Auth auth);


}