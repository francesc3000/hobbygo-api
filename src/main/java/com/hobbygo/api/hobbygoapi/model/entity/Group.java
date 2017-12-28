package com.hobbygo.api.hobbygoapi.model.entity;

import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "groups")
public class Group {
    @Id
    private String id;
    private String name;
    private String avatar;
    private String description;
    private Hobby hobby;

    @DBRef
    private Player owner;
    private List<Member> memberList;

    private Boolean autoGen;

    private Group(){}

    public Group(String id, Player owner, String name, String avatar, String description, Hobby hobby,
                 Boolean autoGen){
        setId(id);
        setName(name);
        setAvatar(avatar);
        setDescription(description);
        setHobby(hobby);
        setAutoGen(autoGen);


        memberList = new ArrayList<>();

        setOwner(owner);
    }

    public Group(Player owner, String name, String avatar, String description, Hobby hobby,
                 Boolean autoGen){
        setName(name);
        setAvatar(avatar);
        setDescription(description);
        setHobby(hobby);
        setAutoGen(autoGen);


        memberList = new ArrayList<>();

        setOwner(owner);
    }

    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        if(avatar==null || avatar.equals(""))
            avatar = "http://groupAvatar.es";
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void setOwner(Player owner){
        this.owner = owner;
    }

    public List<Member> getMembers() {
        List<Member> memberList = new ArrayList<>();
        Member memberOwner = new Member(getOwner(),this);
        memberOwner.setOwnerRol();
        memberList.add(memberOwner);
        memberList.addAll(getMemberList());

        return memberList;
    }

    public Player addMember(Player player){
        if(getMemberList().add(new Member(player,this)))
            return player;

        return null;
    }

    public Member addMember(Member member){
        if(getMemberList().add(member))
            return member;

        return null;
    }

    private List<Member> getMemberList() {
        return this.memberList;
    }

    public Player getOwner() {
        return this.owner;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    private Boolean getAutoGen() {
        return autoGen;
    }

    private void setAutoGen(Boolean autoGen) {
        this.autoGen = autoGen;
    }

    public Boolean isAutoGen(){
        return autoGen;
    }

    public boolean isPlayerAMember(Player player) {
        for(Member member:getMembers())
            if(member.getPlayer().equals(player))
                return true;

        return false;
    }
}
