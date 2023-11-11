package com.expertsoft.phoneshop.persistence.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.SessionScope;

import java.math.BigDecimal;

@Data
public class SearchFormModel {
    private String searchQuery = "";
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "Quantity must be a numeric value")
    private String fromPrice = "";
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "Quantity must be a numeric value")
    private String toPrice = "";

    public BigDecimal getBigDecimalFromPrice() {
        if (this.fromPrice.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(Long.parseLong(fromPrice));
    }

    public BigDecimal getBigDecimalToPrice() {
        if (this.toPrice.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(Long.parseLong(toPrice));
    }

}
