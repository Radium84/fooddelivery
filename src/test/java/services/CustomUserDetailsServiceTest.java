package services;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.entity.Role;
import ru.edu.sberbank.services.AuthService;
import ru.edu.sberbank.services.CustomUserDetailsService;
import ru.edu.sberbank.services.OurUserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Set;


@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @Mock
    private AuthService authService;

    @Mock
    private OurUserService ourUserService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_Success() {
        // Входные данные
        String username = "testUser";

        // Моки
        Auth mockAuth = new Auth();
        mockAuth.setUsername(username);
        mockAuth.setPassword("password");
        Role role = new Role();
        role.setName("ROLE_USER");
        mockAuth.setRoles(Set.of(role));

        OurUser mockOurUser = new OurUser();
        mockOurUser.setId(1L);
        mockOurUser.setAuth(mockAuth);

        given(authService.findAuthByUsername(username)).willReturn(mockAuth);
        given(ourUserService.findOurUserByAuth(mockAuth)).willReturn(mockOurUser);

        // Исполнение
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // Проверки
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(mockAuth.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));

        // Проверка, что методы моков были вызваны
        verify(authService).findAuthByUsername(username);
        verify(ourUserService).findOurUserByAuth(mockAuth);
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        // Входные данные
        String username = "unknownUser";

        // Конфигурация поведения для мока
        given(authService.findAuthByUsername(username)).willThrow(new UsernameNotFoundException("User not found"));

        // Проверяем исключение
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));

        // Убеждаемся, что findOurUserByAuth не вызывался
        verify(ourUserService, never()).findOurUserByAuth(any());
    }
}
