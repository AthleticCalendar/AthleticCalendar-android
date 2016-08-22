package com.github.javierugarte.athelticcalendar;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */
public class Match {

    private String id;

    private String team1;
    private String team1Shield;

    private String team2;
    private String team2Shield;

    private String date;
    private String hour;

    private String tvs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTvs() {
        return tvs;
    }

    public void setTvs(String tvs) {
        this.tvs = tvs;
    }

    public String getTitle() {
        return getTeam1() + " vs " + getTeam2();
    }

    public String getTeam1Shield() {
        return team1Shield;
    }

    public void setTeam1Shield(String team1Shield) {
        this.team1Shield = team1Shield;
    }

    public String getTeam2Shield() {
        return team2Shield;
    }

    public void setTeam2Shield(String team2Shield) {
        this.team2Shield = team2Shield;
    }
}
