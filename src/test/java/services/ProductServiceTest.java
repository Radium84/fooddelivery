package services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.sberbank.entity.Category;
import ru.edu.sberbank.entity.Product;
import ru.edu.sberbank.entity.dto.ProductRequestDTO;
import ru.edu.sberbank.entity.dto.ProductResponseDTO;
import ru.edu.sberbank.exceptions.ResourceNotFoundException;
import ru.edu.sberbank.repository.ProductRepository;
import ru.edu.sberbank.services.CategoryService;
import ru.edu.sberbank.services.DiscountService;
import ru.edu.sberbank.services.ProductService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryService categoryService;


    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_ReturnsProductList() {
        Product product = new Product(); // Создаем экземпляр продукта
        product.setIsDiscount(Boolean.FALSE);
        Category category = new Category();
        category.setId(1L);
        product.setCategory(category);

        // Обеспечиваем работу методов мока
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        List<ProductResponseDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).findAll(); //
    }

    @Test
    void whenCreateProduct_thenReturnSavedProduct() {
        Product product = new Product(); // Создаем экземпляр продукта
        product.setIsDiscount(Boolean.FALSE);
        Category category = new Category();
        category.setId(1L);
        product.setCategory(category);
        ProductRequestDTO productReqDTO = new ProductRequestDTO();
        productReqDTO.setIsDiscount(Boolean.FALSE);
        // Настройте значения для productReqDTO...

        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);

        // Когда
        Product createdProduct = productService.createProduct(productReqDTO);

        // Тогда
        assertNotNull(createdProduct);
        assertEquals(product.getId(), createdProduct.getId());
    }
    @Test
    void whenUpdateProduct_thenSuccessfullyUpdated() {
        // Дано
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        ProductRequestDTO productReqDTO = new ProductRequestDTO();
        productReqDTO.setIsDiscount(Boolean.FALSE);
        // Настройте значения для productReqDTO...

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Когда
        Product updatedProduct = productService.updateProduct(productId, productReqDTO);

        // Тогда
        assertNotNull(updatedProduct);
        assertEquals(productId, updatedProduct.getId());
        // Вы можете также проверить, что поля в updatedProduct соответствуют данным из productReqDTO
    }
    @Test
    void whenDeleteProductById_thenSuccess() {
        Long productId = 1L;
        Mockito.doNothing().when(productRepository).deleteById(productId);
        productService.deleteProductById(productId);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(productId);
    }

    @Test
    void getProductById_WhenProductNotFound_ThrowsException() {
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId));
    }
}
