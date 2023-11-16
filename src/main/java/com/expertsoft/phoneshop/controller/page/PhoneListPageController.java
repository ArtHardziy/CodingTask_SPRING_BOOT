package com.expertsoft.phoneshop.controller.page;

import com.expertsoft.phoneshop.persistence.model.dto.SearchFormModel;
import com.expertsoft.phoneshop.service.PhoneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping
    public ModelAndView submitSearch(Model model,
            @ModelAttribute("searchFormModel") SearchFormModel searchFormModel,
                                      BindingResult bindingResult) {
        var pagedProductListPage = getPagedProductListPage(model,
                1,
                7,
                "id",
                "asc",
                searchFormModel,
                bindingResult);

        return pagedProductListPage;
    }

    @GetMapping
    public ModelAndView getPagedProductListPage(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "7") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            SearchFormModel searchFormModel,
            BindingResult bindingResult
    ) {
        var modelAndView = new ModelAndView(PHONE_LIST_PAGE);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("searchFormModelError", true);
            modelAndView.setStatus(HttpStatus.PERMANENT_REDIRECT);
            return modelAndView;
        }
        try {
            modelAndView.addObject(PHONES_PAGE,
                    phoneService.getPhonePlp(searchFormModel,
                            processPageableRequest(pageNum, pageSize, sortBy, sortOrder))
            );
        } catch (Exception ex) {
            modelAndView.addObject("error", "Error during forming PLP. Redirected to home");
            log.error("Error during forming PLP", ex);
            modelAndView.setStatus(HttpStatus.PERMANENT_REDIRECT);
            return modelAndView;
        }
        return modelAndView;
    }
}
