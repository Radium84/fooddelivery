package services;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.entity.Role;
import ru.edu.sberbank.repository.AuthRepository;
import ru.edu.sberbank.services.AuthService;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void findAuthByUsername_whenUserExist_shouldReturnAuth() {
        // Arrange
        Auth dummyAuth = new Auth();
        dummyAuth.setUsername("existinguser");
        when(authRepository.findByUsername("existinguser")).thenReturn(Optional.of(dummyAuth));

        // Act
        Auth result = authService.findAuthByUsername("existinguser");

        // Assert
        assertThat(result).isEqualTo(dummyAuth);
    }

    @Test
    void findAuthByUsername_whenUserNotExist_shouldThrowException() {
        // Arrange
        when(authRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authService.findAuthByUsername("nonexistent"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found with username: nonexistent");
    }

    @Test
    void isAdmin_whenUserIsAdmin_shouldReturnTrue() {
        // Arrange
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        Auth adminAuth = new Auth();
        adminAuth.setUsername("adminuser");
        adminAuth.setRoles(Set.of(adminRole));
        when(authRepository.findByUsername("adminuser")).thenReturn(Optional.of(adminAuth));

        // Act
        Boolean isAdmin = authService.isAdmin("adminuser");

        // Assert
        assertThat(isAdmin).isTrue();
    }

    @Test
    void isAdmin_whenUserIsNotAdmin_shouldReturnFalse() {
        // Arrange
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        Auth userAuth = new Auth();
        userAuth.setUsername("regularuser");
        userAuth.setRoles(Set.of(userRole));
        when(authRepository.findByUsername("regularuser")).thenReturn(Optional.of(userAuth));

        // Act
        Boolean isAdmin = authService.isAdmin("regularuser");

        // Assert
        assertThat(isAdmin).isFalse();
    }

    @Test
    void isAdmin_whenUserDoesNotExist_shouldReturnFalse() {
        // Arrange
        when(authRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

        // Act
        Boolean isAdmin = authService.isAdmin("ghost");

        // Assert
        assertThat(isAdmin).isFalse();
    }
}
