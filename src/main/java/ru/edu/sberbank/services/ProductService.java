package ru.edu.sberbank.services;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.sberbank.entity.Discount;
import ru.edu.sberbank.entity.Product;
import ru.edu.sberbank.entity.dto.ProductRequestDTO;
import ru.edu.sberbank.entity.dto.ProductResponseDTO;
import ru.edu.sberbank.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final DiscountService discountService;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;


    public ProductService(ProductRepository productRepository, DiscountService discountService,
                          CategoryService categoryService) {
        this.discountService = discountService;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ProductResponseDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToResponseDTO);
    }

    public ProductResponseDTO convertToResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        if (product.getIsDiscount()) {
            Optional<Discount> discountValue = discountService.getDiscountById(product.getDiscount().getId());
            discountValue.ifPresent(discount -> productResponseDTO.setCalculatedPrice((100 - discount.getValue()) * product.getPrice() / 100));

        } else {
            productResponseDTO.setCalculatedPrice(product.getPrice());
        }
        mapDTOToEntity(productResponseDTO, product);
        return productResponseDTO;

    }

    @Transactional
    public Product createProduct(ProductRequestDTO productReqDTO) {
        Product product = new Product();
        mapRequestToEntity(productReqDTO, product);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductRequestDTO productReqDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        mapRequestToEntity(productReqDTO, product);

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    private void mapDTOToEntity(ProductResponseDTO productResponseDTO, Product product) {
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setCategoryName(product.getCategory().getName());

        productResponseDTO.setComposition(product.getComposition());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
        boolean isDiscount = product.getIsDiscount();
        productResponseDTO.setIsDiscount(isDiscount);
        if (isDiscount) {
            productResponseDTO.setDiscountName(product.getDiscount().getName());
        }
    }

    private void mapRequestToEntity(ProductRequestDTO productRequestDTO, Product product) {
        product.setName(productRequestDTO.getName());
        var category = categoryService.findCategoryById(productRequestDTO.getCategoryId());
        category.ifPresent(product::setCategory);
        product.setComposition(productRequestDTO.getComposition());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        boolean isDiscount = productRequestDTO.getIsDiscount();
        product.setIsDiscount(isDiscount);
        if (isDiscount) {
            var discount = discountService.getDiscountById(productRequestDTO.getDiscountId());
            discount.ifPresent(product::setDiscount);
        }
        Hibernate.initialize(product.getUsersWhoFavorited());

    }


}
