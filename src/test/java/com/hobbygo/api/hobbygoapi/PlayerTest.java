package com.hobbygo.api.hobbygoapi;

import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import com.hobbygo.api.hobbygoapi.model.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerTest {

	Player player = new Player("1","francesc3000@gmail.com","francesc3000", "http://avatar.es", Hobby.PADEL);

	private Player getPlayer(){
		return this.player;
	}

	@Test
	public void isIdCorrect() {
		assert(getPlayer().getId().equals("1"));
	}

	@Test
	public void isNameCorrect() {
		assert(getPlayer().getUserId().equals("Francesc"));
	}

	@Test
	public void isAvatarCorrect() {
		assert(getPlayer().getAvatar().equals("http://avatar.es"));
	}

	@Test
	public void insertGroupIsCorrect() {
		getPlayer().addGroup(new Group("1", getPlayer(),"Grupo 1","http://group.com","Descripcion", Hobby.PADEL,false));
		assert(getPlayer().getGroupList().size()==1);
	}

	@Test
	public void getEventoIsCorrect() {
		Address address = new Address("street1","street2","city",
				"state","08940",new Float(38.11450),new Float(158.0014));
		Evento evento = new Evento("1", new Member(getPlayer(),null),"Evento 1","http://evento.com",
									   false, LocalDateTime.now(),LocalDateTime.now(),
									  address,4, Hobby.PADEL);
		getPlayer().setEvento(evento);
		assert(getPlayer().getEventoList().size()==1);
	}

}
