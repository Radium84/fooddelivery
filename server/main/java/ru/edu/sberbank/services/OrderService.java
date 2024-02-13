package ru.edu.sberbank.services;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.sberbank.entity.Order;
import ru.edu.sberbank.entity.OrderItem;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.entity.Product;
import ru.edu.sberbank.entity.dto.OrderRequestDTO;
import ru.edu.sberbank.entity.dto.OrderResponseDTO;
import ru.edu.sberbank.repository.OrderItemRepository;
import ru.edu.sberbank.repository.OrderRepository;
import ru.edu.sberbank.repository.OurUserRepository;
import ru.edu.sberbank.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final OurUserRepository ourUserRepository;


    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        ProductRepository productRepository, ProductService productService,
                        OurUserRepository ourUserRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.ourUserRepository = ourUserRepository;

    }


    @Transactional
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::convertToOrderResponseDTO).toList();
    }

    @Transactional
    public Optional<OrderResponseDTO> getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToOrderResponseDTO);
    }

    public OrderResponseDTO updateOrder(OrderRequestDTO cartItemDTO) {
        Order order = createOrderFromDTO(cartItemDTO);
        return convertToOrderResponseDTO(order);
    }

    @Transactional
    public Order createOrderFromDTO(OrderRequestDTO orderRequestDTO) {
        // Поиск пользователя
        OurUser user = ourUserRepository.findById(orderRequestDTO.getOurUsersId()).orElseThrow(
                () -> new EntityNotFoundException("User not found"));

        // Создание нового заказа
        Order order = new Order();
        order.setUser(user);
        order.setOrderdate(LocalDateTime.now());
        double totalOrderPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        // Создание элементов заказа и вычисление итоговой суммы
        for (Map.Entry<Long, Integer> entry : orderRequestDTO.getProductQuantity().entrySet()) {
            Product product = productRepository.findById(entry.getKey()).orElseThrow(
                    () -> new EntityNotFoundException("Product not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());

            // Вычисляем цену с учетом скидки для каждого элемента заказа
            double discountedPrice = calculateDiscountedPrice(product);
            orderItem.setPrice(discountedPrice);

            // Добавляем стоимость элемента (цена * количество) к итоговой сумме заказа
            totalOrderPrice += discountedPrice * entry.getValue();

            // Добавляем элемент в список
            orderItems.add(orderItem);
        }
        // Установка итоговой суммы заказа
        order.setTotalPrice(totalOrderPrice);

        // Сохранение заказа в базе данных
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // Связывание каждого элемента заказа с сохраненным заказом и сохранение их в базу данных
        orderItems.forEach(item -> item.setOrder(savedOrder));
        orderItemRepository.saveAll(orderItems);

        return savedOrder;
    }

    private Double calculateDiscountedPrice(Product product) {
        double discountRate = product.getIsDiscount() ? product.getDiscount().getValue() : 0.0;
        double originalPrice = product.getPrice();
        return originalPrice - (originalPrice * (discountRate / 100.0));
    }
    private OrderResponseDTO convertToOrderResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOurUserId(order.getUser().getId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderId(order.getId());

        HashMap<Long, Double> productsWithDiscount = new HashMap<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            productsWithDiscount.put(orderItem.getProduct().getId(), orderItem.getPrice() * orderItem.getQuantity());
        }
        dto.setProductWithDiscount(productsWithDiscount);

        return dto;
    }
}