package com.hobbygo.api.hobbygoapi.restapi.resource.assembler;

import com.hobbygo.api.hobbygoapi.model.registration.PasswordResetToken;
import com.hobbygo.api.hobbygoapi.restapi.controller.UserRestController;
import com.hobbygo.api.hobbygoapi.restapi.resource.PasswordResetTokenResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class PasswordResetTokenResourceAssembler extends ResourceAssemblerSupport<PasswordResetToken,PasswordResetTokenResource> {

    private PasswordResetToken passwordResetToken;
    private String passwordResetTokenStatus;

    public PasswordResetTokenResourceAssembler(PasswordResetToken passwordResetToken, String tokenStatus){
        super(UserRestController.class,PasswordResetTokenResource.class);
        setPasswordResetToken(passwordResetToken);
        setPasswordResetTokenStatus(tokenStatus);
    }

    @Override
    public PasswordResetTokenResource toResource(PasswordResetToken token) {
        return createResourceWithId(token.getToken(),token);
    }

    @Override
    protected PasswordResetTokenResource instantiateResource(PasswordResetToken token) {
        return new PasswordResetTokenResource(getPasswordResetTokenStatus());
    }

    public PasswordResetToken getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public String getPasswordResetTokenStatus() {
        return passwordResetTokenStatus;
    }

    public void setPasswordResetTokenStatus(String passwordResetTokenStatus) {
        this.passwordResetTokenStatus = passwordResetTokenStatus;
    }
}
