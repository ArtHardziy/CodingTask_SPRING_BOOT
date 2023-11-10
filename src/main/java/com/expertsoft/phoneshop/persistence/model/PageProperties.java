package com.expertsoft.phoneshop.persistence.model;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "phoneshop.plp")
@Setter
public class PageProperties {
    private String maxNumberOfPages;
    private String maxPageSize;

    public int getMaxNumberOfPages() {
        return Integer.parseInt(this.maxNumberOfPages);
    }
    public int getMaxPageSize() {
        return Integer.parseInt(this.maxPageSize);
    }

}
