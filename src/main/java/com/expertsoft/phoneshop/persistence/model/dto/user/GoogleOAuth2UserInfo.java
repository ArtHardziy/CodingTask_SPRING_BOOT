package com.expertsoft.phoneshop.persistence.model.dto.user;

import java.util.Map;

import static com.expertsoft.phoneshop.PhoneShopConstants.*;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get(ID_GOOGLE_USER_ATTR);
    }

    @Override
    public String getName() {
        return (String) attributes.get(NAME_GOOGLE_USER_ATTR);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get(EMAIL_GOOGLE_USER_ATTR);
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get(IMAGE_URL_GOOGLE_ATTR);
    }
}
