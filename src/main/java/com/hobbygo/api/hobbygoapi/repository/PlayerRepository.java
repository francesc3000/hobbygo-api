package com.hobbygo.api.hobbygoapi.repository;

import com.hobbygo.api.hobbygoapi.model.entity.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerRepository extends MongoRepository<Player,String> {
    List<Player> findAll();

    Player save(Player player);

    //Player findById(String playerId);

    Player findByUserId(String userId);

    Player findByEmail(String email);

    Player findByUserName(String userName);
}
