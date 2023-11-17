package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.AppProperties;
import com.expertsoft.phoneshop.persistence.model.Phone;
import com.expertsoft.phoneshop.persistence.model.dto.PhoneDto;
import com.expertsoft.phoneshop.persistence.model.dto.PlpDto;
import com.expertsoft.phoneshop.persistence.model.dto.SearchFormModel;
import com.expertsoft.phoneshop.persistence.repository.PhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.expertsoft.phoneshop.service.AppUtil.resolvePlpPagingNumbers;

@Service
@AllArgsConstructor
public class PhoneService {

    private PhoneRepository phoneRepo;
    private AppProperties appProperties;

    public PhoneDto getPhoneById(Long id) {
        Optional<Phone> optionalPhone = phoneRepo.findById(id);
        if (optionalPhone.isEmpty()) {
            return null;
        } else {
            Phone phone = optionalPhone.get();
            return new PhoneDto(phone.getId(), phone.getBrand(), phone.getModel(), phone.getImage(), phone.getDescription(), phone.getPrice());
        }
    }

    public PlpDto<Phone> getPhonePlp(SearchFormModel searchFormModel,
                              Pageable requestedPage) {

        Page<Phone> phonesPageBySearchFormModel = getPhonesPageBySearchFormModel(searchFormModel,
                requestedPage);

        var plpPagingNumbers = resolvePlpPagingNumbers(requestedPage.getPageNumber() + 1,
                appProperties.getMaxNumberOfPages(),
                phonesPageBySearchFormModel.getTotalPages());
        return new PlpDto<>(requestedPage,
                phonesPageBySearchFormModel,
                plpPagingNumbers,
                searchFormModel);
    }

    private int resolvePageSize(int size) {
        if (appProperties.getMaxPageSize() == 0) {
            return size;
        }
        if (size > appProperties.getMaxPageSize()) {
            size = appProperties.getMaxPageSize();
        }
        return size;
    }

    private Page<Phone> getPhonesPageBySearchFormModel(SearchFormModel searchFormModel,
                                                      Pageable pageable) {
        var pageRequest = createPageRequestByPageNumberAndPageSize(pageable.getPageNumber(),
                pageable.getPageSize());
        var allPhones = getPhonesBySearchModel(searchFormModel);

        var start = (int) pageRequest.getOffset();
        var end = Math.min((start + pageRequest.getPageSize()), allPhones.size());
        var pageContent = allPhones.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, allPhones.size());
    }

    private List<Phone> getPhonesBySearchModel(SearchFormModel searchFormModel) {
        List<Phone> phonesList = getPhonesBySearchQuery(searchFormModel.getSearchQuery());
        var fromPrice = searchFormModel.getBigDecimalFromPrice();
        var toPrice = searchFormModel.getBigDecimalToPrice();
        return filterPhonesListBetweenFromPriceAndToPrice(phonesList, fromPrice, toPrice);
    }

    private List<Phone> filterPhonesListBetweenFromPriceAndToPrice(List<Phone> phonesList,
                                                                   BigDecimal fromPrice,
                                                                   BigDecimal toPrice) {
        return phonesList.stream()
                .filter(phone -> fromPrice.compareTo(phone.getPrice()) <= 0)
                .filter(phone -> toPrice.compareTo(phone.getPrice()) >= 0)
                .toList();
    }

    private List<Phone> getPhonesBySearchQuery(String searchQuery) {
        if (searchQuery.isBlank()) {
            return phoneRepo.findAll();
        } else {
            return phoneRepo
                    .findAllByModelContainingIgnoreCaseOrBrandContainingIgnoreCase(
                            searchQuery, searchQuery);
        }
    }

    private Pageable createPageRequestByPageNumberAndPageSize(int page, int size) {
        return PageRequest.of(page, resolvePageSize(size));
    }
}
