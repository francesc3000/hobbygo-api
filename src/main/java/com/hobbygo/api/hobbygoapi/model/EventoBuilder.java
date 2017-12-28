package com.hobbygo.api.hobbygoapi.model;

import com.hobbygo.api.hobbygoapi.dao.PlayerDao;
import com.hobbygo.api.hobbygoapi.model.entity.*;
import com.hobbygo.api.hobbygoapi.service.EventoService;
import com.hobbygo.api.hobbygoapi.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventoBuilder {

    @Autowired
    private GroupService groupService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private PlayerDao playerDao;

    private User user;
    private Evento evento;
    private List<Group> groupList;


    public EventoBuilder() {
        setGroupList(new ArrayList<>());
    }

    public void buildEvento() {
        List<Member> membersWithNoGroup = getEvento().getMembersWithNoGroup();

        //Calculamos el numero de grupo a crear
        int totalPlayers = membersWithNoGroup.size();
        int groupNumber = getEvento().getHobby().getGroupPlayerNumber();
        if (totalPlayers != 0){
            float nGroup = totalPlayers / groupNumber;

            int nintGroup = 0;
            if (totalPlayers % groupNumber == 0) {
                //Cuadra
                nintGroup = (int) nGroup;
            } else {
                //TODO: Descuadra, se descartan jugadores por orden de inscripción
                nintGroup = (int) nGroup + 1;
            }
            //Se crean nintGroup grupos
            int i = 0;
            Player player = playerDao.findById(getUser().getPlayerId());
            int iterator = 0;
            while(i < nintGroup){
                Group group = new Group(player,"Grupo"+i,"","Grupo"+i,getEvento().getHobby(),true);
                groupService.insertGroup(getUser().getUserName(), group);
                int z = 0;
                while(z<groupNumber){
                    Member member = membersWithNoGroup.get(iterator);
                    group.addMember(member);
                    member.setGroup(group);
                    member.getPlayer().addGroup(group);
                    playerDao.save(member.getPlayer());
                    getEvento().addGroup(group);
                    getGroupList().add(group);
                    //membersWithNoGroup.remove(iterator);
                    z++;
                    iterator++;
                }
                i++;
                groupService.updateGroup(getUser().getUserName(), group);
            }

            //Se crea la jerarquia de la competición
            //Play.Node nodeAnt=null;
            List<Group> groups = new ArrayList<>();
            for (Group group:getGroupList()) {
                //Play.Node node = new Play.Node(group.getId(), nodeAnt);
                groups.add(group);
                if(groups.size()==getEvento().getHobby().getEventGroupNumber()) {
                    //Play.Node.link(nodeAnt, node);
                    //TODO: Cambiar LocalDateTime.now() por la fecha calculada en el rango de Evento
                    getEvento().addPlay2FlowChart(new Play(groups, LocalDateTime.now(),getEvento().getHobby()));
                    groups.clear();
                }

                //nodeAnt = node;
            }
            if(!getEvento().isFlowChartEmpty())
                eventoService.updateEvento(getUser().getUserName(), getEvento());
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public void init(User user, Evento evento) {
        setUser(user);
        setEvento(evento);
    }
}
