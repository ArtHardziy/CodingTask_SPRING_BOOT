package com.expertsoft.phoneshop.persistence.model.dto.user;

import com.expertsoft.phoneshop.exceptions.OAuth2AuthenticationProcessingException;
import com.expertsoft.phoneshop.persistence.model.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    private OAuth2UserInfoFactory() {

    }

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.GITHUB.toString())) {
            return new GitHubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Yoops, login with " + registrationId + " is not supported yet.");
        }
    }
}
