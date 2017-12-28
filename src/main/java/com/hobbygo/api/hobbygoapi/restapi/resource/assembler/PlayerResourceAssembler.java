package com.hobbygo.api.hobbygoapi.restapi.resource.assembler;

import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.restapi.controller.UserRestController;
import com.hobbygo.api.hobbygoapi.restapi.resource.FactoryResource;
import com.hobbygo.api.hobbygoapi.restapi.resource.PlayerResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PlayerResourceAssembler extends ResourceAssemblerSupport<Player,PlayerResource> {

    private FactoryResource factoryResource;

    public PlayerResourceAssembler(FactoryResource factoryResource){
        super(UserRestController.class,PlayerResource.class);
        setFactoryResource(factoryResource);
    }

    @Override
    public PlayerResource toResource(Player player) {
        PlayerResource playerResource = createResourceWithId(player.getId(),player);
/*
        if(!player.getGroupList().isEmpty())
            playerResource.add(linkTo(methodOn(
                    GroupRestController.class
                    ).getAllGroups(player.getUserName())
            ).withRel("groups"));

        if(!player.getEventoList().isEmpty())
            playerResource.add(linkTo(methodOn(
                    EventoRestController.class
                    ).getAllEventos(player.getUserName())
            ).withRel("eventos"));
*/
        if(!player.getContactList().isEmpty())
            playerResource.add(linkTo(methodOn(
                    UserRestController.class
                    ).setContacts(player.getUserName(),null)
            ).withRel("contacts"));

        if(!player.getFriendList().isEmpty())
            playerResource.add(linkTo(methodOn(
                    UserRestController.class
                    ).request4Relationship(player.getUserName(),null)
            ).withRel("friends"));
        
        return playerResource;
    }

    @Override
    protected PlayerResource instantiateResource(Player player) {
        return new PlayerResource(player, getFactoryResource());
    }

    public FactoryResource getFactoryResource() {
        return factoryResource;
    }

    private void setFactoryResource(FactoryResource factoryResource) {
        this.factoryResource = factoryResource;
    }
}
