package com.hobbygo.api.hobbygoapi.dao;

import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.model.registration.PasswordResetToken;
import com.hobbygo.api.hobbygoapi.model.registration.PasswordResetToken;
import com.hobbygo.api.hobbygoapi.repository.PasswordResetTokenRepository;
import com.hobbygo.api.hobbygoapi.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.stream.Stream;

@Repository
public class PasswordResetTokenDao {
    
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetToken findByToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }

    public PasswordResetToken findByUser(User user){
        return passwordResetTokenRepository.findByUser(user);
    }

    public Stream<PasswordResetToken> findAllByExpiryDateLessThan(LocalDate now){
        return passwordResetTokenRepository.findAllByExpiryDateLessThan(now);
    }

    public void deleteByExpiryDateLessThan(LocalDate now){
        passwordResetTokenRepository.deleteByExpiryDateLessThan(now);
    }

    public PasswordResetToken save(PasswordResetToken myToken) {
        return passwordResetTokenRepository.save(myToken);
    }

    public void delete(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }
}
