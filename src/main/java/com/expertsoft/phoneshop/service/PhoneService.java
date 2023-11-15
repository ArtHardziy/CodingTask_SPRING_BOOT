package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.PageProperties;
import com.expertsoft.phoneshop.persistence.model.Phone;
import com.expertsoft.phoneshop.persistence.model.dto.PhoneDto;
import com.expertsoft.phoneshop.persistence.model.dto.PlpDto;
import com.expertsoft.phoneshop.persistence.model.dto.SearchFormModel;
import com.expertsoft.phoneshop.persistence.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.expertsoft.phoneshop.service.AppUtil.resolvePlpPagingNumbers;

@Service
@AllArgsConstructor
public class PhoneService {

    private ProductRepository productRepository;
    private PageProperties pageProperties;

    public PhoneDto getPhoneById(Long id) {
        Optional<Phone> optionalPhone = productRepository.findById(id);
        if (optionalPhone.isEmpty()) {
            return null;
        } else {
            Phone phone = optionalPhone.get();
            return new PhoneDto(phone.getId(), phone.getBrand(), phone.getModel(), phone.getImage(), phone.getDescription(), phone.getPrice());
        }
    }

    private Page<Phone> getPage(Pageable requestedPage,
                                SearchFormModel searchFormModel) {
        if (searchFormModel == null) {
            return productRepository.findAll(requestedPage);
        } else {
            return getPhonesPage(requestedPage, searchFormModel);
        }
    }

    public PlpDto<Phone> getPhonesPlp(Pageable requestedPage,
                                      SearchFormModel searchFormModel) {
        Page<Phone> page = getPage(requestedPage, searchFormModel);
        var plpPagingNumbers = resolvePlpPagingNumbers(requestedPage.getPageNumber() + 1,
                pageProperties.getMaxNumberOfPages(),
                page.getTotalPages());
        return new PlpDto<>(requestedPage, page, plpPagingNumbers, searchFormModel);
    }

    private Page<Phone> getPhonesPage(Pageable requestedPage, SearchFormModel searchFormModel) {
        var fromPrice = searchFormModel.getBigDecimalFromPrice();
        var toPrice = searchFormModel.getBigDecimalToPrice();
        boolean isFromAndToPricesZero = fromPrice.compareTo(BigDecimal.ZERO) == 0 && toPrice.compareTo(BigDecimal.ZERO) == 0;
        if (!searchFormModel.getSearchQuery().isEmpty()) {
            String searchQuery = searchFormModel.getSearchQuery();
            if (isFromAndToPricesZero) {
                return productRepository.findAllByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(searchQuery, searchQuery, requestedPage);
            } else if (fromPrice.compareTo(BigDecimal.ZERO) != 0 && toPrice.compareTo(BigDecimal.ZERO) != 0) {
                return productRepository.findAllByBrandContainingIgnoreCaseOrModelContainingIgnoreCaseAndPriceLessThanEqualAndPriceGreaterThanEqual(searchQuery, searchQuery, toPrice, fromPrice, requestedPage);
            } else if (toPrice.compareTo(BigDecimal.ZERO) != 0) {
                return productRepository.findAllByBrandContainingIgnoreCaseOrModelContainingIgnoreCaseAndPriceLessThanEqual(searchQuery, searchQuery, toPrice, requestedPage);
            } else {
                return productRepository.findAllByBrandContainingIgnoreCaseOrModelContainingIgnoreCaseAndPriceGreaterThanEqual(searchQuery, searchQuery, fromPrice, requestedPage);
            }
        } else {
            if (isFromAndToPricesZero) {
                return productRepository.findAll(requestedPage);
            } else if (fromPrice.compareTo(BigDecimal.ZERO) != 0 && toPrice.compareTo(BigDecimal.ZERO) != 0) {
                return productRepository.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(fromPrice, toPrice, requestedPage);
            } else if (toPrice.compareTo(BigDecimal.ZERO) != 0) {
                return productRepository.findAllByPriceLessThanEqual(toPrice, requestedPage);
            } else {
                return productRepository.findAllByPriceGreaterThanEqual(fromPrice, requestedPage);
            }
        }
    }

    private int resolvePageSize(String size) {
        if (size == null || size.equals("0")) {
            size = String.valueOf(pageProperties.getMaxPageSize());
        }
        int pageSize = Integer.parseInt(size);
        if (pageProperties.getMaxPageSize() == 0) {
            return pageSize;
        }
        if (pageSize > pageProperties.getMaxPageSize()) {
            pageSize = pageProperties.getMaxPageSize();
        }
        return pageSize;
    }
}
