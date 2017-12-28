package com.hobbygo.api.hobbygoapi.restapi.dto;

import com.hobbygo.api.hobbygoapi.model.entity.Address;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ModifyEventoDto {

    public static String OWNER = "OWNER";
    public static String MEMBER = "MEMBER";

    @NotNull
    private String id;
    private String name;
    private String avatar;
    private Boolean published;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private int dday;
    private int dmonth;
    private int dyear;
    private Address address;
    private int maxPlayers;
    private String hobby;

    private List<String> ownerIdList;
    private List<String> memberIdList;

    private ModifyEventoDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getOwnerIdList() {
        return ownerIdList;
    }

    public void setOwnerIdList(List<String> ownerIdList) {
        this.ownerIdList = ownerIdList;
    }

    public List<String> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<String> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getDday() {
        return dday;
    }

    public void setDday(int dday) {
        this.dday = dday;
    }

    public int getDmonth() {
        return dmonth;
    }

    public void setDmonth(int dmonth) {
        this.dmonth = dmonth;
    }

    public int getDyear() {
        return dyear;
    }

    public void setDyear(int dyear) {
        this.dyear = dyear;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
