package com.github.javierugarte.athleticcalendar.calendar;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */

public class RemoveEventRequestTask extends AsyncTask<Object, Object, Void> {
    private com.google.api.services.calendar.Calendar mService = null;
    private String eventId = null;
    private String calendarId = null;
    private Exception mLastError = null;
    private GoogleCalendar.OnRemoveEventResponseListener l = null;

    public RemoveEventRequestTask(String eventId, String calendarId, GoogleAccountCredential credential, GoogleCalendar.OnRemoveEventResponseListener l) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();

        this.eventId = eventId;
        this.calendarId = calendarId;
        this.l = l;
    }

    @Override
    protected Void doInBackground(Object... objects) {
        try {
            mService.events().delete(calendarId, eventId).execute();
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (l != null) {
            l.onEventRemove();
        }
    }

    @Override
    protected void onCancelled() {
        if (mLastError == null) {
            l.onCancel();
        }
    }
}

