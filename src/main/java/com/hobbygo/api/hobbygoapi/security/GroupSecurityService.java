package com.hobbygo.api.hobbygoapi.security;

import com.hobbygo.api.hobbygoapi.dao.GroupDao;
import com.hobbygo.api.hobbygoapi.dao.PlayerDao;
import com.hobbygo.api.hobbygoapi.model.entity.Group;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.restapi.advice.ValidatingUserRepositoryDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroupSecurityService {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private ValidatingUserRepositoryDecorator validatingUserRepositoryDecorator;

    public boolean canModifyGroup(String userName, String groupId){
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Group groupbd = groupDao.findById(groupId);
        if(groupbd!=null) {
            Player player = playerDao.findById(user.getPlayerId());

            for (Group group : player.getGroupList())
                if (group.getId().equals(groupId))
                    return true;
        }

        return false;
    }
}
