package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.PhoneshopUser;
import com.expertsoft.phoneshop.persistence.model.dto.AdminPanel;
import com.expertsoft.phoneshop.persistence.repository.UserPageableRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminPanelService {

    private final UserPageableRepository userPageableRepo;

    public AdminPanel getAdminPanel(String pageNum, String pageSize) {
        var usersPage = getUsesrPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        AdminPanel adminPanel = new AdminPanel();
        adminPanel.getSimpleDateFormat().applyPattern("yyyy-MM-dd'T'hh:mm");
        adminPanel.setRegisteredUsersList(usersPage.getContent());
        adminPanel.setNumberRegisteredUsers(usersPage.getTotalElements());
        adminPanel.setPageSize(Integer.parseInt(pageSize));
        adminPanel.setCurrentPageNumber(Integer.parseInt(pageNum) - 1);
        adminPanel.setTotalPages(usersPage.getTotalPages());

        return adminPanel;
    }

    private Page<PhoneshopUser> getUsesrPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return userPageableRepo.findAll(pageRequest);
    }
}
