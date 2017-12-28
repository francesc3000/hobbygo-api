package com.hobbygo.api.hobbygoapi.model.entity;

public class Candidate {

    private String playerId;
    private String email;

    public Candidate(String playerId, String email){
        setPlayerId(playerId);
        setEmail(email);
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
