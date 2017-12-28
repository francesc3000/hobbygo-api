package com.hobbygo.api.hobbygoapi.restapi.dto;

import java.util.Map;

public class ScorePlayersDto {
    private Map<String,Double> scorePlayers;

    public Map<String, Double> getScorePlayers() {
        return scorePlayers;
    }

    public void setScorePlayers(Map<String, Double> scorePlayers) {
        this.scorePlayers = scorePlayers;
    }
}
