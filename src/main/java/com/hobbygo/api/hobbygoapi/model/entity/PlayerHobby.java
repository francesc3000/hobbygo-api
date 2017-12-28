package com.hobbygo.api.hobbygoapi.model.entity;

import com.hobbygo.api.hobbygoapi.model.constants.Hobby;

public class PlayerHobby {

    private Hobby hobby;

    private PlayerHobby(){}

    public PlayerHobby(Hobby hobby) {

    }

    public Hobby getHobby(){
        return this.hobby;
    }

    public void setHobby(Hobby hobby){
        this.hobby = hobby;
    }

}
