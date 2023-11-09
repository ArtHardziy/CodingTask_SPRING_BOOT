package com.expertsoft.phoneshop.persistence.model.dto;

import org.springframework.data.domain.Page;

public record PageDto<T>(int pageNum, int pageSize, String sortBy, String sortOrder, Page<T> page) {

}
