package com.github.javierugarte.athleticcalendar.calendar;

import android.os.AsyncTask;

import com.github.javierugarte.athleticcalendar.Match;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */

public class EventsRequestTask extends AsyncTask<Void, Void, Events> {
    private com.google.api.services.calendar.Calendar mService = null;
    private String calendarId = null;
    private Exception mLastError = null;
    private GoogleCalendar.OnEventsResponseListener l = null;

    public EventsRequestTask(GoogleAccountCredential credential, String calendarId, GoogleCalendar.OnEventsResponseListener l) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();

        this.calendarId = calendarId;
        this.l = l;
    }

    /**
     * Background task to call Google Calendar API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected Events doInBackground(Void... params) {
        try {
            return getDataFromApi();
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
    }

    /**
     * Fetch a list of the next 10 events from the primary calendar.
     * @return List of Strings describing returned events.
     * @throws IOException
     */
    private Events getDataFromApi() throws IOException {
        DateTime now = new DateTime(System.currentTimeMillis());
        return mService.events().list(calendarId)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
    }

    @Override
    protected void onPostExecute(Events events) {
        if (l != null) {
            List<Match> matches = buildMatches(events);
            l.onResult(matches);
        }
    }

    @Override
    protected void onCancelled() {
        if (mLastError != null) {
            if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                l.onError((GooglePlayServicesAvailabilityIOException) mLastError);
            } else if (mLastError instanceof UserRecoverableAuthIOException) {
                l.onErrorAuth((UserRecoverableAuthIOException) mLastError);
            } else {
                l.onError(mLastError);
            }
        } else {
            l.onCancel();
        }
    }

    private List<Match> buildMatches(Events events) {

        List<Match> matches = new ArrayList<>(events.getItems().size());

        for (Event event : events.getItems()) {
            Match match = new Match();

            match.setCalendarId(event.getId());
            match.setId(event.getDescription());

            String [] teams = getTeams(event.getSummary());
            match.setTeam1(teams[0]);
            match.setTeam2(teams[1]);

            String startDate = parseDate(event.getStart().getDateTime());
            match.setStartTime(startDate);

            match.setTvs(event.getLocation());

            matches.add(match);
        }

        return matches;
    }

    private String[] getTeams(String summary) {
        return summary.split(" vs ");
    }

    private String parseDate(DateTime date) {
        if (date == null) {
            return null;
        }

        Date otherDate = new Date(date.getValue());

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return df.format(otherDate);
    }

}

