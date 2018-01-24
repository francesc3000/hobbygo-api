package com.hobbygo.api.hobbygoapi.repository;

import com.hobbygo.api.hobbygoapi.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    //User findOneByUsername(String username);

    User findOneByUserName(String username);

    Optional<User> findByUserName(String userName);

    User findByEmail(String email);
}
