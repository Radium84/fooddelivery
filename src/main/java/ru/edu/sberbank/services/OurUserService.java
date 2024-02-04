package ru.edu.sberbank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.repository.OurUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OurUserService {

    private final OurUserRepository ourUserRepository;

    @Autowired
    public OurUserService(OurUserRepository ourUserRepository) {
        this.ourUserRepository = ourUserRepository;
    }

    @Transactional(readOnly = true)
    public List<OurUser> getAllOurUsers() {
        return ourUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<OurUser> getOurUserById(Long id) {
        return ourUserRepository.findById(id);
    }
}