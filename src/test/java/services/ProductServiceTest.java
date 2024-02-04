package services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.sberbank.entity.Discount;
import ru.edu.sberbank.entity.Product;
import ru.edu.sberbank.entity.dto.ProductDTO;
import ru.edu.sberbank.repository.ProductRepository;
import ru.edu.sberbank.services.DiscountService;
import ru.edu.sberbank.services.ProductService;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Discount discount;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(100);
        product.setIsDiscount(true);

        discount = new Discount();
        discount.setId(1L);
        discount.setValue(10);

        product.setDiscount(discount);
    }

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDTO> productDTOs = productService.getAllProducts();

        verify(productRepository).findAll();

        assertEquals(1, productDTOs.size());
        assertEquals(product.getName(), productDTOs.get(0).getName());

    }

    @Test
    void getProductByIdFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(discountService.getDiscountById(discount.getId())).thenReturn(Optional.of(discount));

        Optional<ProductDTO> productDTO = productService.getProductById(1L);

        verify(productRepository).findById(1L);
        verify(discountService).getDiscountById(discount.getId());

        assertTrue(productDTO.isPresent());
        assertEquals(product.getName(), productDTO.get().getName());

        // ...assert other fields as needed, including calculated price with discount
    }

    @Test
    void getProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ProductDTO> productDTO = productService.getProductById(1L);

        verify(productRepository).findById(1L);

        assertFalse(productDTO.isPresent());
    }


}
