package com.hobbygo.api.hobbygoapi.restapi.controller;

import com.hobbygo.api.hobbygoapi.restapi.resource.ResourceCollection;
import com.hobbygo.api.hobbygoapi.restapi.resource.RootResource;
import com.hobbygo.api.hobbygoapi.service.EventoService;
import com.hobbygo.api.hobbygoapi.restapi.resource.EventoResource;
import com.hobbygo.api.hobbygoapi.restapi.resource.FactoryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
public class RootRestController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private FactoryResource factoryResource;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RootResource> getRoot() {
        return ResponseEntity.ok(new RootResource());
    }


    @RequestMapping(value = "/distance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceCollection<EventoResource>> getFilterEventos(@RequestHeader(value="lon") float longitude,
                                                                               @RequestHeader(value="lat") float latitude,
                                                                               @RequestHeader(value="dis") int distance) {

        ResourceCollection<EventoResource> resourceCollection = new ResourceCollection<>(
                eventoService.getEventosByDistance(longitude, latitude, distance)
                        .stream()
                        .map(evento -> factoryResource.getEventoResource(getUserName(), evento))
                        .collect(Collectors.toList()));

        resourceCollection.add(linkTo(methodOn(
                RootRestController.class
                ).getFilterEventos(longitude,latitude,distance)
        ).withSelfRel());

        return ResponseEntity.ok( resourceCollection );
    }

    private String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken))
            return authentication.getName();

        return "";
    }

    @RequestMapping(value = "/distanceCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getCountFilterEventos(@RequestHeader(value="lon") float longitude,
                                                         @RequestHeader(value="lat") float latitude,
                                                         @RequestHeader(value="dis") int distance) {
        return ResponseEntity.ok(eventoService.getCountEventosByDistance(longitude,latitude,distance));
    }
}
