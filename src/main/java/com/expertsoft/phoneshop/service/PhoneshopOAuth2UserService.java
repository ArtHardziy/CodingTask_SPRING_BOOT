package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.exceptions.OAuth2AuthenticationProcessingException;
import com.expertsoft.phoneshop.persistence.model.*;
import com.expertsoft.phoneshop.persistence.model.dto.user.OAuth2UserInfo;
import com.expertsoft.phoneshop.persistence.model.dto.user.OAuth2UserInfoFactory;
import com.expertsoft.phoneshop.persistence.repository.PhoneshopUserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PhoneshopOAuth2UserService extends DefaultOAuth2UserService {

    private final PhoneshopUserRepository userRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider. Check your provider account");
        }

        log.debug("PhoneshopUserInfoDto is {}", oAuth2UserInfo);
        Optional<PhoneshopUser> phoneshopUserOptional = userRepo.findByName(oAuth2UserInfo.getName());
        log.debug("User is {}", phoneshopUserOptional);

        PhoneshopUser phoneshopUser;
        if (phoneshopUserOptional.isPresent()) {
            PhoneshopUser user = phoneshopUserOptional.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase()))) {
                throw new OAuth2AuthenticationProcessingException(String.format("Your are already signed up with %s account." +
                        "Please user your %s account to log in", user.getProvider(), user.getProvider()));
            }
            phoneshopUser = updateExistingUser(user, oAuth2UserInfo);
        } else {
            phoneshopUser = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return PhoneshopUserPrincipal.create(phoneshopUser, oAuth2UserInfo.getAttributes());
    }

    private PhoneshopUser registerNewUser(OAuth2UserRequest oAuth2UserRequest,
                                          OAuth2UserInfo oAuth2UserInfo) {
        PhoneshopUser user = new PhoneshopUser();

        var clientRegistrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        user.setProvider(AuthProvider.valueOf(clientRegistrationId.toUpperCase()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setAvatar_url(oAuth2UserInfo.getImageUrl());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        Role userRole = new Role();
        userRole.setRoleType(RoleType.USER);
        user.setRole(userRole);
        return userRepo.save(user);
    }

    private PhoneshopUser updateExistingUser(PhoneshopUser existingUser,
                                             OAuth2UserInfo phoneshopUserInfoDto) {
        existingUser.setName(phoneshopUserInfoDto.getName());
        existingUser.setAvatar_url(phoneshopUserInfoDto.getImageUrl());
        existingUser.setUpdatedAt(LocalDateTime.now());
        return userRepo.save(existingUser);
    }
}