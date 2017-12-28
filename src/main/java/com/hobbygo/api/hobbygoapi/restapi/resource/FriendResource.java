package com.hobbygo.api.hobbygoapi.restapi.resource;

import com.hobbygo.api.hobbygoapi.model.constants.FriendStatus;
import com.hobbygo.api.hobbygoapi.model.entity.Friend;
import org.springframework.hateoas.ResourceSupport;

public class FriendResource extends ResourceSupport {

    private String friendId;
    private String avatar;
    private FriendStatus status;

    public FriendResource(Friend friend){
         setFriendId(friend.getPlayer().getId());
         setAvatar(friend.getPlayer().getAvatar());
         setStatus(friend.getStatus());
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }
}
