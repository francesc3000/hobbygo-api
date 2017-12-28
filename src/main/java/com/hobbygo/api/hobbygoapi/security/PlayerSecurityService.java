package com.hobbygo.api.hobbygoapi.security;

import com.hobbygo.api.hobbygoapi.dao.UserDao;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class PlayerSecurityService {

    @Autowired
    private UserDao userDao;

    public boolean friendUserNameExists(String friendUserName){
        Optional<User> friend = userDao.findByUserName(friendUserName);


        return friend.isPresent();
    }
}
