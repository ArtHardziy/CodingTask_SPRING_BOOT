package com.expertsoft.phoneshop.persistence.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "phoneshop")
@Validated
@Data
public class AppProperties {
    private String maxNumberOfPages;
    private String maxPageSize;
    private SuperAdmin superAdmin = new SuperAdmin();

    public int getMaxNumberOfPages() {
        return Integer.parseInt(this.maxNumberOfPages);
    }

    public int getMaxPageSize() {
        return Integer.parseInt(this.maxPageSize);
    }

    @Data
    public static class SuperAdmin {
        @NotEmpty
        private String name;
        @NotEmpty
        private String email;
        @NotEmpty
        private String password;
        private String company;
        private String location;
    }
}
