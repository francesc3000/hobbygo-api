package com.hobbygo.api.hobbygoapi.service;

import com.hobbygo.api.hobbygoapi.dao.PlayerDao;
import com.hobbygo.api.hobbygoapi.dao.UserDao;
import com.hobbygo.api.hobbygoapi.model.entity.Contact;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.model.entity.User;
import com.hobbygo.api.hobbygoapi.model.mail.Email;
import com.hobbygo.api.hobbygoapi.model.mail.EmailService;
import com.hobbygo.api.hobbygoapi.model.mail.EmailTemplate;
import com.hobbygo.api.hobbygoapi.restapi.advice.ValidatingUserRepositoryDecorator;
import com.hobbygo.api.hobbygoapi.restapi.dto.ContactDto;
import com.hobbygo.api.hobbygoapi.security.PlayerSecurityService;
import com.hobbygo.api.hobbygoapi.service.mapping.ContactMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayerService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private ValidatingUserRepositoryDecorator validatingUserRepositoryDecorator;

    @Autowired
    private PlayerSecurityService playerSecurityService;

    @Autowired
    EmailService emailService;

    /*
    public List<PlayerDto> getAllPlayers() {

        List<Player> players = playerDao.getAllPlayers();

        return ContactMapping.mapPlayerList2PlayerDtoList(players);
    }*/

    public Player setContacts(User user, List<ContactDto> contactsDto) {
        Player player = playerDao.findById(user.getPlayerId());
        player.setContactList(ContactMapping.mapContactDtoList2ContactList(contactsDto));

        Player playerRet = playerDao.save(player);
        discoverFriends(user, player);
        return playerRet;
    }

    @Async
    private void discoverFriends(User user, Player player){
        for(Contact contact:player.getContactList()){
            Player playerCandidate =
                    playerDao.findByEmail(contact.getEmail());
            if(playerCandidate!=null) {
                player.addFriend(playerCandidate);
                playerCandidate.addFriend(player);
                playerDao.save(playerCandidate);
                playerDao.save(player);
            }else if(contact.isMarked2send()) {
                //TODO: Enviar por correo invitación a la aplicación
                String from = "infoapp@gmail.com";
                String to = contact.getEmail();
                String subject = "Billete hacia tus deseos";

                EmailTemplate template = new EmailTemplate("hello-world-plain.txt");

                Map<String, String> replacements = new HashMap<String, String>();
                replacements.put("user", contact.getName());
                replacements.put("today", String.valueOf(LocalDate.now()));

                String message = template.getTemplate(replacements);

                Email email = new Email(from, to, subject, message);

                emailService.send(email);
                contact.markAsSended();
            }
        }
    }

    @PreAuthorize("@playerSecurityService.friendUserNameExists(#friendUserName)")
    public Player request4Relationship(User user, String friendUserName) {
        Player player = playerDao.findById(user.getPlayerId());

        User friend = userDao.findByUserName(friendUserName).get();
        Player friendPlayer = playerDao.findById(friend.getPlayerId());

        player.addFriend(friendPlayer);
        friendPlayer.addCandidate(player);

        playerDao.save(friendPlayer);
        return playerDao.save(player);
    }

/*
    public Player sendRequest2Candidates(String userName, List<CandidateDto> candidatesDto) {
        User user = userDao.findByUserName(userName).get();
        Player player = playerDao.findById(user.getPlayerId());

        player.addAllCandidates(ContactMapping.mapCandidateListDto2CandidateList(candidatesDto));
        Player playerRet = playerDao.save(player);
        //TODO: Enviar por correo inviatación a la aplicación

        return playerRet;
    }
*/
}
