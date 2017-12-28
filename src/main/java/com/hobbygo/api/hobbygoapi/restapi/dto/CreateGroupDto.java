package com.hobbygo.api.hobbygoapi.restapi.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateGroupDto {

    @NotNull
    @Size(min = 1)
    private String name;
    private String avatar;
    private String description;
    private String hobby;
    private String ownerId;
    private List<String> memberIdList;

    private CreateGroupDto(){}

    public CreateGroupDto(String name, String avatar, String description,String hobby,
                          String ownerId, List<String> memberIdList){

        setName(name);
        if(avatar==null || avatar=="")
            setAvatar("http://www.default.com");
        else
            setAvatar(avatar);
        setDescription(description);
        setHobby(hobby);
        setOwnerId(ownerId);
        setMemberIdList(memberIdList);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOwnerId() {
        return ownerId;
    }

    private void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<String> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
