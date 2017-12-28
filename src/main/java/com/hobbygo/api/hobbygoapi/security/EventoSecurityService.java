package com.hobbygo.api.hobbygoapi.security;

import com.hobbygo.api.hobbygoapi.dao.PlayerDao;
import com.hobbygo.api.hobbygoapi.model.entity.Evento;
import com.hobbygo.api.hobbygoapi.model.entity.Member;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.restapi.advice.ValidatingUserRepositoryDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventoSecurityService {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private ValidatingUserRepositoryDecorator validatingUserRepositoryDecorator;

    public boolean canModifyEvento(String userName, String eventoId){
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Player player = playerDao.findById(user.getPlayerId());

        if(player!=null) {
            for (Evento evento : player.getEventoList())
                if (evento.getId().equals(eventoId)) {
                    for (Member member : evento.getMembers()) {
                        if (member.getPlayer().getUserId().equals(user.getId()))
                            if (member.isOwner())
                                return true;
                    }
                }
        }
        return false;
    }
}
