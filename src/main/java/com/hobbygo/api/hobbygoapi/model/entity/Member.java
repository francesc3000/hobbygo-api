package com.hobbygo.api.hobbygoapi.model.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Member {
    public static String OWNER = "owner";
    public static String MEMBER = "member";
    public static String CORPORATION = "corporation";

    private String rol;
    @DBRef
    private Player player;
    @DBRef
    private Group group;

    public Member(Player player, Group group){
        setMemberRol();
        setPlayer(player);
        setGroup(group);
    }

    public String getRol() {
        return rol;
    }

    private void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean isOwner(){
        if(getRol().equals(OWNER))
            return true;

        return false;
    }

    public Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    public void setOwnerRol(){
        setRol(OWNER);
    }

    public void setMemberRol(){
        setRol(MEMBER);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Boolean hasGroup(){
        if(getGroup()!=null)
            return true;

        return false;
    }

    public Boolean isCorporation(){
        if(getRol().equals(CORPORATION))
            return true;

        return false;
    }
/*
    @Override
    public boolean equals (Object obj) {

        if (obj instanceof Member) {
            Member tmpMember = (Member) obj;

            if (this.getPlayer().getId().equals(tmpMember.getPlayer().getId()))
                return true;
        }

        return false;
    }
    */
}
