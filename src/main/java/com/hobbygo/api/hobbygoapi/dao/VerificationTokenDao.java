package com.hobbygo.api.hobbygoapi.dao;

import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.model.registration.VerificationToken;
import com.hobbygo.api.hobbygoapi.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.stream.Stream;

@Repository
public class VerificationTokenDao {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    public VerificationToken findByToken(String token){
        return tokenRepository.findByToken(token);
    }

    public VerificationToken findByUser(User user){
        return tokenRepository.findByUser(user);
    }

    public Stream<VerificationToken> findAllByExpiryDateLessThan(LocalDate now){
        return tokenRepository.findAllByExpiryDateLessThan(now);
    }

    public void deleteByExpiryDateLessThan(LocalDate now){
        tokenRepository.deleteByExpiryDateLessThan(now);
    }

    public VerificationToken save(VerificationToken myToken) {
        return tokenRepository.save(myToken);
    }

    public void delete(VerificationToken verificationToken) {
        tokenRepository.delete(verificationToken);
    }
}
