package com.hobbygo.api.hobbygoapi.restapi.resource;

import com.hobbygo.api.hobbygoapi.restapi.controller.RootRestController;
import com.hobbygo.api.hobbygoapi.restapi.controller.UserRestController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PasswordResetTokenResource extends ResourceSupport {

    private String tokenStatus;

    public PasswordResetTokenResource(String tokenStatus){
        setTokenStatus(tokenStatus);

        add(ControllerLinkBuilder.linkTo(methodOn(
                UserRestController.class
                ).savePassword(null,null)
        ).withRel("updatePassword"));
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }
}
