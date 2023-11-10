package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.PageProperties;
import com.expertsoft.phoneshop.persistence.model.Phone;
import com.expertsoft.phoneshop.persistence.model.dto.PageDto;
import com.expertsoft.phoneshop.persistence.model.dto.PhoneDto;
import com.expertsoft.phoneshop.persistence.model.dto.PlpDto;
import com.expertsoft.phoneshop.persistence.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public PlpDto getPhonesPlp(String number, String size, String sortBy, String sortOrder) {
        int pageNum = Integer.parseInt(number);
        int pageSize = resolvePageSize(size);
        var phonesPageable = PageRequest.of(pageNum - 1,
                pageSize,
                Sort.by(Sort.Direction.fromString(sortOrder),
                        sortBy));
        Page<Phone> page = productRepository.findAll(phonesPageable);
        var plpPagingNumbers = resolvePlpPagingNumbers(pageNum, pageProperties.getMaxNumberOfPages(), page.getTotalPages());
        return new PlpDto(new PageDto<>(pageNum, pageSize, sortBy, sortOrder, page), plpPagingNumbers);
    }

    private List<Integer> resolvePlpPagingNumbers(int currentPageNumber, int maxNumberOfPages, int totalPages) {
        int arrayLength = Math.min(maxNumberOfPages, totalPages);
        var plpPagingNumbers = new ArrayList<Integer>(arrayLength);

        int centerIndex = arrayLength / 2 + arrayLength % 2;
        int indentedLeft = currentPageNumber - centerIndex - 1;
        int indentedRight = currentPageNumber + centerIndex;
        int startPlpIndex = currentPageNumber - centerIndex;
        if (indentedLeft <= 0) {
            startPlpIndex = 1;
        } else if (indentedRight >= totalPages) {
            startPlpIndex = totalPages - arrayLength + 1;
        }
        if (maxNumberOfPages == 1) {
            startPlpIndex = currentPageNumber;
        }
        for (int i = 0; i < arrayLength; i++) {
            plpPagingNumbers.add(startPlpIndex++);
        }
        return plpPagingNumbers;
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
