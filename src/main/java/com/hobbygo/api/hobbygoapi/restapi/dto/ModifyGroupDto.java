package com.hobbygo.api.hobbygoapi.restapi.dto;

import java.util.List;

public class ModifyGroupDto extends CreateGroupDto{

    private String id;

    public ModifyGroupDto(String id, String name, String avatar, String description, String hobby,
                          String ownerId, List<String> memberIdList){
        super(name, avatar, description, hobby, ownerId, memberIdList);

        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
