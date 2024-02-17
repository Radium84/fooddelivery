package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.edu.sberbank.entity.Discount;
import ru.edu.sberbank.exceptions.ResourceNotFoundException;
import ru.edu.sberbank.repository.DiscountRepository;
import ru.edu.sberbank.services.DiscountService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
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

    }

    @Test
    void getAllDiscountsTest() {
        when(discountRepository.findAll()).thenReturn(Collections.singletonList(discount));
        List<Discount> result = discountService.getAllDiscounts();
        assertEquals(1, result.size());
        verify(discountRepository).findAll();
    }

    @Test
    void getDiscountByIdTest() {
        when(discountRepository.findById(1L)).thenReturn(Optional.of(discount));
        Discount result = discountService.getDiscountById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void getDiscountByIdNotFoundTest() {
        assertThrows(ResourceNotFoundException.class, () -> discountService.getDiscountById(2L));
    }

    @Test
    void createDiscountTest() {
        when(discountRepository.save(any(Discount.class))).thenReturn(discount);
        Discount created = discountService.createDiscount(discount);
        assertEquals(discount.getId(), created.getId());
    }

    @Test
    void updateDiscountTest() throws Exception {
        Discount updatedDiscount = new Discount();
        updatedDiscount.setId(1L);
        // обновите поля updatedDiscount по необходимости

        when(discountRepository.findById(1L)).thenReturn(Optional.of(discount));
        when(discountRepository.save(any(Discount.class))).thenReturn(updatedDiscount);

        Discount result = discountService.updateDiscount(1L, updatedDiscount);
        assertEquals(updatedDiscount.getId(), result.getId());
        // Добавьте дополнительные проверки для свойств
    }

    @Test
    void deleteDiscountTest() {
        doNothing().when(discountRepository).deleteById(1L);
        discountService.deleteDiscount(1L);
        verify(discountRepository, times(1)).deleteById(1L);
    }
}