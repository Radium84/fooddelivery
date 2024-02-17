package controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.edu.sberbank.controller.CategoryController;
import ru.edu.sberbank.entity.Category;
import ru.edu.sberbank.services.CategoryService;

import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(categoryController).build();
    }

    @Test
    void testGetAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(new Category(1L, "Test Category 1", "Description 1", "endpoint1"), new Category(2L, "Test Category 2", "Description 2", "endpoint2"));
        when(categoryService.findAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/categories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Category 1"))
                .andExpect(jsonPath("$[1].name").value("Test Category 2"));
    }

    @Test
    void testGetCategoryById() throws Exception {
        Category category = new Category(1L, "Test Category", "Description", "endpoint");
        when(categoryService.findCategoryById(1L)).thenReturn(category);

        mockMvc.perform(get("/api/categories/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Category"));
    }

    @Test
    void testCreateCategory() throws Exception {
        Category inputCategory = new Category(null, "New Category", "New Description", "newEndpoint");
        Category savedCategory = new Category(1L, "New Category", "New Description", "newEndpoint");
        when(categoryService.createCategory(any(Category.class))).thenReturn(savedCategory);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputCategory)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    @Test
    void testUpdateCategory() throws Exception {
        Category updateCategory = new Category(1L, "Updated Category", "Updated Description", "updatedEndpoint");
        when(categoryService.updateCategory(eq(1L), any(Category.class))).thenReturn(updateCategory);

        mockMvc.perform(put("/api/categories/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Category"));
    }
}
