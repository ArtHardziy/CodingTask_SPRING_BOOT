package com.expertsoft.phoneshop.persistence.model.dto;

import java.util.List;

public record PlpDto(PageDto<?> pageDto, List<Integer> plpPagingNumbers) {

}
