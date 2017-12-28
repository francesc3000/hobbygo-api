package com.hobbygo.api.hobbygoapi.dao;

import com.hobbygo.api.hobbygoapi.model.entity.Group;
import com.hobbygo.api.hobbygoapi.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupDao {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public Group update(Group group) {
        return groupRepository.save(group);
    }

    public Group findById(String groupId) {
        return groupRepository.findById(groupId);
    }
}
