package com.hobbygo.api.hobbygoapi.service;

import com.hobbygo.api.hobbygoapi.dao.GroupDao;
import com.hobbygo.api.hobbygoapi.dao.PlayerDao;
import com.hobbygo.api.hobbygoapi.dao.UserDao;
import com.hobbygo.api.hobbygoapi.model.entity.Group;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.restapi.advice.ValidatingUserRepositoryDecorator;
import com.hobbygo.api.hobbygoapi.restapi.dto.CreateGroupDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyGroupDto;
import com.hobbygo.api.hobbygoapi.security.GroupSecurityService;
import com.hobbygo.api.hobbygoapi.service.mapping.GroupMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupSecurityService groupSecurityService;

    @Autowired
    private ValidatingUserRepositoryDecorator validatingUserRepositoryDecorator;

    public List<Group> getAllGroups(String userName) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        //Buscamos el jugador en BBDD
        Player player = playerDao.findById(user.getPlayerId());

        return player.getGroupList();
    }

    public Group insertGroup(String userName, CreateGroupDto createGroupDto) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        //Buscamos el jugador en BBDD
        Player player = playerDao.findById(user.getPlayerId());

        return insertGroup(userName, GroupMapping.mapCreateGroupDto2Group(player, createGroupDto));
    }

    public Group insertGroup(String userName, Group group) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        //Buscamos el jugador en BBDD
        Player player = playerDao.findById(user.getPlayerId());
        //Group insert
        Group groupRet = groupDao.save(group);

        //Player Update
        player.addGroup(groupRet);
        playerDao.save(player);

        return groupRet;
    }

    @PreAuthorize("@groupSecurityService.canModifyGroup(#userName, #createGroupDto.getId())")
    public Group updateGroup(String userName, ModifyGroupDto modifyGroupDto) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        //Buscamos el jugador en BBDD
        Player player = playerDao.findById(user.getPlayerId());

        Group group = groupDao.save(GroupMapping.mapModifyGroupDto2Group(player,modifyGroupDto));

        return group;
    }

    @PreAuthorize("@groupSecurityService.canModifyGroup(#userName, #group.getId())")
    public Group updateGroup(String userName, Group group) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        return groupDao.save(group);
    }

    @PreAuthorize("@groupSecurityService.canModifyGroup(#userName, #groupId)")
    public Group insertMember2Group(String userName, String groupId, String memberName) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        User member = userDao.findByUserName(memberName).get();
        //Buscamos el usuario en BBDD
        Group group = groupDao.findById(groupId);

        Player memberPlayer = playerDao.findById(member.getPlayerId());
        group.addMember(memberPlayer);

        return groupDao.save(group);
    }
}
