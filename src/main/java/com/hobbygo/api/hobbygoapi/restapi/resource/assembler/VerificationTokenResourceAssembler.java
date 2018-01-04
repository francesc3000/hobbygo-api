package com.hobbygo.api.hobbygoapi.restapi.resource.assembler;

import com.hobbygo.api.hobbygoapi.model.registration.VerificationToken;
import com.hobbygo.api.hobbygoapi.restapi.controller.UserRestController;
import com.hobbygo.api.hobbygoapi.restapi.resource.VerificationTokenResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class VerificationTokenResourceAssembler extends ResourceAssemblerSupport<VerificationToken,VerificationTokenResource> {

    private VerificationToken verificationToken;
    private String verificationTokenStatus;

    public VerificationTokenResourceAssembler(VerificationToken verificationToken, String tokenStatus){
        super(UserRestController.class,VerificationTokenResource.class);
        setVerificationToken(verificationToken);
        setVerificationTokenStatus(tokenStatus);
    }

    @Override
    public VerificationTokenResource toResource(VerificationToken token) {
        return createResourceWithId(token.getId(),token);
    }

    @Override
    protected VerificationTokenResource instantiateResource(VerificationToken token) {
        return new VerificationTokenResource(getVerificationTokenStatus());
    }

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getVerificationTokenStatus() {
        return verificationTokenStatus;
    }

    public void setVerificationTokenStatus(String verificationTokenStatus) {
        this.verificationTokenStatus = verificationTokenStatus;
    }
}
