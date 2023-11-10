package com.expertsoft.phoneshop.controller.page;

import com.expertsoft.phoneshop.persistence.model.dto.PhoneDto;
import com.expertsoft.phoneshop.service.PhoneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.expertsoft.phoneshop.PhoneShopConstants.PHONES_PATH;

@Controller
@RequestMapping(PHONES_PATH)
@AllArgsConstructor
public class PhoneDetailsPageController {

    private static final String PHONE_DETAILS_PAGE = "phoneDetailsPage";

    private PhoneService phoneService;

    @GetMapping("/{phoneId}")
    public String getPhoneDetails(@PathVariable("phoneId") Long phoneId, Model model) {
        if (phoneId == null) {
            return PHONE_DETAILS_PAGE;
        }
        model.addAttribute("phone", phoneService.getPhoneById(phoneId));
        return PHONE_DETAILS_PAGE;
    }
}
