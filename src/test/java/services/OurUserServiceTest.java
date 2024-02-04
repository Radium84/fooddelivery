package services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.entity.Role;
import ru.edu.sberbank.repository.OurUserRepository;
import ru.edu.sberbank.services.OurUserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OurUserServiceTest {
    @Mock
    private OurUserRepository ourUserRepository;

    @InjectMocks
    private OurUserService ourUserService;

    @Test
    void getAllOurUsers_ShouldReturnAllUsers() {
        // Создаём список пользователей
        List<OurUser> expectedUsers = Arrays.asList(
                createOurUser(1L, "Alice", "A", "Smith", "123 Main St", LocalDateTime.now().minusYears(30)),
                createOurUser(2L, "Bob", "B", "Johnson", "456 Elm St", LocalDateTime.now().minusYears(25))
        );

        // Настройка поведения мока
        when(ourUserRepository.findAll()).thenReturn(expectedUsers);

        // Выполнение сервис-метода
        List<OurUser> actualUsers = ourUserService.getAllOurUsers();

        // Проверка результата и поведения мока
        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
        verify(ourUserRepository).findAll();
    }

    @Test
    void getOurUserById_WhenUserExists_ShouldReturnUser() {
        // Создаём пользователя
        OurUser expectedUser = createOurUser(1L, "Alice", "A", "Smith", "123 Main St", LocalDateTime.now().minusYears(30));

        // Настройка поведения мока
        when(ourUserRepository.findById(1L)).thenReturn(Optional.of(expectedUser));

        // Выполнение сервис-метода
        Optional<OurUser> actualUserOpt = ourUserService.getOurUserById(1L);

        // Проверка результата и поведения мока
        assertTrue(actualUserOpt.isPresent());
        assertEquals(expectedUser, actualUserOpt.get());
        verify(ourUserRepository).findById(1L);
    }

    // Вспомогательный метод для создания объекта пользователя
    private OurUser createOurUser(Long id, String firstname, String middlename, String lastname, String address, LocalDateTime birthday) {
        OurUser user = new OurUser();
        user.setId(id);
        user.setFirstname(firstname);
        user.setMiddlename(middlename);
        user.setLastname(lastname);
        Role role = new Role();
        user.setRole(role);
        user.setAddress(address);
        user.setBirthday(birthday);
        return user;
    }
}
