package com.expertsoft.phoneshop.controller.page;

import com.expertsoft.phoneshop.persistence.model.Phone;
import com.expertsoft.phoneshop.persistence.model.PhoneSortingType;
import com.expertsoft.phoneshop.persistence.model.dto.PlpDto;
import com.expertsoft.phoneshop.persistence.model.dto.SearchFormModel;
import com.expertsoft.phoneshop.service.PhoneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.expertsoft.phoneshop.PhoneShopConstants.PHONES_PATH;
import static com.expertsoft.phoneshop.service.AppUtil.processPageableRequest;

@Controller
@RequestMapping(PHONES_PATH)
@Slf4j
@AllArgsConstructor
public class PhoneListPageController {

    private static final String PHONE_LIST_PAGE = "phoneListPage";
    private static final String PHONES_PAGE = "phonesPage";

    private PhoneService phoneService;

    @GetMapping
    public String getPhoneList(@RequestParam(defaultValue = "1") String pageNum,
                               @RequestParam(required = false, defaultValue = "10") String pageSize,
                               @RequestParam(defaultValue = "id") String sortBy,
                               @RequestParam(defaultValue = "ASC") String sortOrder,
                               @ModelAttribute("searchFormModel") SearchFormModel searchFormModel,
                               BindingResult bindingResult,
                               Pageable pageable,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Check entered values");
            return PHONE_LIST_PAGE;
        }
        Pageable pageRequest = processPageableRequest(pageNum, pageSize, sortBy, sortOrder, pageable);
        PlpDto<Phone> phonesPlpDto = phoneService.getPhonesPlp(pageRequest, searchFormModel);
        model.addAttribute(PHONES_PAGE, phonesPlpDto);
        model.addAttribute("searchFormModel", searchFormModel);
        return PHONE_LIST_PAGE;
    }

    @PostMapping
    public String submitSearch(Model model,
                               @ModelAttribute("searchFormModel") SearchFormModel searchFormModel,
                               BindingResult bindingResult) {
        getPhoneList("1", "10", "id", "asc", searchFormModel, bindingResult, null, model);
        return PHONE_LIST_PAGE;
    }
}
