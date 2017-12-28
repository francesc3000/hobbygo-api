package com.hobbygo.api.hobbygoapi.restapi.resource;

import com.hobbygo.api.hobbygoapi.model.entity.Address;
import com.hobbygo.api.hobbygoapi.model.entity.Evento;
import com.hobbygo.api.hobbygoapi.model.entity.Member;
import com.hobbygo.api.hobbygoapi.model.entity.Play;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EventoResource extends ResourceSupport {

    private String eventoId;
    private String name;
    private String avatar;
    private Boolean published;
    private LocalDateTime date;
    private Address address;

    private List<String> membersUserName;
    private List<PlayResource> flowChart;

    public EventoResource(Evento evento){
        setEventoId(evento.getId());
        setName(evento.getName());
        setAvatar(evento.getAvatar());
        setPublished(evento.isPublished());
        setDate(evento.getDate());
        setAddress(evento.getAddress());
        setMembersUserName(new ArrayList<>());
        setMembers(evento.getMembers());
        setFlowChart(new ArrayList<>());
        if(!getFlowChart().isEmpty()) {
            int i=0;
            while (i <= evento.getFlowChartSize()) {
                Play play = evento.getPlayFromFlowChartById(String.valueOf(i));
                setPlayResource2FlowChart(new PlayResource(play));

                i++;
            }
        }
    }

    public String getEventoId() {
        return eventoId;
    }

    private void setEventoId(String eventoId) {
        this.eventoId = eventoId;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    private void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getPublished() {
        return published;
    }

    private void setPublished(Boolean published) {
        this.published = published;
    }

    public LocalDateTime getDate() {
        return date;
    }

    private void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Address getAddress() {
        return address;
    }

    private void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getMembersUserName() {
        return membersUserName;
    }

    private void setMembersUserName(List<String> membersId) {
        this.membersUserName = membersId;
    }

    private void setMembers(List<Member> members) {
        for(Member member:members)
            setMemberUserName(member.getPlayer().getUserName());
    }

    private void setMemberUserName(String userName){
        getMembersUserName().add(userName);
    }

    public List<PlayResource> getFlowChart() {
        return flowChart;
    }

    private void setFlowChart(List<PlayResource> flowChart) {
        this.flowChart = flowChart;
    }

    private Boolean setPlayResource2FlowChart(PlayResource playResource) {
        return getFlowChart().add(playResource);
    }
}
