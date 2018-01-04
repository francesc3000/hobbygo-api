package com.hobbygo.api.hobbygoapi.restapi.resource.assembler;

import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.restapi.controller.EventoRestController;
import com.hobbygo.api.hobbygoapi.restapi.controller.GroupRestController;
import com.hobbygo.api.hobbygoapi.restapi.controller.UserRestController;
import com.hobbygo.api.hobbygoapi.restapi.resource.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<User,UserResource> {


    public UserResourceAssembler(){
        super(UserRestController.class,UserResource.class);
    }

    @Override
    public UserResource toResource(User user) {
        UserResource userResource = createResourceWithId(user.getId(),user);

        userResource.add(linkTo(methodOn(
                UserRestController.class
                ).getUser(user.getUserName())
        ).withSelfRel());

        if(user.isEnabled()) {
            userResource.add(linkTo(methodOn(
                    GroupRestController.class
                    ).getAllGroups(user.getUserName())
            ).withRel("groups"));

            userResource.add(linkTo(methodOn(EventoRestController.class
            ).getAllEventos(user.getUserName())).withRel("eventos"));
        }

        return userResource;
    }

    @Override
    protected UserResource instantiateResource(User user) {
        return new UserResource(user);
    }
}
