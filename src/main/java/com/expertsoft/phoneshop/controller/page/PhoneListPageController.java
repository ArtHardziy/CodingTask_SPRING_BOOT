package com.expertsoft.phoneshop.controller.page;

import com.expertsoft.phoneshop.service.PhoneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.expertsoft.phoneshop.PhoneShopConstants.PHONES_PATH;

@Controller
@RequestMapping(PHONES_PATH)
@Slf4j
@AllArgsConstructor
public class PhoneListPageController {

    private static final String PHONE_LIST_PAGE = "phoneListPage";
    private static final String PHONES = "phones";

    private PhoneService phoneService;

    @GetMapping
    public String getPhoneList(@RequestParam(defaultValue = "1") String pageNum,
                               @RequestParam(defaultValue = "5") String pageSize,
                               @RequestParam(defaultValue = "id") String sortBy,
                               @RequestParam(defaultValue = "ASC") String sortOrder,
                               Model model) {
        var phonesPage = phoneService.getPhonesPage(pageNum, pageSize, sortBy, sortOrder);
        model.addAttribute(PHONES, phonesPage);

        return PHONE_LIST_PAGE;
    }
}
