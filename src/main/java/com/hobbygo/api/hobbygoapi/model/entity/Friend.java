package com.hobbygo.api.hobbygoapi.model.entity;

import com.hobbygo.api.hobbygoapi.model.constants.FriendStatus;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Friend {

    @DBRef
    private Player player;
    private FriendStatus status;

    public Friend(Player player){
        setPlayer(player);
        setStatus(FriendStatus.CONNECTED);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public FriendStatus getStatus() {
        return status;
    }

    private void setStatus(FriendStatus status) {
        this.status = status;
    }

    public void block(){
        setStatus(FriendStatus.BLOCKED);
    }

    public Boolean markAsMutual() {
        setStatus(FriendStatus.MUTUAL);
        return true;
    }

    public Boolean markAsCandidate(){
        setStatus(FriendStatus.CANDIDATE);
        return true;
    }
}
