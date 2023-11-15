package com.expertsoft.phoneshop.controller.page;

import com.expertsoft.phoneshop.persistence.model.PhoneshopUser;
import com.expertsoft.phoneshop.persistence.model.dto.AdminPanel;
import com.expertsoft.phoneshop.persistence.model.dto.SearchFormModel;
import com.expertsoft.phoneshop.service.AdminPanelService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.expertsoft.phoneshop.PhoneShopConstants.ADMIN_PANEL_PAGE;
import static com.expertsoft.phoneshop.PhoneShopConstants.ADMIN_PATH;
import static com.expertsoft.phoneshop.service.AppUtil.resolvePlpPagingNumbers;

@Controller
@RequestMapping(ADMIN_PATH)
@AllArgsConstructor
public class AdminPageController {

    private final AdminPanelService adminPanelService;

    @GetMapping
    public String getAdminPanel(@RequestParam(defaultValue = "1") String pageNum,
                                @RequestParam(required = false, defaultValue = "10") String pageSize,
                                @RequestParam(defaultValue = "id") String sortBy,
                                @RequestParam(defaultValue = "ASC") String sortOrder,
                                @ModelAttribute("searchFormModel") SearchFormModel searchFormModel,
                                BindingResult bindingResult,
                                Pageable pageable,
                                Model model) {
        AdminPanel adminPanel = new AdminPanel();
        adminPanel.getSimpleDateFormat().applyPattern("yyyy-MM-dd'T'hh:mm");
        Page<PhoneshopUser> usesrPage = adminPanelService.getUsesrPage(Integer.parseInt(pageNum),
                Integer.parseInt(pageSize));
        adminPanel.setRegisteredUsersList(usesrPage.getContent());
        adminPanel.setNumberRegisteredUsers(usesrPage.getTotalElements());
        adminPanel.setPageSize(Integer.parseInt(pageSize));
        adminPanel.setCurrentPageNumber(Integer.parseInt(pageNum) - 1);
        adminPanel.setTotalPages(usesrPage.getTotalPages());
        model.addAttribute("registeredUsers", adminPanel);

        var plpPagingNumbers = resolvePlpPagingNumbers(
                Integer.parseInt(pageNum),
                5,
                usesrPage.getTotalPages()
        );
        model.addAttribute("plpPagingNumbers", plpPagingNumbers);
        return ADMIN_PANEL_PAGE;
    }
}
