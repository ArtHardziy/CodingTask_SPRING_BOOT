package com.expertsoft.phoneshop.persistence.model.dto;

import com.expertsoft.phoneshop.persistence.model.PhoneshopUser;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.List;

@Data
public class AdminPanel {
    private Long numberRegisteredUsers;
    private List<PhoneshopUser> registeredUsersList;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    private int pageSize;
    private int currentPageNumber;
    private int totalPages;
}
