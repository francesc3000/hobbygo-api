package com.hobbygo.api.hobbygoapi.dao;

import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.model.registration.PasswordResetToken;
import com.hobbygo.api.hobbygoapi.model.registration.VerificationToken;
import com.hobbygo.api.hobbygoapi.repository.PasswordResetTokenRepository;
import com.hobbygo.api.hobbygoapi.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.stream.Stream;

@Repository
public class VerificationTokenDao {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public VerificationToken findByToken(String token){
        return verificationTokenRepository.findByToken(token);
    }

    public VerificationToken findByUser(User user){
        return verificationTokenRepository.findByUser(user);
    }

    public Stream<VerificationToken> findAllByExpiryDateLessThan(LocalDate now){
        return verificationTokenRepository.findAllByExpiryDateLessThan(now);
    }

    public void deleteByExpiryDateLessThan(LocalDate now){
        verificationTokenRepository.deleteByExpiryDateLessThan(now);
    }

    public VerificationToken save(VerificationToken myToken) {
        return verificationTokenRepository.save(myToken);
    }

    public void delete(VerificationToken verificationToken) {
        verificationTokenRepository.delete(verificationToken);
    }
}
