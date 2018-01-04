package com.hobbygo.api.hobbygoapi.model.entity;

import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "eventos")
public class Evento {
    @Id
    private String id;
    private String name;
    private String avatar;
    private Boolean published;
    private LocalDateTime date;
    private LocalDateTime deadline;
    private Address address;
    private int maxPlayers;
    private Hobby hobby;

    @DBRef
    private List<Group> groupList;

    private Member owner;
    private List<Member> memberList;

    //@DBRef
    //private List<Player> playersImFavorite;

    private Map<Integer,Play> flowChart;

    private Evento(){}

    public Evento(String id, Member owner, String name, String avatar,
                  Boolean published, LocalDateTime date, LocalDateTime deadline,
                  Address address, int maxPlayers, Hobby hobby) {
        setId(id);
        setName(name);
        setAvatar(avatar);
        owner.setOwnerRol();
        setOwner(owner);
        setMemberList(new ArrayList<>());
        //setPlayersImFavorite(new ArrayList<>());
        setPublished(published);
        setDate(date);
        setAddress(address);
        setDeadline(deadline);
        setMaxPlayers(maxPlayers);
        setHobby(hobby);
        setFlowChart(new HashMap<>());
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
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

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public List<Member> getMembers(){
        List<Member> members = new ArrayList<>();

        members.addAll(getMemberList());

        for(Group group:getGroupList())
            members.addAll(group.getMembers());

        //Insertamos el owner si no es una empresa
        if(!getOwner().isCorporation()) {
            //Comprobamos que el dueño no se encuntra ya en la lista de miembros proveniente de un grupo
            if(!members.contains(getOwner()))
                members.add(getOwner());
        }

        return members;
    }

    private List<Member> getMemberList() {
        return memberList;
    }

    public List<Member> getMembersWithNoGroup() {
        List<Member> members = new ArrayList<>();

        members.addAll(getMemberList());

        if(!getOwner().isCorporation())
            if(!getOwner().hasGroup())
                members.add(getOwner());

        return members;
    }

    private void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public void publish(){
        this.published = true;
    }

    public void unPublish(){
        setPublished(false);
    }

    public Boolean isPublished(){
        return getPublished();
    }

    private Boolean getPublished() {
        return published;
    }

    private void setPublished(Boolean published) {
        this.published = published;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Boolean addMember(Player player) {
        for(Group group:getGroupList())
            for(Member member:group.getMembers())
                if(member.getPlayer().equals(player))
                    return false;

        return getMemberList().add(new Member(player, null));
    }

    private List<Group> getGroupList() {
        return groupList == null ? Collections.EMPTY_LIST: groupList;
    }

    private void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public Boolean addGroup(Group group){
        //Se comprueba que el numero de jugadores concuerda con el deporte
        if (getHobby().getGroupPlayerNumber() != group.getMembers().size()){
            if(!getHobby().isSubsEnabled())
                return false;
        }
        for(Member member:getMemberList())
            if (group.getMembers().contains(member))
                return false;

        return getGroupList().add(group);
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public void addPlay2FlowChart(Play play){
        Boolean exit=false;

        while (!exit) {
            play.setId(UUID.randomUUID().toString());
            if (getFlowChart().put(Integer.valueOf(play.getId()), play) == null)
                exit = true;
        }
    }

    private Map<Integer,Play> getFlowChart() {
        return flowChart;
    }

    private void setFlowChart(Map<Integer,Play> flowChart) {
        this.flowChart = flowChart;
    }

    public Group getOwnerGroup() {
        return getOwner().getGroup();
    }

    public boolean isFlowed() {
        return !getFlowChart().isEmpty();
    }

    public boolean isFlowChartEmpty() {
        return getFlowChart().isEmpty();
    }

    public int getFlowChartSize() {
        return getFlowChart().size();
    }

    public Play getPlayFromFlowChartById(String i) {
        return getFlowChart().get(i);
    }

    public Boolean addPlayerRating(String playId, Player player, Double score) {
        Play play = getPlayFromFlowChartById(playId);
        if(play!=null)
            if(play.isPlayerScoreable(player))
                return player.addRating(score);

        return false;
    }

    public Boolean playOver(String playId, int winnerScore, int loserScore, LocalDateTime date) {
        Play play = getPlayFromFlowChartById(playId);
        if(play!=null) {
            play.setWinnerScore(winnerScore);
            play.setLoserScore(loserScore);
            play.setDate(date);
        }

        return false;
    }
/*
    public void addAsFavoritePlayer(Player player) {
        if(!getPlayersImFavorite().contains(player))
            getPlayersImFavorite().add(player);
    }

    public void removeAsFavoritePlayer(Player player) {
        getPlayersImFavorite().delete(player);
    }

    public List<Player> getPlayersImFavorite() {
        return playersImFavorite;
    }

    private void setPlayersImFavorite(List<Player> playersImFavorite) {
        this.playersImFavorite = playersImFavorite;
    }

    public boolean isUserNameMyFan(String userName) {
        for(Player player:getPlayersImFavorite())
            if(player.getUserName().equals(userName))
                return true;

        return false;
    }
    */
}
