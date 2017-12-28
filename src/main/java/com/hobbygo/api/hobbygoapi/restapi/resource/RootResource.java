package com.hobbygo.api.hobbygoapi.restapi.resource;

import com.hobbygo.api.hobbygoapi.restapi.controller.RootRestController;
import com.hobbygo.api.hobbygoapi.restapi.controller.UserRestController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class RootResource extends ResourceSupport {

    public RootResource() {

        add(
                linkTo(UserRestController.class).withRel("users")
        );

        add(ControllerLinkBuilder.linkTo(methodOn(
                RootRestController.class
                ).getFilterEventos(0,0,0)
        ).withRel("eventos"));

        add(linkTo(methodOn(RootRestController.class
                ).getCountFilterEventos(0,0,0)
        ).withRel("eventosCount"));
    }
}
