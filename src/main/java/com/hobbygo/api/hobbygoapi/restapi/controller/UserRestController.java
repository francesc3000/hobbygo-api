package com.hobbygo.api.hobbygoapi.restapi.controller;

import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.model.registration.PasswordResetToken;
import com.hobbygo.api.hobbygoapi.model.registration.VerificationToken;
import com.hobbygo.api.hobbygoapi.restapi.advice.ValidatingUserRepositoryDecorator;
import com.hobbygo.api.hobbygoapi.restapi.dto.ContactDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.CreateUserDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyUserDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.PasswordDto;
import com.hobbygo.api.hobbygoapi.restapi.exception.UserNotFoundException;
import com.hobbygo.api.hobbygoapi.restapi.resource.*;
import com.hobbygo.api.hobbygoapi.service.PlayerService;
import com.hobbygo.api.hobbygoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    public static final String OWNER = "authentication.name == #userName";
    public static final String ADMIN = "hasRole('ADMIN')";

    @Autowired
    private UserService userService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ValidatingUserRepositoryDecorator validatingUserRepositoryDecorator;

    @Autowired
    private FactoryResource factoryResource;

    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> getUser(@PathVariable String userName) {
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);

        return ResponseEntity.ok(factoryResource.getUserResource(user));
    }

    //@PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> getUserRoot() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken))
            return getUser(authentication.getName());

        throw new UserNotFoundException(authentication.getName());
    }

    /*
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResourceCollection<UserResource>> getUsers() {

        return ResponseEntity.ok(
                new ResourceCollection<>(
                        userService.findAll().stream()
                                .map(user -> new UserResource(user))
                                .collect(Collectors.toList())
                )
        );

    }
*/
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> createUser(@Validated @RequestBody CreateUserDto userDto, HttpServletRequest request) {

        User savedUser = userService.registerNewUserAccount(userDto, request);

        return ResponseEntity.created(
                linkTo(methodOn(UserRestController.class).getUser(savedUser.getUserName()))
                        .toUri()
        ).body(
                factoryResource.getUserResource(savedUser)
        );
    }

    @RequestMapping(path = "/registrationConfirm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VerificationTokenResource> confirmRegistration(HttpServletRequest request, @RequestParam("token") String token) {

        final String result = userService.validateVerificationToken(token);

        switch (result){
            case UserService.TOKEN_VALID:
                VerificationToken verificationToken = userService.findByVerificationToken(token);
                userService.newUserValidatedPhase2(verificationToken.getUser());
                return ResponseEntity.ok(factoryResource.getVerificationTokenResource(verificationToken,result));

                default:
                    return ResponseEntity.unprocessableEntity()
                            .body(factoryResource.getVerificationTokenResource(new VerificationToken(token),result));
        }
    }

    @RequestMapping(path = "/resetPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {

        userService.resetPassword(request, userEmail);

        return ResponseEntity.ok(null);
    }

    @RequestMapping(path = "/changePassword", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PasswordResetTokenResource> changePassword(HttpServletRequest request,
                                                                     @RequestParam("id") String id, @RequestParam("token") String token) {

        String result = userService.validatePasswordResetToken(id, token);

        switch (result){
            case UserService.TOKEN_VALID:
                PasswordResetToken passwordResetToken = userService.findByPasswordResetToken(token);
                return ResponseEntity.ok(factoryResource.getChangePasswordTokenResource(passwordResetToken, result));

            default:
                return ResponseEntity.unprocessableEntity()
                        .body(factoryResource.getChangePasswordTokenResource(new PasswordResetToken(token),result));
        }
    }

    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> savePassword(HttpServletRequest request, @Valid PasswordDto passwordDto) {

        User user =
                (User) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        userService.changeUserPassword(user, passwordDto.getNewPassword());

        return ResponseEntity.ok(factoryResource.getUserResource(user));
    }

    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/{userName}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> modifyUser(@PathVariable String userName,
                                                   @Validated @RequestBody ModifyUserDto userDto) {

        User savedUser = userService.modifyUserAccount(userDto, userName);

        return ResponseEntity.ok(
                factoryResource.getUserResource(savedUser)
        );
    }

    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/{userName}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUser(@PathVariable String userName) {

        Optional<User> userOptional = userService.findByUserName(userName);

        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        userService.delete(userOptional.get());

        return ResponseEntity.ok().build();
    }

    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(value = "/{userName}/contacts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerResource> setContacts(@PathVariable String userName,
                                            @RequestBody List<ContactDto> contactsDto){
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Player playerRet = playerService.setContacts(user, contactsDto);

        return ResponseEntity.ok(factoryResource.getPlayerResource(playerRet));
    }

    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(value = "/{userName}/friend/{friendUserName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerResource> request4Relationship(@PathVariable String userName, @PathVariable String friendUserName){
        User user = validatingUserRepositoryDecorator.findAccountValidated(userName);
        Player playerRet = playerService.request4Relationship(user, friendUserName);

        return ResponseEntity.ok(factoryResource.getPlayerResource(playerRet));
    }
}
