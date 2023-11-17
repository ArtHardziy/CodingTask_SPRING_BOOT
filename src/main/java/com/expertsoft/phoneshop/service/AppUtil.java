package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.PhoneSortingType;
import com.expertsoft.phoneshop.persistence.model.dto.SearchFormModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.expertsoft.phoneshop.PhoneShopConstants.*;

public class AppUtil {

    private AppUtil() {

    }

    public static Pageable processPageableRequest(String pageNum, String pageSize, String sortBy, String sortOrder,
                                                  Pageable pageable) {
        int number = pageNum != null && !pageNum.isEmpty() ? Integer.parseInt(pageNum)
                : pageable.getPageNumber();
        int size = pageSize != null && !pageSize.isEmpty() ? Integer.parseInt(pageSize)
                : pageable.getPageSize();
        Sort.Order order = resolveSortOrder(sortBy, sortOrder);

        return PageRequest.of(--number, size, Sort.by(List.of(order)));
    }

    public static Pageable processPageableRequest(Integer pageNum, Integer pageSize,
                                                  String sortBy, String sortOrder) {
        Sort.Order order = resolveSortOrder(sortBy, sortOrder);
        return PageRequest.of(--pageNum, pageSize, Sort.by(List.of(order)));
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

    public static List<Integer> resolvePlpPagingNumbers(int currentPageNumber, int maxNumberOfPages, int totalPages) {
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

    public static SearchFormModel populateSearchFormModelIfSessionSearchFormModelHasNotBlankValues(HttpSession httpSession) {
        var sessionSearchQuery = (String) httpSession.getAttribute(SEARCH_QUERY);
        var sessionFromPrice = (String) httpSession.getAttribute(FROM_PRICE);
        var sessionToPrice = (String) httpSession.getAttribute(TO_PRICE);
        var searchFormModel = new SearchFormModel();
        if (sessionSearchQuery != null && !sessionSearchQuery.isBlank()) {
            searchFormModel.setSearchQuery(sessionSearchQuery);
        }
        if (sessionFromPrice != null && !sessionFromPrice.isBlank()) {
            searchFormModel.setFromPrice(sessionFromPrice);
        }
        if (sessionToPrice != null && !sessionToPrice.isBlank()) {
            searchFormModel.setToPrice(sessionToPrice);
        }
        return searchFormModel;
    }
}
