package com.expertsoft.phoneshop.controller;

import com.expertsoft.phoneshop.persistence.model.dto.AdminPanel;
import com.expertsoft.phoneshop.service.AdminPanelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.expertsoft.phoneshop.PhoneShopConstants.*;
import static com.expertsoft.phoneshop.service.AppUtil.resolvePlpPagingNumbers;

@Controller
@RequestMapping(ADMIN_PATH)
@AllArgsConstructor
public class AdminPageController {

    private final AdminPanelService adminPanelService;

    @GetMapping
    public String getAdminPanel(
            @RequestParam(defaultValue = "1") String pageNum,
            @RequestParam(required = false, defaultValue = "10") String pageSize,
            Model model
    ) {
        AdminPanel adminPanel = adminPanelService.getAdminPanel(pageNum, pageSize);
        model.addAttribute(REGISTERED_USERS, adminPanel);

        var plpPagingNumbers = resolvePlpPagingNumbers(
                Integer.parseInt(pageNum),
                5,
                adminPanel.getTotalPages()
        );
        model.addAttribute(PLP_PAGING_NUMBERS, plpPagingNumbers);
        return ADMIN_PANEL_PAGE;
    }
}