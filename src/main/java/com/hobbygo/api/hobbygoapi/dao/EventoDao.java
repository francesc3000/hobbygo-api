package com.hobbygo.api.hobbygoapi.dao;

import com.hobbygo.api.hobbygoapi.model.entity.Evento;
import com.hobbygo.api.hobbygoapi.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventoDao {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Evento> getAllEventos() {
        return eventoRepository.findAll();
    }

    public Evento save(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Evento update(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Evento findById(String eventoId) {
        return eventoRepository.findOne(eventoId);
    }

    public List<Evento> find(Query query) {
        return mongoTemplate.find(query,Evento.class);
    }
}
