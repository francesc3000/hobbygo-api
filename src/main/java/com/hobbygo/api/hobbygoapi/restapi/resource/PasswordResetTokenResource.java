package com.hobbygo.api.hobbygoapi.restapi.resource;

import org.springframework.hateoas.ResourceSupport;

public class PasswordResetTokenResource extends ResourceSupport {

    private String tokenStatus;

    public PasswordResetTokenResource(String tokenStatus){
        setTokenStatus(tokenStatus);
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }
}
