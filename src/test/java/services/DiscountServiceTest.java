package services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.edu.sberbank.entity.Discount;
import ru.edu.sberbank.exceptions.ResourceNotFoundException;
import ru.edu.sberbank.repository.DiscountRepository;
import ru.edu.sberbank.services.DiscountService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class DiscountServiceTest {

    @Mock
    private DiscountRepository discountRepository;

    @InjectMocks
    private DiscountService discountService;

    private Discount discount;

    @BeforeEach
    void setUp() {
        discount = new Discount();
        discount.setId(1L);
        discount.setName("Test Discount");
        discount.setDescription("Test Description");
        discount.setValue(10);
    }

    @Test
    void testGetAllDiscounts() {
        when(discountRepository.findAll()).thenReturn(Arrays.asList(discount));

        List<Discount> result = discountService.getAllDiscounts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(discount.getValue(), result.get(0).getValue());
    }

    @Test
    void testGetDiscountByIdFound() {
        when(discountRepository.findById(anyLong())).thenReturn(Optional.of(discount));

        Discount result = discountService.getDiscountById(1L);

        assertNotNull(result);
        assertEquals("Test Discount", result.getName());
    }

    @Test
    void testGetDiscountByIdNotFound() {
        when(discountRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> discountService.getDiscountById(1L));
    }

    @Test
    void testCreateDiscount() {
        when(discountRepository.save(any(Discount.class))).thenReturn(discount);

        Discount result = discountService.createDiscount(discount);

        assertNotNull(result);
        assertEquals("Test Discount", result.getName());
    }

    @Test
    void testUpdateDiscountFound() throws Exception {
        when(discountRepository.findById(anyLong())).thenReturn(Optional.of(discount));
        when(discountRepository.save(any(Discount.class))).thenReturn(discount);

        Discount toUpdate = new Discount();
        toUpdate.setId(1L);
        toUpdate.setName("Updated Discount");

        Discount result = discountService.updateDiscount(1L, toUpdate);

        assertNotNull(result);
        assertEquals("Updated Discount", result.getName());
    }

    @Test
    void testUpdateDiscountNotFound() {
        when(discountRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> discountService.updateDiscount(1L, new Discount()));
    }

    @Test
    void testDeleteDiscount() {
        willDoNothing().given(discountRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> discountService.deleteDiscount(1L));
    }
}