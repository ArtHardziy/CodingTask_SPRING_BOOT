package com.expertsoft.phoneshop.controller;

import com.expertsoft.phoneshop.persistence.model.dto.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    @GetMapping
    public String getLoginForm(Model model) {
        model.addAttribute("signInRequest", new SignInRequest());
        return "loginPage";
    }
}
