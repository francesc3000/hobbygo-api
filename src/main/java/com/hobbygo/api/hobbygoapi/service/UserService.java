package com.hobbygo.api.hobbygoapi.service;

import com.hobbygo.api.hobbygoapi.configuration.security.oAuth2.configuration.ApplicationConfigurationProperties;
import com.hobbygo.api.hobbygoapi.dao.PasswordResetTokenDao;
import com.hobbygo.api.hobbygoapi.dao.PlayerDao;
import com.hobbygo.api.hobbygoapi.dao.UserDao;
import com.hobbygo.api.hobbygoapi.dao.VerificationTokenDao;
import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.model.registration.PasswordResetToken;
import com.hobbygo.api.hobbygoapi.model.registration.VerificationToken;
import com.hobbygo.api.hobbygoapi.model.registration.event.OnRegistrationCompleteEvent;
import com.hobbygo.api.hobbygoapi.restapi.dto.CreateUserDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyUserDto;
import com.hobbygo.api.hobbygoapi.restapi.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

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
    private VerificationTokenDao verificationTokenDao;

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messages;

    @Autowired
    private Environment env;

    public Optional<User> findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    public User registerNewUserAccount(CreateUserDto createUserDto, HttpServletRequest request) {
        if (findByUserName(createUserDto.getUserName()).isPresent())
            throw new UserNameAlreadyExistException(createUserDto.getUserName());

        if(userDao.findByEmail(createUserDto.getEmail())!=null)
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
        verificationTokenDao.save(myToken);
    }

    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = verificationTokenDao.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        if ((verificationToken.getExpiryDate().minusMinutes(LocalDateTime.now().getMinute()).getMinute() <= 0)) {
            verificationTokenDao.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        final User user = verificationToken.getUser();
        user.setEnabled(true);
        userDao.save(user);
        return TOKEN_VALID;
    }

    public VerificationToken findByVerificationToken(String token) {
        return verificationTokenDao.findByToken(token);
    }

    public PasswordResetToken findByPasswordResetToken(String token) {
        return passwordResetTokenDao.findByToken(token);
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

    public void resetPassword(HttpServletRequest request, String userEmail) {
        User user = userDao.findByEmail(userEmail);
        if(user==null)
            throw new EmailNoExistException(userEmail);

        final String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
    }

    private void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenDao.save(myToken);
    }

    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public String validatePasswordResetToken(String id, String token) {
        final PasswordResetToken passToken = passwordResetTokenDao.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id))
            return TOKEN_INVALID;


        if ((passToken.getExpiryDate().minusMinutes(LocalDateTime.now().getMinute()).getMinute() <= 0)) {
            passwordResetTokenDao.delete(passToken);
            return TOKEN_EXPIRED;
        }

        final User user = passToken.getUser();
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return TOKEN_VALID;
    }

    public void changeUserPassword(final User user, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        userDao.save(user);
    }
}
