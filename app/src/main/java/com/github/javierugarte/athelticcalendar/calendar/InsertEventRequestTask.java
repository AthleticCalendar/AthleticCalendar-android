package com.github.javierugarte.athelticcalendar.calendar;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.model.Event;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */

public class InsertEventRequestTask extends AsyncTask<Void, Void, Event> {
    private com.google.api.services.calendar.Calendar mService = null;
    private Event event = null;
    private String calendarId = null;
    private Exception mLastError = null;
    private GoogleCalendar.OnInsertEventResponseListener l = null;

    public InsertEventRequestTask(Event event, String calendarId, GoogleAccountCredential credential, GoogleCalendar.OnInsertEventResponseListener l) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();

        this.event = event;
        this.calendarId = calendarId;
        this.l = l;
    }

    /**
     * Background task to call Google Calendar API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected Event doInBackground(Void... params) {
        try {
            return mService.events().insert(calendarId, event).execute();
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Event event) {
        if (l != null) {
            l.onEventCreated(event);
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
}

