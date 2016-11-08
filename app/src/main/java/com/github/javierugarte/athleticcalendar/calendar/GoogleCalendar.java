package com.github.javierugarte.athleticcalendar.calendar;

import com.github.javierugarte.athleticcalendar.Match;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.util.List;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */
public class GoogleCalendar {

    public interface OnEventsResponseListener {
        void onResult(List<Match> events);
        void onCancel();
        void onError(GooglePlayServicesAvailabilityIOException error);
        void onErrorAuth(UserRecoverableAuthIOException error);
        void onError(Exception error);
    }

    /**
     *
     * @param calendarId by default is "primary"
     */
    public void getEvents(String calendarId, GoogleAccountCredential credential, OnEventsResponseListener listener) {
        new EventsRequestTask(credential, calendarId, listener).execute();
    }

    public interface OnInsertEventResponseListener {
        void onEventCreated(Event event);
        void onCancel();
        void onError(GooglePlayServicesAvailabilityIOException error);
        void onErrorAuth(UserRecoverableAuthIOException error);
        void onError(Exception error);
    }

    public void insertEvent(Event event, String calendarId, GoogleAccountCredential credential, OnInsertEventResponseListener listener) {
        new InsertEventRequestTask(event, calendarId, credential, listener).execute();
    }

    public interface OnRemoveEventResponseListener {
        void onEventRemove();
        void onCancel();
    }

    public void removeEvent(String eventId, String calendarId, GoogleAccountCredential credential, OnRemoveEventResponseListener listener) {
        new RemoveEventRequestTask(eventId, calendarId, credential, listener).execute();
    }




}
