package com.expertsoft.phoneshop.persistence.model.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record PlpDto<T>(Pageable pageable,
                        Page<T> page,
                        List<Integer> plpPagingNumbers,
                        SearchFormModel searchFormModel) {

}
