package services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.sberbank.entity.Role;
import ru.edu.sberbank.exceptions.ResourceNotFoundException;
import ru.edu.sberbank.repository.RoleRepository;
import ru.edu.sberbank.services.RoleService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void getAllRoles_ShouldReturnAllRoles() {
        // Arrange
        Role adminRole = new Role(1L, "ADMIN");
        Role userRole = new Role(2L, "USER");
        List<Role> expectedRoles = Arrays.asList(adminRole, userRole);
        given(roleRepository.findAll()).willReturn(expectedRoles);

        // Act
        List<Role> actualRoles = roleService.getAllRoles();

        // Assert
        assertThat(actualRoles).isEqualTo(expectedRoles);
    }

    @Test
    void getRoleById_WhenFound_ShouldReturnRole() {
        // Arrange
        Role userRole = new Role(2L, "USER");
        given(roleRepository.findById(2L)).willReturn(Optional.of(userRole));

        // Act
        Role actualRole = roleService.getRoleById(2L);

        // Assert
        assertThat(actualRole).isEqualTo(userRole);
    }

    @Test
    void getRoleById_WhenNotFound_ShouldThrowException() {
        // Arrange
        long roleId = 99L;
        given(roleRepository.findById(roleId)).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> roleService.getRoleById(roleId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Role not found with id: " + roleId);
    }
}