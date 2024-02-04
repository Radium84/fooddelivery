package ru.edu.sberbank.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.sberbank.entity.Discount;
import ru.edu.sberbank.entity.Product;
import ru.edu.sberbank.entity.dto.ProductDTO;
import ru.edu.sberbank.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final DiscountService discountService;

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, DiscountService discountService) {
        this.discountService = discountService;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToProductDTO);
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        if (product.getIsDiscount()) {
            Optional<Discount> discountValue = discountService.getDiscountById(product.getDiscount().getId());
            if (discountValue.isPresent()) {
                productDTO.setCalculatedPrice((100 - discountValue.get().getValue()) * product.getPrice() / 100);
            } else {
                productDTO.setCalculatedPrice(product.getPrice());
            }
        }
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategory(product.getCategory());
        productDTO.setComposition(product.getComposition());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setIsDiscount(product.getIsDiscount());
        productDTO.setDiscount(product.getDiscount());
        return productDTO;

    }


}
