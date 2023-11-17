package com.expertsoft.phoneshop.controller;

import com.expertsoft.phoneshop.persistence.model.dto.SearchFormModel;
import com.expertsoft.phoneshop.service.PhoneService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.expertsoft.phoneshop.PhoneShopConstants.*;
import static com.expertsoft.phoneshop.service.AppUtil.populateSearchFormModelIfSessionSearchFormModelHasNotBlankValues;
import static com.expertsoft.phoneshop.service.AppUtil.processPageableRequest;

@Controller
@RequestMapping(PHONES_PATH)
@Slf4j
@AllArgsConstructor
public class PhoneListPageController {

    private final PhoneService phoneService;

    @GetMapping("/searchByForm")
    public ModelAndView getPagedProductListPageBySearchForm(
            HttpSession httpSession,
            @ModelAttribute SearchFormModel searchFormModel,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            var modelAndView = new ModelAndView(PHONE_LIST_PAGE);
            modelAndView.addObject(SEARCH_FORM_MODEL_ERROR, true);
            modelAndView.setStatus(HttpStatus.PERMANENT_REDIRECT);
            return modelAndView;
        }
        httpSession.setAttribute(SEARCH_QUERY, searchFormModel.getSearchQuery());
        httpSession.setAttribute(FROM_PRICE, searchFormModel.getFromPrice());
        httpSession.setAttribute(TO_PRICE, searchFormModel.getToPrice());

        return getPagedProductListPage(1, 7, "id", "asc", null, null, httpSession);
    }

    @GetMapping
    public ModelAndView getPagedProductListPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "7") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @ModelAttribute SearchFormModel search,
            BindingResult bindingResult,
            HttpSession httpSession
    ) {
        var modelAndView = new ModelAndView(PHONE_LIST_PAGE);
        SearchFormModel searchFormModel = populateSearchFormModelIfSessionSearchFormModelHasNotBlankValues(httpSession);

        modelAndView.addObject(PHONES_PAGE,
                phoneService.getPhonePlp(searchFormModel,
                        processPageableRequest(pageNum, pageSize, sortBy, sortOrder))
        );
        modelAndView.addObject(SEARCH_FORM_MODEL, searchFormModel);
        return modelAndView;
    }
}
