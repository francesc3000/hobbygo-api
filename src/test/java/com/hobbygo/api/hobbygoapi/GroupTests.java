package com.hobbygo.api.hobbygoapi;

import com.hobbygo.api.model.entity.Group;
import com.hobbygo.api.model.entity.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.hobbygo.api.model.constants.Hobby.PADEL;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupTests {

	Player player = new Player("1","francesc3000@gmail.com","francesc3000", "http://avatar.es", PADEL);
	Group group;

	private Player getPlayer(){
		return this.player;
	}

	private Group getGroup(){
		if(group == null)
			group = new Group("1", getPlayer(),"Group 1", "http://group.com", "Descripcion",PADEL,false);

		return group;
	}

	@Test
	public void isIdCorrect() {
		assert(getGroup().getId().equals("1"));
	}

	@Test
	public void isNameCorrect() {
		assert(getGroup().getName().equals("Group 1"));
	}

	@Test
	public void isAvatarCorrect() {
		assert(getGroup().getAvatar().equals("http://group.com"));
	}

	@Test
	public void isDescriptionCorrect() {
		assert(getGroup().getDescription().equals("Descripcion"));
	}

	@Test
	public void isOwnerCorrect() {
		assert(getGroup().getOwner().equals(getPlayer()));
	}

	@Test
	public void insertMemberIsCorrect() {
		Player member = new Player("2",  "member@member.es", "user2", "http://member.es", PADEL);
		getGroup().addMember(member);
		assert(getGroup().getMembers().size()==2);
	}
}
