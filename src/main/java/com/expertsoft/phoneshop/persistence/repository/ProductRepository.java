package com.expertsoft.phoneshop.persistence.repository;

import com.expertsoft.phoneshop.persistence.model.Phone;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Phone, Long> {

    List<Phone> findAllByModel(String model, Pageable pageable);

}
