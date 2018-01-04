package com.hobbygo.api.hobbygoapi.repository;

import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.model.registration.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken,String> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(LocalDate now);

    void deleteByExpiryDateLessThan(LocalDate now);

    //TODO: Implementar esta query
    /*
    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(LocalDate now);
    */
}
