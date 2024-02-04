package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.sberbank.entity.Role;
import ru.edu.sberbank.repository.RoleRepository;
import ru.edu.sberbank.services.RoleService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role1;
    private Role role2;

    @BeforeEach
    public void setUp() {
        // Initialize your roles here
        role1 = new Role();
        role1.setRoleId(1L);
        role1.setName("ROLE_USER");

        role2 = new Role();
        role2.setRoleId(2L);
        role2.setName("ROLE_ADMIN");
    }

    @Test
    void getAllRoles_Success() {
        List<Role> expectedRoles = Arrays.asList(role1, role2);
        when(roleRepository.findAll()).thenReturn(expectedRoles);

        List<Role> actualRoles = roleService.getAllRoles();

        verify(roleRepository).findAll(); // Verifies that the findAll method is called
        assertEquals(expectedRoles, actualRoles); // Checks if the expected and actual lists are the same
    }

    @Test
    void getRoleById_Found() {
        Long roleId = 1L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role1));

        Optional<Role> actualRole = roleService.getRoleById(roleId);

        verify(roleRepository).findById(roleId); // Verifies that the findById method is called
        assertTrue(actualRole.isPresent()); // Ensures the optional is not empty
        assertEquals(role1, actualRole.get()); // Compares the expected role with the actual role
    }

    @Test
    void getRoleById_NotFound() {
        Long roleId = 99L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        Optional<Role> actualRole = roleService.getRoleById(roleId);

        verify(roleRepository).findById(roleId); // Verifies that the findById method is called
        assertFalse(actualRole.isPresent()); // The role should not be found, and Optional should be empty
    }
//    @Test
//    void calculatePassword(){
//        String password = "user";
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode(password);
//        assertEquals(hashedPassword,"12345");
//    }
}