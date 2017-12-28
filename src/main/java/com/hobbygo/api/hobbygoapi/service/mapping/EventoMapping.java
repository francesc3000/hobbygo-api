package com.hobbygo.api.hobbygoapi.service.mapping;

import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import com.hobbygo.api.hobbygoapi.model.entity.Evento;
import com.hobbygo.api.hobbygoapi.model.entity.Group;
import com.hobbygo.api.hobbygoapi.model.entity.Member;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.restapi.dto.CreateEventoDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyEventoDto;

import java.time.LocalDateTime;

public class EventoMapping {

    public static Evento mapEventoDto2Evento(Player player, Group group, CreateEventoDto eventoDto) {
        return new Evento(null, new Member(player,group),
                eventoDto.getName(), eventoDto.getAvatar(),
                eventoDto.getPublished(),
                LocalDateTime.of(eventoDto.getYear(), eventoDto.getMonth(), eventoDto.getDay(),
                                 eventoDto.getHour(), eventoDto.getMinute()),
                LocalDateTime.of(eventoDto.getDyear(), eventoDto.getDmonth(), eventoDto.getDday(),
                                0,0),
                eventoDto.getAddress(),
                eventoDto.getMaxPlayers(),
                Hobby.valueOf(eventoDto.getHobby())
        );
    }

    public static Evento mapEventoDto2Evento(Player player, Group group, ModifyEventoDto eventoDto) {
        return new Evento(eventoDto.getId(), new Member(player, group),
                eventoDto.getName(), eventoDto.getAvatar(),
                eventoDto.getPublished(),
                LocalDateTime.of(eventoDto.getYear(), eventoDto.getMonth(), eventoDto.getDay(),
                        eventoDto.getHour(), eventoDto.getMinute()),
                LocalDateTime.of(eventoDto.getDyear(), eventoDto.getDmonth(), eventoDto.getDday(),
                        0,0),
                eventoDto.getAddress(),
                eventoDto.getMaxPlayers(),
                Hobby.valueOf(eventoDto.getHobby())
        );
    }
}
