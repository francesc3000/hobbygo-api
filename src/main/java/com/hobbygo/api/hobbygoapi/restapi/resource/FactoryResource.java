package com.hobbygo.api.hobbygoapi.restapi.resource;

import com.hobbygo.api.hobbygoapi.dao.PlayerDao;
import com.hobbygo.api.hobbygoapi.model.entity.Evento;
import com.hobbygo.api.hobbygoapi.model.entity.Group;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.model.registration.VerificationToken;
import com.hobbygo.api.hobbygoapi.restapi.resource.assembler.*;
import com.hobbygo.api.hobbygoapi.security.EventoSecurityService;
import com.hobbygo.api.hobbygoapi.security.GroupSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FactoryResource {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private EventoSecurityService eventoSecurityService;

    @Autowired
    private GroupSecurityService groupSecurityService;

    private FactoryResource(){

    }

    public UserResource getUserResource(User user) {
        UserResourceAssembler assembler = new UserResourceAssembler();

        return assembler.toResource(user);
    }

    public EventoResource getEventoResource(String userName, Evento evento){
        Player player = playerDao.findByUserName(userName);
        EventoResourceAssembler assembler = new EventoResourceAssembler(userName,player, eventoSecurityService);

        return assembler.toResource(evento);
    }

    public PlayerResource getPlayerResource(Player player) {
        PlayerResourceAssembler assembler = new PlayerResourceAssembler(this);

        return assembler.toResource(player);
    }


    public GroupResource getGroupResource(String userName, Group group){
        GroupResourceAssembler assembler = new GroupResourceAssembler(userName,groupSecurityService);

        return assembler.toResource(group);
    }

    public VerificationTokenResource getTokenResource(VerificationToken verificationTokenToken, String tokenStatus) {
        VerificationTokenResourceAssembler assembler = new VerificationTokenResourceAssembler(verificationTokenToken, tokenStatus);

        return assembler.toResource(verificationTokenToken);
    }
}
