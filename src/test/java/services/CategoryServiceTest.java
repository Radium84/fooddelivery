package services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.sberbank.entity.Category;
import ru.edu.sberbank.repository.CategoryRepository;
import ru.edu.sberbank.services.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void whenFindAllCategories_thenReturnCategoryList() {
        // Инициализируем данные, которые мы ожидаем получить
        Category category1 = new Category(1L, "Category1", "Description1");
        Category category2 = new Category(2L, "Category2", "Description2");
        List<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(category1);
        expectedCategories.add(category2);
        // Настраиваем поведение мока
        when(categoryRepository.findAll()).thenReturn(expectedCategories);

        // Вызываем метод сервиса
        List<Category> actualCategories = categoryService.findAllCategories();

        // Проверяем результат
        assertThat(actualCategories, is(not(empty())));
        assertThat(actualCategories, is(expectedCategories));
    }

    @Test
    public void whenFindCategoryById_thenReturnCategory() {
        // Инициализируем данные, которые мы ожидаем получить
        Category category = new Category(1L, "Category1", "Description1");
        Optional<Category> expectedCategory = Optional.of(category);

        // Настраиваем поведение мока
        when(categoryRepository.findById(1L)).thenReturn(expectedCategory);

        // Вызываем метод сервиса
        Optional<Category> actualCategory = categoryService.findCategoryById(1L);

        // Проверяем результат
        assertThat(actualCategory, is(notNullValue()));
        assertThat(actualCategory.get(), is(expectedCategory.get()));
    }

    @Test
    public void whenFindCategoryById_withNonExistingId_thenReturnEmpty() {
        // Настраиваем поведение мока для отсутствующей категории
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        // Вызываем метод сервиса
        Optional<Category> actualCategory = categoryService.findCategoryById(999L);

        // Проверяем результат
        assertThat(actualCategory, is(Optional.empty()));
    }


}

