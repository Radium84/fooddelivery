package ru.edu.sberbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.sberbank.entity.Category;
import ru.edu.sberbank.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories") // префикс URL для всех методов в этом контроллере
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Получение всех категорий
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categories); // HTTP 200 OK
    }

    // Получение категории по ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.findCategoryById(id)
                .map(ResponseEntity::ok) // HTTP 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // HTTP 404 Not Found
    }
}
