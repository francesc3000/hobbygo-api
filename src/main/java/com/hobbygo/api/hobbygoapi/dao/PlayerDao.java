package com.hobbygo.api.hobbygoapi.dao;

import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.repository.PlayerRepository;
import com.hobbygo.api.hobbygoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("mongoData")
public class PlayerDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Player insert(Player player) {
        return playerRepository.save(player);
    }

    public Player findByUserId(String userId) {
        return playerRepository.findByUserId(userId);
    }

    public Player findById(String id) {
        return playerRepository.findOne(id);
    }

    public Player findByEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    public Player findByUserName(String userName) {
        return playerRepository.findByUserName(userName);
    }

    public void delete(Player player) {
        playerRepository.delete(player);
    }
}
