package com.hobbygo.api.hobbygoapi.service.mapping;

import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import com.hobbygo.api.hobbygoapi.model.entity.Group;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.restapi.dto.CreateGroupDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyGroupDto;

import java.util.ArrayList;
import java.util.List;

public class GroupMapping {

    private static Group createGroupDto2Group(Player owner, CreateGroupDto createGroupDto){
        return new Group(owner, createGroupDto.getName(), createGroupDto.getAvatar(), createGroupDto.getDescription(),
                         Hobby.valueOf(createGroupDto.getHobby()),false);
    }

    public static List<Group> mapCreateGroupDtoList2GroupList(Player player, List<CreateGroupDto> groupsDto){
        List<Group> groups = new ArrayList<>();
        for(CreateGroupDto createGroupDto :groupsDto)
            groups.add(createGroupDto2Group(player, createGroupDto));

        return groups;
    }

    public static Group mapCreateGroupDto2Group(Player owner, CreateGroupDto createGroupDto) {
        return createGroupDto2Group(owner, createGroupDto);
    }

    private static Group modifyGroupDto2Group(Player owner, ModifyGroupDto modifyGroupDto){
        return new Group(modifyGroupDto.getId(),
                owner, modifyGroupDto.getName(), modifyGroupDto.getAvatar(), modifyGroupDto.getDescription(),
                Hobby.valueOf(modifyGroupDto.getHobby()),false);
    }

    public static List<Group> mapModifyGroupDtoList2GroupList(Player player, List<ModifyGroupDto> groupsDto){
        List<Group> groups = new ArrayList<>();
        for(ModifyGroupDto modifyGroupDto :groupsDto)
            groups.add(modifyGroupDto2Group(player, modifyGroupDto));

        return groups;
    }

    public static Group mapModifyGroupDto2Group(Player owner, ModifyGroupDto modifyGroupDto) {
        return modifyGroupDto2Group(owner, modifyGroupDto);
    }
}
