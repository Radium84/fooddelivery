package ru.edu.sberbank.services;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OurUserService {

    public OurUserService(OurUserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthService authService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    private final OurUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Transactional
    public OurUser createUser(OurUserRegisterDTO userDTO) {
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
    @Transactional
    public OurUserResponseDTO updateUser(Long id, OurUserRegisterDTO userDTO) {
        OurUser user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));
        user.setFirstname(userDTO.getFirstname());
        user.setMiddlename(userDTO.getMiddlename());
        user.setLastname(userDTO.getLastname());
        user.setAddress(userDTO.getAddress());
        user.setBirthday(userDTO.getBirthday());
        boolean usernamePresent = userDTO.getUsername() != null && !userDTO.getUsername().trim().isEmpty();
        boolean passwordPresent = userDTO.getPassword() != null && !userDTO.getPassword().trim().isEmpty();
        if (usernamePresent && !passwordPresent) {
            throw new IllegalArgumentException("Пароль должен быть указан, если задано имя пользователя.");
        }
        if (usernamePresent) {
            Auth auth = createAuth(userDTO.getUsername(), userDTO.getPassword());
            user.setAuth(auth);
        }

        OurUser savedUser = userRepository.save(user);
        return toDTO(savedUser);

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
    @Transactional(readOnly = true)
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

