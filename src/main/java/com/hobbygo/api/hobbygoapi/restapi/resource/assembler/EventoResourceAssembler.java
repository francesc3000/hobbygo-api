package com.hobbygo.api.hobbygoapi.restapi.resource.assembler;

import com.hobbygo.api.hobbygoapi.model.entity.Evento;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.restapi.controller.EventoRestController;
import com.hobbygo.api.hobbygoapi.restapi.resource.EventoResource;
import com.hobbygo.api.hobbygoapi.security.EventoSecurityService;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


public class EventoResourceAssembler extends ResourceAssemblerSupport<Evento,EventoResource> {

    private String userName;
    private Player player;
    private EventoSecurityService eventoSecurityService;

    public EventoResourceAssembler(String userName, Player player, EventoSecurityService eventoSecurityService){
        super(EventoRestController.class,EventoResource.class);

        setUserName(userName);
        setPlayer(player);
        setEventoSecurityService(eventoSecurityService);
    }

    @Override
    public EventoResource toResource(Evento evento) {
        EventoResource eventoResource = createResourceWithId(evento.getId(),evento,getUserName());
        if(getUserName()!=null && (!getUserName().isEmpty() && !getUserName().equals("Default"))) {

            if (eventoSecurityService.canModifyEvento(getUserName(), evento.getId())){
                if (evento.isPublished()) {
                    eventoResource.add(linkTo(methodOn(
                            EventoRestController.class
                            ).unPublishEvento(getUserName(), evento.getId())
                    ).withRel("unpublish"));

                    if (!evento.isFlowed())
                        eventoResource.add(linkTo(methodOn(
                                EventoRestController.class
                                ).buildEvento(getUserName(), evento.getId())
                        ).withRel("genEvento"));
                } else
                    eventoResource.add(linkTo(methodOn(
                            EventoRestController.class
                            ).publishEvento(getUserName(), evento.getId())
                    ).withRel("publish"));
            }else {
                if (!eventoResource.getMembersUserName().contains(getUserName()))
                    eventoResource.add(linkTo(methodOn(
                            EventoRestController.class
                            ).enrollUser(getUserName(), evento.getId())
                    ).withRel("enroll"));

                if(getPlayer().isEventoFavorite(evento))
                    eventoResource.add(linkTo(methodOn(
                            EventoRestController.class
                            ).nonfavoriteEvento(getUserName(), evento.getId())
                    ).withRel("nonfavorite"));
                else
                    eventoResource.add(linkTo(methodOn(
                            EventoRestController.class
                            ).favoriteEvento(getUserName(), evento.getId())
                    ).withRel("favorite"));
            }
        }
        return eventoResource;
    }

    @Override
    protected EventoResource instantiateResource(Evento evento) {
        return new EventoResource(evento);
    }

    private String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    public Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    private EventoSecurityService getEventoSecurityService() {
        return eventoSecurityService;
    }

    private void setEventoSecurityService(EventoSecurityService eventoSecurityService) {
        this.eventoSecurityService = eventoSecurityService;
    }
}
