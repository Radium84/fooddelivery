package ru.edu.sberbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.sberbank.entity.Order;
import ru.edu.sberbank.entity.dto.OrderDTO;
import ru.edu.sberbank.entity.dto.OrderResponseDTO;
import ru.edu.sberbank.services.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/update")
    public ResponseEntity<OrderResponseDTO> updateOrder(@RequestBody OrderDTO cartItemDTO) {
        OrderResponseDTO orderResponse = orderService.updateOrder(cartItemDTO);
        return ResponseEntity.ok(orderResponse);
    }


}