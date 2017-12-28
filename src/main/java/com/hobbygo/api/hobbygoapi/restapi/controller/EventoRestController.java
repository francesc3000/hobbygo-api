package com.hobbygo.api.hobbygoapi.restapi.controller;

import com.hobbygo.api.hobbygoapi.model.entity.Evento;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.restapi.dto.CreateEventoDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyEventoDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyPlayDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ScorePlayersDto;
import com.hobbygo.api.hobbygoapi.restapi.resource.EventoResource;
import com.hobbygo.api.hobbygoapi.restapi.resource.FactoryResource;
import com.hobbygo.api.hobbygoapi.restapi.resource.PlayerResource;
import com.hobbygo.api.hobbygoapi.restapi.resource.ResourceCollection;
import com.hobbygo.api.hobbygoapi.security.EventoSecurityService;
import com.hobbygo.api.hobbygoapi.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/{userName}/eventos")
public class EventoRestController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private FactoryResource factoryResource;

    @Autowired
    private EventoSecurityService eventoSecurityService;

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceCollection<EventoResource>> getAllEventos(@PathVariable String userName){
        return ResponseEntity.ok(
                new ResourceCollection<>(
                        eventoService.getAllEventos(userName)
                                .stream()
                                .map(evento -> factoryResource.getEventoResource(userName, evento))
                                .collect(Collectors.toList())
                )
        );
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(value = "/{eventoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoResource> getEvento(@PathVariable String userName, @PathVariable String eventoId){
        Evento evento = eventoService.getEvento(userName, eventoId);

        return ResponseEntity.ok(factoryResource.getEventoResource(userName, evento));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoResource> insertEvento(@PathVariable String userName, @RequestBody CreateEventoDto eventoDto){
        Evento evento = eventoService.insertEvento(userName, eventoDto);

        return ResponseEntity.ok(factoryResource.getEventoResource(userName,evento));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoResource> updateEvento(@PathVariable String userName, @RequestBody ModifyEventoDto eventoDto){
        Evento evento = eventoService.updateEventoDto(userName, eventoDto.getId(), eventoDto);

        return ResponseEntity.ok(factoryResource.getEventoResource(userName,evento));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(value = "/{eventoId}/publish", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoResource> publishEvento(@PathVariable String userName, @PathVariable String eventoId){
        Evento evento = eventoService.publishEvento(userName, eventoId);

        return ResponseEntity.ok(factoryResource.getEventoResource(userName, evento));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(value = "/{eventoId}/unpublish", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoResource> unPublishEvento(@PathVariable String userName, @PathVariable String eventoId){
        Evento evento = eventoService.unPublishEvento(userName, eventoId);

        return ResponseEntity.ok(factoryResource.getEventoResource(userName, evento));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(value = "/{eventoId}/favorite", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerResource> favoriteEvento(@PathVariable String userName, @PathVariable String eventoId){
        Player player = eventoService.favoriteEvento(userName, eventoId);

        return ResponseEntity.ok(factoryResource.getPlayerResource(player));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(value = "/{eventoId}/nonfavorite", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerResource> nonfavoriteEvento(@PathVariable String userName, @PathVariable String eventoId){
        Player player = eventoService.nonfavorite(userName, eventoId);

        return ResponseEntity.ok(factoryResource.getPlayerResource(player));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(value = "/{eventoId}/enroll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoResource> enrollUser(@PathVariable String userName, @PathVariable String eventoId){
        Evento evento = eventoService.enroll(userName, eventoId);

        return ResponseEntity.ok(factoryResource.getEventoResource(userName, evento));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(value = "/{eventoId}/generate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoResource> buildEvento(@PathVariable String userName, @PathVariable String eventoId){
        Evento evento = eventoService.genEvento(userName, eventoId);
        if(evento==null)
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        else
            return ResponseEntity.ok(factoryResource.getEventoResource(userName, evento));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(value = "/{eventoId}/play", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoResource> playOver(@PathVariable String userName, @PathVariable String eventoId,
                                                   @RequestBody ModifyPlayDto modifyPlayDto){
        Evento evento = eventoService.playOver(userName, eventoId, modifyPlayDto);

        if(evento==null)
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .body(factoryResource.getEventoResource(userName,evento));
        else
            return ResponseEntity.ok(factoryResource.getEventoResource(userName, evento));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(value = "/{eventoId}/{playId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoResource> score2Players(@PathVariable String userName,
                                                        @PathVariable String eventoId,
                                                        @PathVariable String playId,
                                                        @RequestBody ScorePlayersDto scorePlayersDto){

        Evento evento = eventoService.score2Players(userName, eventoId, playId, scorePlayersDto);

        if(evento==null)
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .body(factoryResource.getEventoResource(userName,evento));
        else
            return ResponseEntity.ok(factoryResource.getEventoResource(userName, evento));
    }

}
