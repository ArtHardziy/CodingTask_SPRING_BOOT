package com.expertsoft.phoneshop.service.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.expertsoft.phoneshop.PhoneShopConstants.*;
import static jakarta.servlet.http.HttpServletResponse.SC_TEMPORARY_REDIRECT;

@Component
public class LoginPageFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (isAuthenticated() && LOGIN_PATH.equalsIgnoreCase(req.getRequestURI())) {
            String encodedRedirectURL = ((HttpServletResponse) response).encodeRedirectURL(
                    req.getContextPath() + ROOT_CONTEXT_PATH);

            res.setStatus(SC_TEMPORARY_REDIRECT);
            res.setHeader(LOCATION_HEADER, encodedRedirectURL);
        }
        chain.doFilter(req, res);
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
