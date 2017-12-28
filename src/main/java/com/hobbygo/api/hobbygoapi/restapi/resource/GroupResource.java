package com.hobbygo.api.hobbygoapi.restapi.resource;

import com.hobbygo.api.hobbygoapi.model.entity.Group;
import com.hobbygo.api.hobbygoapi.model.entity.Member;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class GroupResource extends ResourceSupport {

    private String groupId;
    private String name;
    private String avatar;
    private String description;
    private String hobby;

    private List<MemberResource> memberList;

    public GroupResource(Group group){
        setGroupId(group.getId());
        setName(group.getName());
        setAvatar(group.getAvatar());
        setDescription(group.getDescription());
        setHobby(group.getHobby().hobby());
        this.memberList = new ArrayList<>();
        setMemberList(group.getMembers());
        getMemberList().add(new MemberResource(new Member(group.getOwner(),group)));

    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public List<MemberResource> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        for(Member member:memberList)
            this.memberList.add(new MemberResource(member));
    }
}
