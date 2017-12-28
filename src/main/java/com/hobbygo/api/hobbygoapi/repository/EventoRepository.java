package com.hobbygo.api.hobbygoapi.repository;

import com.hobbygo.api.hobbygoapi.model.entity.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventoRepository extends MongoRepository<Evento,String> {
    List<Evento> findAll();

    Evento save(Evento evento);
}
