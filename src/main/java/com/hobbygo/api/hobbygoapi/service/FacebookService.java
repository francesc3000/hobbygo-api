package com.hobbygo.api.hobbygoapi.service;

import com.hobbygo.api.hobbygoapi.model.mail.ApplicationLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class FacebookService {
    private static final ApplicationLogger logger = ApplicationLogger.getInstance();

    @Value("${spring.social.facebook.appId}")
    String facebookAppId;
    @Value("${spring.social.facebook.appSecret}")
    String facebookSecret;

    private String accessToken;

    public String createFacebookAuthorizationURL(){
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("https://hobbygo-api.herokuapp.com/api/v1/users/facebook");
        params.setScope("public_profile,email,user_birthday");
        return oauthOperations.buildAuthorizeUrl(params);
    }

    public void createFacebookAccessToken(String code) {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, "https://hobbygo-api.herokuapp.com/api/v1/users/facebook", null);
        accessToken = accessGrant.getAccessToken();
        logger.info(accessToken);
    }

    public String getName() {
        Facebook facebook = new FacebookTemplate(accessToken);
        //String[] fields = {"id", "name"};
        //return facebook.fetchObject("me", String.class, fields).getName();
        return facebook.userOperations().getUserProfile().getName();
    }
}
