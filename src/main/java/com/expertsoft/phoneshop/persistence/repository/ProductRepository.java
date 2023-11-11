package com.expertsoft.phoneshop.persistence.repository;

import com.expertsoft.phoneshop.persistence.model.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Phone, Long> {

    List<Phone> findAllByModel(String model, Pageable pageable);

    List<Phone> findAll();

    Page<Phone> findAllByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(String brand, String model, Pageable pageable);

    Optional<Phone> findById(Long id);

    Page<Phone> findAllByBrandContainingIgnoreCaseAndModelContainingIgnoreCaseAndPriceGreaterThanAndPriceLessThan(String brand, String model, BigDecimal fromPrice, BigDecimal toPrice, Pageable pageable);
    Page<Phone> findAllByBrandOrModel(String brand, String model, Pageable pageable);

    List<Phone> findAllByBrandEqualsIgnoreCaseOrModelContainingIgnoreCase(String brand, String model);

    Page<Phone> findAllByBrandOrModelAndPriceGreaterThanEqualAndPriceLessThanEqual(String brand, String model, BigDecimal priceGreater, BigDecimal priceLess, Pageable pageable);

    Page<Phone> findAllByBrandOrModelAndPriceGreaterThan(String brand, String model, BigDecimal priceGreater, Pageable pageable);

    Page<Phone> findAllByBrandOrModelAndPriceLessThanEqual(String brand, String model, BigDecimal priceLess, Pageable pageable);

    Page<Phone> findAllByPriceLessThanEqual(BigDecimal priceLes, Pageable pageable);

    Page<Phone> findAllByPriceGreaterThanEqual(BigDecimal priceGreater, Pageable pageable);

    Page<Phone> findAllByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal priceGreaterThan, BigDecimal priceLessThan, Pageable pageable);

    Page<Phone> findAllByBrandContainingIgnoreCaseOrModelContainingIgnoreCaseAndPriceGreaterThanEqual(String searchQuery, String searchQuery1, BigDecimal fromPrice, Pageable requestedPage);

    Page<Phone> findAllByBrandContainingIgnoreCaseOrModelContainingIgnoreCaseAndPriceLessThanEqual(String searchQuery, String searchQuery1, BigDecimal toPrice, Pageable requestedPage);

    Page<Phone> findAllByBrandContainingIgnoreCaseOrModelContainingIgnoreCaseAndPriceLessThanEqualAndPriceGreaterThanEqual(String searchQuery, String searchQuery1, BigDecimal toPrice, BigDecimal fromPrice, Pageable requestedPage);
}
