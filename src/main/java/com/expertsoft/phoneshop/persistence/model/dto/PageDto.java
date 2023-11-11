package com.expertsoft.phoneshop.persistence.model.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public record PageDto<T>(Pageable pageable, Page<T> page) {

}
