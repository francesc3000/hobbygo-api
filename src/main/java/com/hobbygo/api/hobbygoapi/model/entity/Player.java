package com.hobbygo.api.hobbygoapi.model.entity;

import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "players")
public class Player {
    @Id
    private String id;
    private String userId;
    private String email;
    private String userName;
    private String avatar;

    private Map<Hobby,PlayerHobby> playerHobbyList;
    @DBRef
    private List<Group> groupList;
    @DBRef
    private List<Evento> eventoList;
    @DBRef
    private List<Evento> favoriteEventoList;

    @DBRef
    private List<Contact> contactList;

    private List<Friend> friendList;

    private Player(){}

    public Player(String userId, String email, String userName, Hobby hobby){
        initPlayer(null, userId, email, userName,"http://avatar.es", hobby);
    }

    public Player(String userId, String email, String userName, String avatar, Hobby hobby){
        initPlayer(null, userId, email, userName, avatar, hobby);
    }

    public Player(String id, String userId, String email, String userName, String avatar, Hobby hobby){
        initPlayer(id, userId, email, userName, avatar, hobby);
    }

    private void initPlayer(String id, String userId, String email, String userName, String avatar, Hobby hobby){
        setId(id);
        setUserName(userName);
        setEmail(email);
        setUserId(userId);
        setAvatar(avatar);

        playerHobbyList = new HashMap<>();
        groupList = new ArrayList<>();
        eventoList = new ArrayList<>();
        favoriteEventoList = new ArrayList<>();

        contactList = new ArrayList<>();
        friendList = new ArrayList<>();

        addHobby(hobby);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Map<Hobby, PlayerHobby> getPlayerHobbyList() {
        return playerHobbyList;
    }

    public void setPlayerHobbyList(Map<Hobby, PlayerHobby> playerHobbyList) {
        this.playerHobbyList = playerHobbyList;
    }

    public Boolean addHobby(Hobby hobby){
        if(!getPlayerHobbyList().containsKey(hobby))
            if(getPlayerHobbyList().put(hobby,new PlayerHobby(hobby))==null)
                return true;

        return false;
    }

    public Boolean addGroup(Group group) {
        if(!getGroupList().contains(group))
            return getGroupList().add(group);

        return false;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public void setEvento(Evento evento) {
        getEventoList().add(evento);
    }

    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    public void setContactList(List<Contact> contactList) {
        getContactList().addAll(contactList);
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
    }

    public void setHobby(Hobby hobby) {
        getPlayerHobbyList().put(hobby,new PlayerHobby(hobby));
    }

    public Boolean addFriend(Player playerFriend) {
        Boolean isMutual = false;

        for(Contact contactFriend:playerFriend.getContactList())
            if (contactFriend.getEmail().equals(getEmail()))
                isMutual = true;

        for(Friend friend:getFriendList())
            if(friend.getPlayer().equals(playerFriend))
                if(isMutual){
                    return friend.markAsMutual();
                }else
                    return true;

        Friend friend = new Friend(playerFriend);
        if(isMutual)
            friend.markAsMutual();

        return getFriendList().add(friend);
    }

    public Boolean addCandidate(Player playerFriend) {
        Boolean isMutual = false;

        for(Contact contactFriend:playerFriend.getContactList())
            if (contactFriend.getEmail().equals(getEmail()))
                isMutual = true;

        for(Friend friend:getFriendList())
            if(friend.getPlayer().equals(playerFriend))
                if(isMutual){
                    return friend.markAsMutual();
                }else
                    return true;

        Friend friend = new Friend(playerFriend);
        if(isMutual)
            friend.markAsMutual();
        else
            friend.markAsCandidate();

        return getFriendList().add(friend);
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Player))return false;
        Player otherPlayer = (Player)other;
        return otherPlayer.getId().equals(((Player) other).getId());

    }

    public void addFavoriteEvento(Evento evento) {
        if(!getFavoriteEventoList().contains(evento))
            getFavoriteEventoList().add(evento);
    }

    public void removeFavoriteEvento(Evento evento) {
        getFavoriteEventoList().remove(evento);
    }

    public List<Evento> getFavoriteEventoList() {
        return favoriteEventoList;
    }

    private void setFavoriteEventoList(List<Evento> favoriteEventoList) {
        this.favoriteEventoList = favoriteEventoList;
    }

    public Boolean isEventoFavorite(Evento evento) {
        return getFavoriteEventoList().contains(evento);
    }

    public Boolean setScore(Double score) {

        ///this.score = score;
        return true;
    }
}
