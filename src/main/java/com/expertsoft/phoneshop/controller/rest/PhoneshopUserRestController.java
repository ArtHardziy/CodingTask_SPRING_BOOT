package com.expertsoft.phoneshop.controller.rest;

import com.expertsoft.phoneshop.persistence.model.PhoneshopUserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class PhoneshopUserRestController {

    @GetMapping
    public Map<String, Object> user(@AuthenticationPrincipal PhoneshopUserPrincipal principal) {
        if (principal != null) {
            if (principal.getAttributes() != null) {
                return Collections.singletonMap("name", principal.getAttribute("name"));
            } else if (principal.getName() != null) {
                return Collections.singletonMap("name", principal.getName());
            }
        }
        return null;
    }

}
