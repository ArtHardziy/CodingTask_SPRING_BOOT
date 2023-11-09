package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.Phone;
import com.expertsoft.phoneshop.persistence.repository.PhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhoneService {

    private PhoneRepository phoneRepository;

    public Page<Phone> getPhonesPage(Pageable pageable) {
        return phoneRepository.findAll(pageable);
    }
}
