package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.PhoneshopUser;
import com.expertsoft.phoneshop.persistence.repository.UserPageableRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminPanelService {

    private final UserPageableRepository userPageableRepo;


    public Page<PhoneshopUser> getUsesrPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return userPageableRepo.findAll(pageRequest);
    }

}
