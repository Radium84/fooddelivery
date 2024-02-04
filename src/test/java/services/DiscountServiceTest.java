package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.sberbank.entity.Discount;
import ru.edu.sberbank.repository.DiscountRepository;
import ru.edu.sberbank.services.DiscountService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @Mock
    private DiscountRepository discountRepository;

    @InjectMocks
    private DiscountService discountService;

    @Test
    void getAllDiscounts_ShouldReturnAllDiscounts() {
        Discount discount1 = new Discount(1L, "Discount 1", "Description 1", 10,
                LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(5));
        Discount discount2 = new Discount(2L, "Discount 2", "Description 2", 20,
                LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(10));

        List<Discount> expectedDiscounts = Arrays.asList(discount1, discount2);


        when(discountRepository.findAll()).thenReturn(expectedDiscounts);

        List<Discount> actualDiscounts = discountService.getAllDiscounts();

        assertNotNull(actualDiscounts);
        assertEquals(expectedDiscounts.size(), actualDiscounts.size());
        assertEquals(expectedDiscounts, actualDiscounts);

        verify(discountRepository).findAll();
    }

    @Test
    void getDiscountById_WhenIdExists_ShouldReturnDiscount() {
        Long id = 1L;
        Discount expectedDiscount = new Discount(1L, "Discount 1", "Description 1", 10,
                LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(5));

        when(discountRepository.findById(id)).thenReturn(Optional.of(expectedDiscount));

        Optional<Discount> actualDiscountOpt = discountService.getDiscountById(id);

        assertTrue(actualDiscountOpt.isPresent());
        assertEquals(expectedDiscount, actualDiscountOpt.get());

        verify(discountRepository).findById(id);
    }

    @Test
    void getDiscountById_WhenIdDoesNotExist_ShouldReturnEmpty() {
        Long id = 2L;

        when(discountRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Discount> actualDiscountOpt = discountService.getDiscountById(id);

        assertFalse(actualDiscountOpt.isPresent());

        verify(discountRepository).findById(id);
    }
}
