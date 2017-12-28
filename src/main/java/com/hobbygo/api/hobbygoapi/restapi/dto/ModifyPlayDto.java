package com.hobbygo.api.hobbygoapi.restapi.dto;

import java.time.LocalDateTime;

public class ModifyPlayDto {

    private String id;
    private int winnerScore;
    private int loserScore;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;

    private ModifyPlayDto(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWinnerScore() {
        return winnerScore;
    }

    public void setWinnerScore(int winnerScore) {
        this.winnerScore = winnerScore;
    }

    public int getLoserScore() {
        return loserScore;
    }

    public void setLoserScore(int loserScore) {
        this.loserScore = loserScore;
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

    public LocalDateTime getLocalDateTime(){
        return LocalDateTime.of(getYear(),getMonth(),getDay(),getHour(),getMinute());
    }
}
