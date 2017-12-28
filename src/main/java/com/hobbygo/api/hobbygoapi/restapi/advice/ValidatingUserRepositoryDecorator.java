package com.hobbygo.api.hobbygoapi.restapi.advice;

import com.hobbygo.api.hobbygoapi.dao.UserDao;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.restapi.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidatingUserRepositoryDecorator {

    public static final Logger LOGGER = LoggerFactory.getLogger(ValidatingUserRepositoryDecorator.class);

    @Autowired
    private UserDao userDao;

    public User findAccountValidated(String userName) {

        LOGGER.debug("findAccountValidated " + userName);
        
        Optional<User> userOptional = userDao.findByUserName(userName);

        return userOptional.orElseThrow(
                () -> new UserNotFoundException(userName)
        );
    }

}
