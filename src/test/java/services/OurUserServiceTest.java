package services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.entity.Product;
import ru.edu.sberbank.entity.dto.OurUserRegisterDTO;
import ru.edu.sberbank.entity.dto.OurUserResponseDTO;
import ru.edu.sberbank.exceptions.ResourceNotFoundException;
import ru.edu.sberbank.repository.OurUserRepository;
import ru.edu.sberbank.repository.RoleRepository;
import ru.edu.sberbank.services.AuthService;
import ru.edu.sberbank.services.OurUserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OurUserServiceTest {
    @Mock
    private OurUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthService authService;

    @InjectMocks
    private OurUserService userService;
    @Test
    void testCreateUser() {
        OurUserRegisterDTO userDTO = new OurUserRegisterDTO();
        userDTO.setUsername("testUser");
        userDTO.setPassword("password");
        // Добавьте поля userDTO здесь

        OurUser expectedUser = new OurUser();
        expectedUser.setAuth(new Auth());

        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(authService.isAdmin(anyString())).thenReturn(false);
        when(userRepository.save(any(OurUser.class))).thenReturn(expectedUser);

        OurUser result = userService.createUser(userDTO);

        assertNotNull(result);
        verify(userRepository).save(any(OurUser.class));
        verify(passwordEncoder).encode("password");
    }
    @Test
    void testUpdateUser() {
        OurUserRegisterDTO userDTO = new OurUserRegisterDTO();
        userDTO.setUsername("updatedUsername");
        userDTO.setPassword("updatedPassword");
        // Добавьте остальные поля userDTO здесь

        OurUser existingUser = new OurUser();
        existingUser.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(anyString())).thenReturn("hashedUpdatedPassword");
        when(userRepository.save(any(OurUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OurUserResponseDTO result = userService.updateUser(1L, userDTO);

        assertNotNull(result);
        verify(userRepository).save(any(OurUser.class));
        verify(passwordEncoder).encode("updatedPassword");
    }

    @Test
    public void getUserById_ShouldReturnUser_WhenUserExists() {
        Long userId = 1L;
        OurUser user = new OurUser();
        user.setId(userId);

        user.setFavoriteProducts(List.of(new Product()));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        OurUserResponseDTO response = userService.getUserById(userId);
        assertNotNull(response);
        assertEquals(userId, response.getId()); // предполагая, что в вашем DTO есть такой же идентификатор
        // Другие проверки для полей DTO
    }

    @Test
    public void getUserById_ShouldThrowException_WhenUserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
    }
    @Test
    public void findOurUserByAuth_ReturnsUser_WhenUserExists() {
        Auth auth = new Auth();
        auth.setUsername("userTest");

        OurUser user = new OurUser();
        user.setAuth(auth);

        when(userRepository.findByAuth(auth)).thenReturn(Optional.of(user));

        OurUser result = userService.findOurUserByAuth(auth);

        assertNotNull(result);
        assertEquals(auth.getUsername(), result.getAuth().getUsername());
    }

    @Test
    public void findOurUserByAuth_ThrowsException_WhenUserNotFound() {
        Auth auth = new Auth();
        auth.setUsername("userTest");

        when(userRepository.findByAuth(auth)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findOurUserByAuth(auth));
    }
}
