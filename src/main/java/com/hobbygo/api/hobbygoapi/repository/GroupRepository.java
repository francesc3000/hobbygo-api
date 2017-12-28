package com.hobbygo.api.hobbygoapi.repository;

import com.hobbygo.api.hobbygoapi.model.entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group,String> {
    List<Group> findAll();

    Group save(Group group);

    Group findById(String groupId);
}
