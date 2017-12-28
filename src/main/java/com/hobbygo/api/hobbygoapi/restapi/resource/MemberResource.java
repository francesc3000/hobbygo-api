package com.hobbygo.api.hobbygoapi.restapi.resource;

import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import com.hobbygo.api.hobbygoapi.model.entity.Member;
import org.springframework.hateoas.ResourceSupport;

public class MemberResource extends ResourceSupport {

    private String rol;
    private String userName;
    private String userAvatar;
    private String groupId;
    private String groupName;
    private String groupAvatar;
    private String groupDescription;
    private Hobby hobby;

    public MemberResource(Member member){
        setRol(member.getRol());
        setUserName(member.getPlayer().getUserName());
        setUserAvatar(member.getPlayer().getAvatar());
        if(member.getGroup()!=null) {
            setGroupId(member.getGroup().getId());
            setGroupName(member.getGroup().getName());
            setGroupAvatar(member.getGroup().getAvatar());
            setGroupDescription(member.getGroup().getDescription());
            setHobby(member.getGroup().getHobby());
        }
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupAvatar() {
        return groupAvatar;
    }

    public void setGroupAvatar(String groupAvatar) {
        this.groupAvatar = groupAvatar;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }
}
