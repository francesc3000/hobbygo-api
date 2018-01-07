package com.hobbygo.api.hobbygoapi.service;

import com.hobbygo.api.hobbygoapi.configuration.security.oAuth2.configuration.ApplicationConfigurationProperties;
import com.hobbygo.api.hobbygoapi.dao.PlayerDao;
import com.hobbygo.api.hobbygoapi.dao.UserDao;
import com.hobbygo.api.hobbygoapi.dao.VerificationTokenDao;
import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.model.registration.VerificationToken;
import com.hobbygo.api.hobbygoapi.model.registration.event.OnRegistrationCompleteEvent;
import com.hobbygo.api.hobbygoapi.restapi.dto.CreateUserDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyUserDto;
import com.hobbygo.api.hobbygoapi.restapi.exception.EmailAlreadyExistException;
import com.hobbygo.api.hobbygoapi.restapi.exception.SendConfirmationEmailException;
import com.hobbygo.api.hobbygoapi.restapi.exception.UserNameAlreadyExistException;
import com.hobbygo.api.hobbygoapi.restapi.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class UserService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Autowired
    private ApplicationConfigurationProperties configurationProperties;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private VerificationTokenDao tokenDao;

    public Optional<User> findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    public User registerNewUserAccount(CreateUserDto createUserDto, HttpServletRequest request) {
        if (findByUserName(createUserDto.getUserName()).isPresent())
            throw new UserNameAlreadyExistException(createUserDto.getUserName());

        if(!userDao.findByEmail(createUserDto.getEmail()).isEmpty())
            throw new EmailAlreadyExistException(createUserDto.getEmail());

        User user = new User(createUserDto.getEmail(), createUserDto.getFullName(), createUserDto.getUserName(),
                createUserDto.getPassword(),
                configurationProperties.getDefaultUserRoles(),request.getLocale());

        User createdUser = userDao.save(user);

        try {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (createdUser, request.getLocale(), request.getContextPath()));
        } catch (Exception me) {
            userDao.delete(createdUser);
            throw new SendConfirmationEmailException(me.getMessage());
        }

        return createdUser;

    }

    public User modifyUserAccount(ModifyUserDto modifyUserDto, String userName) {
        Optional<User> userOptional = userDao.findByUserName(userName);

        if (!userOptional.isPresent()) {
            throw new UserNotFoundException(userName);
        }

        User user = userOptional.get();
        modifyUser(user, modifyUserDto);

        return userDao.save(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    private void modifyUser(User user, ModifyUserDto userDto) {
        user.setFullName(userDto.getFullName());
        if (!StringUtils.isEmpty(userDto.getPassword()))
            user.setPassword(userDto.getPassword());

        user.setRoles(userDto.getRoles());
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void createVerificationTokenForUser(User user, String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenDao.save(myToken);
    }

    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenDao.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        if ((verificationToken.getExpiryDate().minusMinutes(LocalDateTime.now().getMinute()).getMinute() <= 0)) {
            tokenDao.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        final User user = verificationToken.getUser();
        user.setEnabled(true);
        userDao.save(user);
        return TOKEN_VALID;
    }

    public VerificationToken findByToken(String token) {
        return tokenDao.findByToken(token);
    }

    public Boolean newUserValidatedPhase2(User user) {
        Player createdPlayer = playerDao.save(
                new Player(user.getId(), user.getEmail(),
                        user.getUserName(), Hobby.CHICHO_TERREMOTO));
        user.setPlayerId(createdPlayer.getId());
        user.setEnabled(true);
        userDao.save(user);

        return true;

    }
}
