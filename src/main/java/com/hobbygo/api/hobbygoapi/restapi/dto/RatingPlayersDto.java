package com.hobbygo.api.hobbygoapi.restapi.dto;

import java.util.Map;

public class RatingPlayersDto {
    private Map<String,Double> ratingPlayers;

    public Map<String, Double> getRatingPlayers() {
        return ratingPlayers;
    }

    public void setRatingPlayers(Map<String, Double> ratingPlayers) {
        this.ratingPlayers = ratingPlayers;
    }
}
