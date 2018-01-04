package com.hobbygo.api.hobbygoapi.restapi.resource;

import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import com.hobbygo.api.hobbygoapi.model.entity.Group;
import com.hobbygo.api.hobbygoapi.model.entity.Play;
import com.hobbygo.api.hobbygoapi.service.UserService;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VerificationTokenResource extends ResourceSupport {

    private String tokenStatus;

    public VerificationTokenResource(String tokenStatus){
        setTokenStatus(tokenStatus);
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }
}
