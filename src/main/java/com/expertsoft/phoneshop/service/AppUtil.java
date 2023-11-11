package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.PhoneSortingType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class AppUtil {

    public static Pageable processPageableRequest(String pageNum, String pageSize, String sortBy, String sortOrder,
                                                  Pageable pageable) {
        int number = pageNum != null && !pageNum.isEmpty() ? Integer.parseInt(pageNum)
                : pageable.getPageNumber();
        int size = pageSize != null && !pageSize.isEmpty() ? Integer.parseInt(pageSize)
                : pageable.getPageSize();
        Sort.Order order = resolveSortOrder(sortBy, sortOrder);

        return PageRequest.of(--number, size, Sort.by(List.of(order)));
    }

    private static Sort.Order resolveSortOrder(String sortBy, String sortOrder) {
        Sort.Direction sortDirection = switch (sortOrder.toLowerCase()) {
            case "asc" -> Sort.Direction.ASC;
            case "desc" -> Sort.Direction.DESC;
            default -> Sort.DEFAULT_DIRECTION;
        };
        PhoneSortingType phoneSortingType = PhoneSortingType.valueOf(sortBy.toUpperCase());
        return new Sort.Order(sortDirection, phoneSortingType.getSortingType());
    }

}
