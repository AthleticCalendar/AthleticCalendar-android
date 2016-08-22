package com.github.javierugarte.athelticcalendar;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.calendar.CalendarScopes;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final BasicListAdapter adapter = new BasicListAdapter(this);
        recyclerView.setAdapter(adapter);

        MatchDataManager matchDataManager = new MatchDataManager(this);
        matchDataManager.getNextMatches("", new OnNextMatchesResponse() {
            @Override
            public void onSuccess(List<Match> matches) {
                adapter.setData(matches);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }
}
