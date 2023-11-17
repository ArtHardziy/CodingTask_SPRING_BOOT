package com.expertsoft.phoneshop.persistence.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

import static com.expertsoft.phoneshop.PhoneShopConstants.SEARCH_FORM_PRICE_VALIDATION_EXC_MSG;

@Data
public class SearchFormModel {
    private String searchQuery = "";
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = SEARCH_FORM_PRICE_VALIDATION_EXC_MSG)
    private String fromPrice = "";
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = SEARCH_FORM_PRICE_VALIDATION_EXC_MSG)
    private String toPrice = "";

    public BigDecimal getBigDecimalFromPrice() {
        if (this.fromPrice.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(Long.parseLong(fromPrice));
    }

    public BigDecimal getBigDecimalToPrice() {
        if (this.toPrice.isEmpty()) return BigDecimal.valueOf(Long.MAX_VALUE);
        return new BigDecimal(Long.parseLong(toPrice));
    }
}