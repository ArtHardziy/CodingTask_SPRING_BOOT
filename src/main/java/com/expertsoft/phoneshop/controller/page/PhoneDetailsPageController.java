package com.expertsoft.phoneshop.controller.page;

import com.expertsoft.phoneshop.service.PhoneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.expertsoft.phoneshop.PhoneShopConstants.*;

@Controller
@RequestMapping(PHONES_PATH)
@AllArgsConstructor
public class PhoneDetailsPageController {

    private PhoneService phoneService;

    @GetMapping(PHONE_ID_REQUEST_PATH)
    public String getPhoneDetails(@PathVariable(PHONE_ID_ATTRIBUTE) Long phoneId, Model model) {
        if (phoneId == null) {
            return PHONE_DETAILS_PAGE;
        }
        model.addAttribute(PHONE, phoneService.getPhoneById(phoneId));
        return PHONE_DETAILS_PAGE;
    }
}
