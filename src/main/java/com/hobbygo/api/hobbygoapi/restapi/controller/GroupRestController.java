package com.hobbygo.api.hobbygoapi.restapi.controller;

import com.hobbygo.api.hobbygoapi.model.entity.Group;
import com.hobbygo.api.hobbygoapi.restapi.advice.ValidatingUserRepositoryDecorator;
import com.hobbygo.api.hobbygoapi.restapi.dto.CreateGroupDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyGroupDto;
import com.hobbygo.api.hobbygoapi.restapi.resource.FactoryResource;
import com.hobbygo.api.hobbygoapi.restapi.resource.GroupResource;
import com.hobbygo.api.hobbygoapi.restapi.resource.ResourceCollection;
import com.hobbygo.api.hobbygoapi.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/{userName}/groups")
public class GroupRestController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ValidatingUserRepositoryDecorator validatingUserRepositoryDecorator;

    @Autowired
    private FactoryResource factoryResource;

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceCollection<GroupResource>> getAllGroups(@PathVariable String userName){
        validatingUserRepositoryDecorator.findAccountValidated(userName);
        List<Group> groupList = groupService.getAllGroups(userName);

        return ResponseEntity.ok(
                new ResourceCollection<>(
                        groupService.getAllGroups(userName)
                                .stream()
                                .map(group -> factoryResource.getGroupResource(userName,group))
                                .collect(Collectors.toList())
                )
        );
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupResource> insertGroup(@PathVariable String userName,
                                                     @RequestBody CreateGroupDto createGroupDto){
        validatingUserRepositoryDecorator.findAccountValidated(userName);
        Group group = groupService.insertGroup(userName, createGroupDto);

        return ResponseEntity.ok(
                factoryResource.getGroupResource(userName, group));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupResource> updateGroup(@PathVariable String userName,
                                                     @RequestBody ModifyGroupDto modifyGroupDto){
        validatingUserRepositoryDecorator.findAccountValidated(userName);
        return ResponseEntity.ok(
                factoryResource.getGroupResource(userName, groupService.updateGroup(userName, modifyGroupDto)));
    }

    @PreAuthorize(UserRestController.ADMIN + " or " + UserRestController.OWNER)
    @RequestMapping(value = "/insertMember", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupResource> insertMember2Group(@PathVariable String userName,
                                                            @RequestHeader(value="groupId") String groupId,
                                                            @RequestHeader(value="memberName") String memberName){
        validatingUserRepositoryDecorator.findAccountValidated(userName);
        Group group = groupService.insertMember2Group(userName, groupId, memberName);

        return ResponseEntity.ok(
                factoryResource.getGroupResource(userName,group));
    }
}
