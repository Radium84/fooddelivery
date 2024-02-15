package ru.edu.sberbank.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.edu.sberbank.entity.Discount;
import ru.edu.sberbank.services.DiscountService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@Log
public class DiscountController {

    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        List<Discount> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable Long id) {
        Discount discount = discountService.getDiscountById(id);
        return ResponseEntity.ok(discount);
    }
    @PostMapping
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        Discount newDiscount = discountService.createDiscount(discount);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDiscount.getId())
                .toUri();
        return ResponseEntity.created(location).body(newDiscount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable Long id, @RequestBody Discount discount) {
        try {
            Discount updatedDiscount = discountService.updateDiscount(id, discount);
            return ResponseEntity.ok(updatedDiscount);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        try {
            discountService.deleteDiscount(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}