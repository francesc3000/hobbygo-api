package com.hobbygo.api.hobbygoapi.restapi.resource.assembler;

import com.hobbygo.api.hobbygoapi.model.entity.Group;
import com.hobbygo.api.hobbygoapi.restapi.controller.GroupRestController;
import com.hobbygo.api.hobbygoapi.restapi.resource.GroupResource;
import com.hobbygo.api.hobbygoapi.security.GroupSecurityService;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class GroupResourceAssembler extends ResourceAssemblerSupport<Group,GroupResource> {

    private String userName;
    private GroupSecurityService groupSecurityService;

    public GroupResourceAssembler(String userName, GroupSecurityService groupSecurityService){
        super(GroupRestController.class,GroupResource.class);

        setUserName(userName);
        setGroupSecurityService(groupSecurityService);
    }

    @Override
    public GroupResource toResource(Group group) {
        GroupResource groupResource = createResourceWithId(group.getId(),group,getUserName());

        if(getGroupSecurityService().canModifyGroup(getUserName(),group.getId())) {
            groupResource.add(linkTo(methodOn(
                    GroupRestController.class
                    ).updateGroup(userName, null)
            ).withRel("group"));

            groupResource.add(linkTo(methodOn(
                    GroupRestController.class
                    ).insertMember2Group(userName, group.getId(), "")
            ).withRel("insertMember"));
        }

        return groupResource;
    }

    @Override
    protected GroupResource instantiateResource(Group group) {
        return new GroupResource(group);
    }

    private String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    private GroupSecurityService getGroupSecurityService() {
        return groupSecurityService;
    }

    private void setGroupSecurityService(GroupSecurityService groupSecurityService) {
        this.groupSecurityService = groupSecurityService;
    }
}
