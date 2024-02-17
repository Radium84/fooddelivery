package ru.edu.sberbank.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.edu.sberbank.entity.dto.OrderRequestDTO;
import ru.edu.sberbank.entity.dto.OrderResponseDTO;
import ru.edu.sberbank.services.OrderService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Log
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        OrderResponseDTO orderResponseDTO = orderService.getOrderById(id);
        return ResponseEntity.ok(orderResponseDTO);

    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            OrderResponseDTO orderResponseDTO = orderService.updateOrder(orderRequestDTO);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(orderResponseDTO.getOrderId())
                    .toUri();
            return ResponseEntity.created(location).body(orderResponseDTO);
        } catch (EntityNotFoundException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }
}