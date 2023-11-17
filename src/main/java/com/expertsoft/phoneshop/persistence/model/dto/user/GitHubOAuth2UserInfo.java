package com.expertsoft.phoneshop.persistence.model.dto.user;

import java.util.Map;

import static com.expertsoft.phoneshop.PhoneShopConstants.*;

public class GitHubOAuth2UserInfo extends OAuth2UserInfo {

    public GitHubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get(ID_GITHUB_USER_ATTR)).toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get(LOGIN_GITHUB_USER_ATTR);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get(EMAIL_GITHUB_USER_ATTR);
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get(AVATAR_URL_GITHUB_USER_ATTR);
    }
}
