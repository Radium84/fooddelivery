package ru.edu.sberbank.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.sberbank.entity.Order;
import ru.edu.sberbank.entity.OrderItem;
import ru.edu.sberbank.entity.OurUser;
import ru.edu.sberbank.entity.Product;
import ru.edu.sberbank.entity.dto.OrderDTO;
import ru.edu.sberbank.entity.dto.OrderResponseDTO;
import ru.edu.sberbank.entity.dto.ProductDTO;
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
                        ProductRepository productRepository,ProductService productService,
                        OurUserRepository ourUserRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.ourUserRepository = ourUserRepository;

    }


    @Transactional
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Transactional
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public OrderResponseDTO updateOrder(OrderDTO cartItemDTO) {
        Order order = createOrderFromDTO(cartItemDTO);
        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setOrderId(order.getId()); // Устанавливаем ID заказа
        dto.setOurUserID(order.getUser().getId()); // Устанавливаем ID пользователя
        dto.setTotalPrice(order.getTotalPrice()); // Устанавливаем общую цену

        // Инициализируем мапу для хранения товаров и их цен с учетом скидки
        HashMap<Long, Double> productsWithDiscount = new HashMap<>();

        // Перебираем элементы заказа и заполняем мапу
        for (OrderItem orderItem : order.getOrderItems()) {
            productsWithDiscount.put(orderItem.getProduct().getId(), orderItem.getPrice());
        }

        dto.setProductWithDiscount(productsWithDiscount); // Устанавливаем мапу

        return dto; // Возвращаем DTO
    }
    @Transactional
    public Order createOrderFromDTO(OrderDTO orderDTO) {
        // Поиск пользователя
        OurUser user = ourUserRepository.findById(orderDTO.getOurUserID()).orElseThrow(
                () -> new RuntimeException("User not found"));

        // Создание нового заказа
        Order order = new Order();
        order.setUser(user);
        order.setOrderdate(LocalDateTime.now());
        double totalOrderPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        // Создание элементов заказа и вычисление итоговой суммы
        for (Map.Entry<Long, Integer> entry : orderDTO.getProductQuantity().entrySet()) {
            Product product = productRepository.findById(entry.getKey()).orElseThrow(
                    () -> new RuntimeException("Product not found"));
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
        Order savedOrder = orderRepository.save(order);

        // Связывание каждого элемента заказа с сохраненным заказом и сохранение их в базу данных
        orderItems.forEach(item -> item.setOrder(savedOrder));
        orderItemRepository.saveAll(orderItems);

        return savedOrder;
    }

    private Double calculateDiscountedPrice(Product product){
        double discountRate = product.getIsDiscount() ? product.getDiscount().getValue() : 0.0;
        double originalPrice = product.getPrice();
        return originalPrice - (originalPrice * (discountRate / 100.0));
    }
}