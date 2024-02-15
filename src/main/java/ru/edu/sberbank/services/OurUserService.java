package ru.edu.sberbank.services;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.entity.Product;
import ru.edu.sberbank.entity.Role;
import ru.edu.sberbank.entity.dto.OurUserRegisterDTO;
import ru.edu.sberbank.entity.dto.OurUserResponseDTO;
import ru.edu.sberbank.exceptions.ResourceNotFoundException;
import ru.edu.sberbank.repository.OurUserRepository;
import ru.edu.sberbank.repository.RoleRepository;

import java.util.Collections;
import java.util.List;


@Service
@AllArgsConstructor
public class OurUserService {


    private final OurUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Transactional
    public OurUser registerUser(OurUserRegisterDTO userDTO) {
        OurUser ourUser = new OurUser();
        ourUser.setFirstname(userDTO.getFirstname());
        ourUser.setMiddlename(userDTO.getMiddlename());
        ourUser.setLastname(userDTO.getLastname());
        ourUser.setAddress(userDTO.getAddress());
        ourUser.setBirthday(userDTO.getBirthday());
        Auth auth = createAuth(userDTO.getUsername(), userDTO.getPassword());
        ourUser.setAuth(auth);
        ourUser.setIsAdmin(authService.isAdmin(userDTO.getUsername()));
        return userRepository.save(ourUser);

    }

    private Auth createAuth(String username, String password) {
        Auth auth = new Auth();
        auth.setUsername(username);
        auth.setPassword(passwordEncoder.encode(password));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newUserRole = new Role();
                    newUserRole.setName("ROLE_USER");
                    return roleRepository.save(newUserRole);
                });

        auth.setRoles(Collections.singleton(userRole));

        return auth;
    }

    @Transactional(readOnly = true)
    public OurUserResponseDTO getUserById(Long id) {
        OurUser user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        // После загрузки объекта пользователя из базы данных инициализируем список избранных продуктов
        Hibernate.initialize(user.getFavoriteProducts());

        // Преобразование OurUser в OurUserResponseDTO
        return toDTO(user);
    }

    public OurUser findOurUserByAuth(Auth auth) {
        return userRepository.findByAuth(auth).orElseThrow(() ->
                new ResourceNotFoundException("User not found with username: " + auth.getUsername()));
    }


    private OurUserResponseDTO toDTO(OurUser user) {
        OurUserResponseDTO dto = new OurUserResponseDTO();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setMiddlename(user.getMiddlename());
        dto.setLastname(user.getLastname());
        dto.setAddress(user.getAddress());
        dto.setBirthday(user.getBirthday());

        // Получение и установка username из сущности Auth, связанной с пользователем
        if (user.getAuth() != null) {
            dto.setUsername(user.getAuth().getUsername());
        }
        if (user.getFavoriteProducts() != null) {
            List<Long> favoriteProductNames = user.getFavoriteProducts().stream()
                    .map(Product::getId)
                    .toList();
            dto.setFavoriteProducts(favoriteProductNames);
        }

        return dto;
    }
}

