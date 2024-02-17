package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.edu.sberbank.controller.RoleController;
import ru.edu.sberbank.entity.Role;
import ru.edu.sberbank.services.RoleService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void testGetAllRoles() throws Exception {
        Role roleAdmin = new Role(1L, "ADMIN");
        Role roleUser = new Role(2L, "USER");
        List<Role> allRoles = Arrays.asList(roleAdmin, roleUser);

        when(roleService.getAllRoles()).thenReturn(allRoles);

        mockMvc.perform(get("/roles").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(allRoles)));

        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    public void testGetRoleById() throws Exception {
        Role roleUser = new Role(2L, "USER");

        when(roleService.getRoleById(2L)).thenReturn(roleUser);

        mockMvc.perform(get("/roles/{id}", 2L).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(roleUser)));

        verify(roleService, times(1)).getRoleById(2L);
    }
}
