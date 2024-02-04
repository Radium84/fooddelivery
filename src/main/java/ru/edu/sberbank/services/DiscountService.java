package ru.edu.sberbank.services;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.sberbank.entity.Discount;
import ru.edu.sberbank.repository.DiscountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Transactional(readOnly = true)
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Discount> getDiscountById(Long id) {
        return discountRepository.findById(id);
    }
    @Transactional
    public Discount createDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    @Transactional
    public Discount updateDiscount(Long id, Discount discount) throws Exception {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> new Exception("Discount not found with id " + id));
        existingDiscount.setName(discount.getName());
        existingDiscount.setDescription(discount.getDescription());
        existingDiscount.setValue(discount.getValue());
        existingDiscount.setStartDate(discount.getStartDate());
        existingDiscount.setEndDate(discount.getEndDate());
        return discountRepository.save(existingDiscount);
    }

    @Transactional
    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }
}
