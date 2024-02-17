package services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.sberbank.entity.Order;
import ru.edu.sberbank.entity.OrderItem;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.entity.dto.OrderResponseDTO;
import ru.edu.sberbank.exceptions.ResourceNotFoundException;
import ru.edu.sberbank.repository.OrderRepository;
import ru.edu.sberbank.services.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getAllOrdersShouldReturnNotEmptyList() {
        // Настройка поведения моков
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(1L);
        OurUser user = new OurUser();
        user.setId(2L);
        order.setUser(user);
        List<OrderItem> orderItems = new ArrayList<>();

        order.setOrderItems(orderItems);

        orders.add(order);
        when(orderRepository.findAll()).thenReturn(orders);
        List<OrderResponseDTO> result = orderService.getAllOrders();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getOrderId());


        verify(orderRepository).findAll();
    }

    @Test
    void getOrderByIdShouldReturnCorrectOrder() {
        // Настройка и инициализация
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        // Создание и настройка пользователя
        OurUser user = new OurUser();
        user.setId(2L);
        order.setUser(user);
        List<OrderItem> orderItems = new ArrayList<>();
        order.setOrderItems(orderItems);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Выполнение
        OrderResponseDTO result = orderService.getOrderById(orderId);

        // Верификация
        assertEquals(orderId, result.getOrderId());
        verify(orderRepository).findById(orderId);
    }

    @Test
    void getOrderByIdShouldThrowExceptionIfNotFound() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrderById(orderId);
        });
    }
}
