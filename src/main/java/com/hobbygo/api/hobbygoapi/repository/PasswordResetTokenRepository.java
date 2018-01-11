package com.hobbygo.api.hobbygoapi.repository;

import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.model.registration.PasswordResetToken;
import com.hobbygo.api.hobbygoapi.model.registration.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken,String> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    Stream<PasswordResetToken> findAllByExpiryDateLessThan(LocalDate now);

    void deleteByExpiryDateLessThan(LocalDate now);

    //TODO: Implementar esta query
    /*
    @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(LocalDate now);
    */
}
