package services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.sberbank.entity.Category;
import ru.edu.sberbank.exceptions.ResourceNotFoundException;
import ru.edu.sberbank.repository.CategoryRepository;
import ru.edu.sberbank.services.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronics category");
    }

    @Test
    void findAllCategories_shouldReturnAllCategories() {
        // Arrange
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        given(categoryRepository.findAll()).willReturn(categories);

        // Act
        List<Category> foundCategories = categoryService.findAllCategories();

        // Assert
        assertThat(foundCategories).hasSize(1);
        assertThat(foundCategories.get(0)).isEqualTo(category);
    }

    @Test
    void findCategoryById_whenCategoryExists_shouldReturnCategory() {
        // Arrange
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));

        // Act
        Category foundCategory = categoryService.findCategoryById(1L);

        // Assert
        assertThat(foundCategory).isEqualTo(category);
    }

    @Test
    void findCategoryById_whenCategoryDoesNotExist_shouldThrowException() {
        // Arrange
        given(categoryRepository.findById(anyLong())).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> categoryService.findCategoryById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Discount not found with username: 1");
    }

    @Test
    void createCategory_shouldReturnCreatedCategory() {
        // Arrange
        given(categoryRepository.save(category)).willReturn(category);

        // Act
        Category savedCategory = categoryService.createCategory(category);

        // Assert
        assertThat(savedCategory).isEqualTo(category);
    }

    @Test
    void updateCategory_whenCategoryExists_shouldReturnUpdatedCategory() {
        // Arrange
        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Name");
        updatedCategory.setDescription("Updated Description");
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(categoryRepository.save(category)).willReturn(updatedCategory);

        // Act
        Category result = categoryService.updateCategory(1L, updatedCategory);

        // Assert
        assertThat(result.getName()).isEqualTo(updatedCategory.getName());
        assertThat(result.getDescription()).isEqualTo(updatedCategory.getDescription());
    }

    @Test
    void updateCategory_whenCategoryDoesNotExist_shouldThrowException() {
        // Arrange
        given(categoryRepository.findById(anyLong())).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> categoryService.updateCategory(1L, new Category()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Category with id 1 not found");
    }

    @Test
    void deleteCategoryById_shouldCallDeleteMethod() {
        // Arrange
        doNothing().when(categoryRepository).deleteById(1L);

        // Act
        categoryService.deleteCategoryById(1L);

        // Assert
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
