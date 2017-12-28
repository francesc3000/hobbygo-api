package com.hobbygo.api.hobbygoapi.service;

import com.hobbygo.api.hobbygoapi.configuration.security.oAuth2v1.configuration.ApplicationConfigurationProperties;
import com.hobbygo.api.hobbygoapi.dao.PlayerDao;
import com.hobbygo.api.hobbygoapi.dao.UserDao;
import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.restapi.dto.CreateUserDto;
import com.hobbygo.api.hobbygoapi.restapi.dto.ModifyUserDto;
import com.hobbygo.api.hobbygoapi.service.exception.UpdatedUserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private ApplicationConfigurationProperties configurationProperties;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserDao userDao;

    @Autowired
    private PlayerDao playerDao;

    public Optional<User> findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    public User save(CreateUserDto createUserDto) {
        User user = new User();
        updateUser(user, createUserDto);

        User userBBDD = userDao.save(user);
        if(userBBDD!=null) {
            Player player = playerDao.save(
                    new Player(userBBDD.getId(), userBBDD.getEmail(),
                            userBBDD.getUserName(), Hobby.CHICHO_TERREMOTO));
            userBBDD.setPlayerId(player.getId());
            userBBDD = userDao.save(userBBDD);
        }

        return userBBDD;

    }

    public User save(ModifyUserDto modifyUserDto, String userName) throws UpdatedUserNotFoundException {
        Optional<User> userOptional = userDao.findByUserName(userName);

        if (!userOptional.isPresent()) {
            throw new UpdatedUserNotFoundException(userName);
        }

        User user = userOptional.get();
        updateUser(user, modifyUserDto);

        return userDao.save(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    private void updateUser(User user, CreateUserDto userDto) {
        user.setFullName(userDto.getFullName());
        user.setUserName(userDto.getUserName());
        user.setPassword(
                encryptPassword(userDto.getPassword())
        );

        user.setRoles(configurationProperties.getDefaultUserRoles());
    }

    private void updateUser(User user, ModifyUserDto userDto) {
        user.setFullName(userDto.getFullName());
        if (!StringUtils.isEmpty(userDto.getPassword())) {
            user.setPassword(
                    encryptPassword(userDto.getPassword())
            );
        }
        user.setRoles(userDto.getRoles());
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }
}
