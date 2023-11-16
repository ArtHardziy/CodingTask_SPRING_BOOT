package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.PageProperties;
import com.expertsoft.phoneshop.persistence.model.Phone;
import com.expertsoft.phoneshop.persistence.model.dto.PhoneDto;
import com.expertsoft.phoneshop.persistence.model.dto.PlpDto;
import com.expertsoft.phoneshop.persistence.model.dto.SearchFormModel;
import com.expertsoft.phoneshop.persistence.repository.PhoneRepository;
import com.expertsoft.phoneshop.persistence.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.expertsoft.phoneshop.service.AppUtil.resolvePlpPagingNumbers;

@Service
@AllArgsConstructor
public class PhoneService {

    private ProductRepository productRepository;
    private PhoneRepository phoneRepo;
    private PageProperties pageProperties;

    public PhoneDto getPhoneById(Long id) {
        Optional<Phone> optionalPhone = productRepository.findById(id);
        if (optionalPhone.isEmpty()) {
            return null;
        } else {
            Phone phone = optionalPhone.get();
            return new PhoneDto(phone.getId(), phone.getBrand(), phone.getModel(), phone.getImage(), phone.getDescription(), phone.getPrice());
        }
    }

    private int resolvePageSize(int size) {
        if (pageProperties.getMaxPageSize() == 0) {
            return size;
        }
        if (size > pageProperties.getMaxPageSize()) {
            size = pageProperties.getMaxPageSize();
        }
        return size;
    }

    public PlpDto<Phone> getPhonePlp(SearchFormModel searchFormModel,
                              Pageable requestedPage) {

        Page<Phone> phonesPageBySearchFormModel = getPhonesPageBySearchFormModel(searchFormModel,
                requestedPage);

        var plpPagingNumbers = resolvePlpPagingNumbers(requestedPage.getPageNumber() + 1,
                pageProperties.getMaxNumberOfPages(),
                phonesPageBySearchFormModel.getTotalPages());
        return new PlpDto<>(requestedPage,
                phonesPageBySearchFormModel,
                plpPagingNumbers,
                searchFormModel);
    }


    public Page<Phone> getPhonesPageBySearchFormModel(SearchFormModel searchFormModel,
                                                      Pageable pageable

    ) {
        var pageRequest = createPageRequestUsing(pageable.getPageNumber(),
                pageable.getPageSize());
        List<Phone> allPhones = getPhonesBySearchModel(searchFormModel);

        var start = (int) pageRequest.getOffset();
        var end = Math.min((start + pageRequest.getPageSize()), allPhones.size());

        var pageContent = allPhones.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, allPhones.size());
    }

    private List<Phone> getPhonesBySearchModel(SearchFormModel searchFormModel) {
        List<Phone> allPhones = getPhonesBySearchQuery(searchFormModel.getSearchQuery());

        var fromPrice = searchFormModel.getBigDecimalFromPrice();
        var toPrice = searchFormModel.getBigDecimalToPrice();
        return allPhones.stream()
                .filter(phone -> fromPrice.compareTo(phone.getPrice()) < 0)
                .filter(phone -> toPrice.compareTo(phone.getPrice()) > 0)
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

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, resolvePageSize(size));
    }
}
