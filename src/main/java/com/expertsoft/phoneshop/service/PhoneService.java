package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.Phone;
import com.expertsoft.phoneshop.persistence.model.dto.PageDto;
import com.expertsoft.phoneshop.persistence.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhoneService {

    private ProductRepository productRepository;

    public PageDto<Phone> getPhonesPage(String number, String size, String sortBy, String sortOrder) {
        int pageNum = Integer.parseInt(number);
        int pageSize = Integer.parseInt(size);
        var phonesPageable = PageRequest.of(pageNum - 1,
                pageSize,
                Sort.by(Sort.Direction.fromString(sortOrder),
                        sortBy));
        Page<Phone> page = productRepository.findAll(phonesPageable);
        return new PageDto<>(pageNum, pageSize, sortBy, sortOrder, page);
    }
}
