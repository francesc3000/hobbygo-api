package com.hobbygo.api.hobbygoapi.service;

import com.hobbygo.api.hobbygoapi.dao.EventoDao;
import com.hobbygo.api.hobbygoapi.dao.GroupDao;
import com.hobbygo.api.hobbygoapi.dao.PlayerDao;
import com.hobbygo.api.hobbygoapi.model.EventoBuilder;
import com.hobbygo.api.hobbygoapi.model.entity.Evento;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.restapi.advice.ValidatingUserRepositoryDecorator;
import com.hobbygo.api.hobbygoapi.restapi.dto.CreateEventoDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyEventoDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyPlayDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.RatingPlayersDto;
import com.hobbygo.api.hobbygoapi.service.mapping.EventoMapping;
import com.hobbygo.api.hobbygoapi.model.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EventoService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private EventoDao eventoDao;

    @Autowired
    private EventoBuilder eventoBuilder;

    @Autowired
    private ValidatingUserRepositoryDecorator validatingUserRepositoryDecorator;

    public List<Evento> getAllEventos(String userName) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Player player = playerDao.findById(user.getPlayerId());

        return player.getEventoList();
    }

    public Evento insertEvento(String userName, CreateEventoDto eventoDto) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Player player = playerDao.findById(user.getPlayerId());
        Group group = groupDao.findById(eventoDto.getOwnerGroupId());

        //Evento insert
        Evento eventoMapped = EventoMapping.mapEventoDto2Evento(player, group, eventoDto);
        eventoMapped.publish();
        Evento evento = eventoDao.save(eventoMapped);

        //Player Update
        player.setEvento(evento);
        playerDao.save(player);

        return evento;
    }

    @PreAuthorize("@eventoSecurityService.canModifyEvento(#userName, #eventoId)")
    public Evento updateEventoDto(String userName, String eventoId, ModifyEventoDto eventoDto) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Evento evento = eventoDao.findById(eventoId);
        Player player = playerDao.findById(user.getPlayerId());

        return eventoDao.update(EventoMapping.mapEventoDto2Evento(player, evento.getOwnerGroup(), eventoDto));
    }

    @PreAuthorize("@eventoSecurityService.canModifyEvento(#userName, #evento.getId())")
    public Evento updateEvento(String userName, Evento evento) {
        //Player player = playerDao.findByUserName(userName,evento.getHobby());

        return eventoDao.update(evento);
    }

    @PreAuthorize("@eventoSecurityService.canModifyEvento(#userName, #eventoId)")
    public Evento publishEvento(String userName, String eventoId) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);

        Evento evento = eventoDao.findById(eventoId);

        evento.publish();

        return eventoDao.update(evento);
    }

    @PreAuthorize("@eventoSecurityService.canModifyEvento(#userName, #eventoId)")
    public Evento unPublishEvento(String userName, String eventoId) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);

        Evento evento = eventoDao.findById(eventoId);

        evento.unPublish();

        return eventoDao.update(evento);
    }

    public List<Evento> getEventosByDistance(float longitude, float latitude, int distance) {

        Point basePoint = new Point(longitude, latitude);
        Distance radius = new Distance(distance, Metrics.KILOMETERS);

        Circle area = new Circle(basePoint, radius);
        Query query = new Query();
        query.addCriteria(Criteria.where("address.location").withinSphere(area))
                .addCriteria(Criteria.where("published").is(true));

        List<Evento> eventoList = eventoDao.find(query);

        return eventoList;

    }

    public Integer getCountEventosByDistance(float longitude, float latitude, int distance) {
        return getEventosByDistance(longitude,latitude,distance).size();
    }

    @PreAuthorize("@eventoSecurityService.canModifyEvento(#userName, #eventoId)")
    public Evento getEvento(String userName, String eventoId) {
        return eventoDao.findById(eventoId);
    }

    //@PreAuthorize("@eventoSecurityService.canModifyEvento(#userName, #eventoId)")
    public Evento enroll(String userName, String eventoId) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Evento evento = eventoDao.findById(eventoId);
        Player player = playerDao.findById(user.getPlayerId());

        //Se incluye el player en el evento
        evento.addMember(player);
        Evento eventoRet = eventoDao.save(evento);

        //Se incluye el evento en el player
        player.setEvento(evento);
        playerDao.save(player);

        return eventoRet;
    }

    @PreAuthorize("@eventoSecurityService.canModifyEvento(#userName, #eventoId)")
    public Evento genEvento(String userName, String eventoId) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Evento evento = eventoDao.findById(eventoId);

        if(evento.isFlowed())
            evento = null;
        else {
            eventoBuilder.init(user,evento);
            eventoBuilder.buildEvento();

            eventoDao.save(evento);
        }

        return evento;
    }

    @PreAuthorize("@eventoSecurityService.canModifyEvento(#userName, #eventoId)")
    public Evento playOver(String userName, String eventoId, ModifyPlayDto modifyPlayDto) {
        Evento evento = eventoDao.findById(eventoId);

        if(evento.playOver(modifyPlayDto.getId(),
                        modifyPlayDto.getWinnerScore(),
                        modifyPlayDto.getLoserScore(),
                        modifyPlayDto.getLocalDateTime()))
            return eventoDao.save(evento);

        return null;
    }

    public Player favoriteEvento(String userName, String eventoId) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Evento evento = eventoDao.findById(eventoId);
        Player player = playerDao.findById(user.getPlayerId());

        player.addFavoriteEvento(evento);
        //evento.addAsFavoritePlayer(player);

        return playerDao.save(player);
        //return eventoDao.save(evento);
    }

    public Player nonfavorite(String userName, String eventoId) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Evento evento = eventoDao.findById(eventoId);
        Player player = playerDao.findById(user.getPlayerId());

        player.removeFavoriteEvento(evento);
        //evento.removeAsFavoritePlayer(player);

        return playerDao.save(player);
        //return eventoDao.save(evento);
    }

    @PreAuthorize("@eventoSecurityService.canModifyEvento(#userName, #eventoId)")
    public Evento ratingPlayers(String userName, String eventoId, String playId, RatingPlayersDto ratingPlayersDto) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Evento evento = eventoDao.findById(eventoId);
        //Player player = playerDao.findById(user.getPlayerId());

        for(Map.Entry<String,Double> ratingPlayers: ratingPlayersDto.getRatingPlayers().entrySet()) {
            Player player2rate = playerDao.findByUserName(ratingPlayers.getKey());
            if(player2rate!=null)
                evento.addPlayerRating(playId, player2rate, ratingPlayers.getValue());
        }

        eventoDao.save(evento);

        return evento;
    }
}
