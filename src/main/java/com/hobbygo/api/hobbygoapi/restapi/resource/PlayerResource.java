package com.hobbygo.api.hobbygoapi.restapi.resource;

import com.hobbygo.api.hobbygoapi.model.entity.*;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class PlayerResource extends ResourceSupport {

    private String playerId;
    private String avatar;

    private List<GroupResource> groupList;
    private List<EventoResource> eventoList;
    private List<ContactResource> contactList;
    private List<FriendResource> friendList;

    public PlayerResource(Player player, FactoryResource factoryResource){
        setPlayerId(player.getId());
        setAvatar(player.getAvatar());
        setGroupList(player.getGroupList(),player.getUserName(),factoryResource);
        setEventoList(player.getEventoList(),player.getUserName(),factoryResource);
        setContactList(player.getContactList(),factoryResource);
        setFriendList(player.getFriendList(),factoryResource);
    }

    public String getPlayerId() {
        return playerId;
    }

    private void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getAvatar() {
        return avatar;
    }

    private void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<GroupResource> getGroupList() {
        if(groupList==null)
            this.groupList = new ArrayList<>();
        return groupList;
    }

    private void setGroupList(List<Group> groupList, String userName, FactoryResource factoryResource) {
        for(Group group:groupList){
            getGroupList().add(factoryResource.getGroupResource(userName,group));
        }
    }

    public List<EventoResource> getEventoList() {
        if(eventoList==null)
            this.eventoList = new ArrayList<>();
        return eventoList;
    }

    private void setEventoList(List<Evento> eventoList, String userName, FactoryResource factoryResource) {
        for(Evento evento:eventoList)
            getEventoList().add(factoryResource.getEventoResource(userName,evento));
    }

    public List<ContactResource> getContactList() {
        if(contactList==null)
            this.contactList = new ArrayList<>();
        return contactList;
    }

    private void setContactList(List<Contact> contactList, FactoryResource factoryResource) {
        for(Contact contact:contactList)
            getContactList().add(new ContactResource(contact));

    }

    public List<FriendResource> getFriendList() {
        if(friendList==null)
            this.friendList = new ArrayList<>();
        return friendList;
    }

    private void setFriendList(List<Friend> friendList, FactoryResource factoryResource) {
        for(Friend friend:friendList)
            getFriendList().add(new FriendResource(friend));
    }
}
