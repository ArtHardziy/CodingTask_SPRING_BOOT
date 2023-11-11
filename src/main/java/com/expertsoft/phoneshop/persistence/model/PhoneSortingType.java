package com.expertsoft.phoneshop.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum PhoneSortingType {
    ID("id"),
    BRAND("brand"),
    MODEL("model"),
    PRICE("price");

    private final String sortingType;
}
