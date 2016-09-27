package com.github.javierugarte.athleticcalendar;

import android.text.format.DateFormat;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

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

    private String startTime;
    private String endTime;

    private String tvs;
    private boolean different;
    private String calendarId;
    private boolean exists;

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

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(DateTime dateTime) {
        startTime = dateTime.toString();
    }

    public Date getStartTime() {
        if (startTime == null) {
            return null;
        }

        return parseDate(startTime, "yyyy/MM/dd hh:mm:ss");
    }

    public String getStartTimeWithFormat() {
        return "" + DateFormat.format("dd-MM-yyyy kk:mm", getStartTime());
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        if (endTime == null) {
            return null;
        }

        return parseDate(endTime, "yyyy/MM/dd hh:mm:ss");
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

    public void setDifferent(boolean different) {
        this.different = different;
    }

    public boolean isDifferent() {
        return different;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean exists() {
        return this.exists;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getCalendarId() {
        return this.calendarId;
    }

    private Date parseDate(String date, String format) {
        if (date == null || format == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Event getEvent() {
        Event event = new Event()
                .setSummary(getTitle())
                .setLocation(getTvs());

        DateTime startDateTime = new DateTime(getStartTime());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Madrid");
        event.setStart(start);

        DateTime endDateTime = new DateTime(getEndTime());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Madrid");
        event.setEnd(end);

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("popup").setMinutes(10),
        };

        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        return event;

    }

}
