package com.hobbygo.api.hobbygoapi.restapi.resource;

import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import com.hobbygo.api.hobbygoapi.model.entity.*;
import com.hobbygo.api.hobbygoapi.model.entity.Group;
import com.hobbygo.api.hobbygoapi.model.entity.Play;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlayResource extends ResourceSupport {

    private List<String> groupList;
    private Group winner;
    private int winnerScore;
    private int loserScore;
    private LocalDateTime date;
    private Hobby hobby;

    public PlayResource(Play play){
        setGroupList(new ArrayList<>());
        for(Group group:play.getGroupList())
            addGroup(group.getName());

        setWinner(play.getWinner());
        setWinnerScore(play.getWinnerScore());
        setLoserScore(play.getLoserScore());
        setDate(play.getDate());
        setHobby(play.getHobby());

        addPlayerLinks();
    }

    private void addPlayerLinks() {

    }

    public List<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<String> groupList) {
        this.groupList = groupList;
    }

    public Group getWinner() {
        return winner;
    }

    public void setWinner(Group winner) {
        this.winner = winner;
    }

    public int getWinnerScore() {
        return winnerScore;
    }

    public void setWinnerScore(int winnerScore) {
        this.winnerScore = winnerScore;
    }

    public int getLoserScore() {
        return loserScore;
    }

    public void setLoserScore(int loserScore) {
        this.loserScore = loserScore;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public Boolean addGroup(String name){
        return getGroupList().add(name);
    }
}
