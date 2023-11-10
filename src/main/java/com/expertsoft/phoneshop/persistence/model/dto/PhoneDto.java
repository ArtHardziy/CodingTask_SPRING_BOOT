package com.expertsoft.phoneshop.persistence.model.dto;

import java.math.BigDecimal;

public record PhoneDto(Long id, String brand, String model, String image, String description, BigDecimal price) {
}