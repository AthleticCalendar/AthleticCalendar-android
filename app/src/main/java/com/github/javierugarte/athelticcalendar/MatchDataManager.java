package com.github.javierugarte.athelticcalendar;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */
public class MatchDataManager {

    private final Context context;

    public MatchDataManager(Context context) {
        this.context = context;
    }

    public void getNextMatches(String team, final OnNextMatchesResponse listener) {
        
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://jugarte.es/api/athcalendar/EventList.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<Match> matches = null;

                        try {
                            matches = buildMatches(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listener.onSuccess(matches);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });
        
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private List<Match> buildMatches(String response) throws JSONException {
        ArrayList<Match> matches = null;

        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonMatches = jsonObject.optJSONArray("matches");

        if (jsonMatches != null) {
            matches = new ArrayList<>();

            for (int i = 0; i < jsonMatches.length(); i++) {
                JSONObject jsonMatch = jsonMatches.getJSONObject(i);

                Match match = new Match();
                match.setId(jsonMatch.getString("id"));

                match.setTeam1(jsonMatch.getString("team1"));
                match.setTeam1Shield(jsonMatch.getString("team1_shield"));

                match.setTeam2(jsonMatch.getString("team2"));
                match.setTeam2Shield(jsonMatch.getString("team2_shield"));

                match.setDate(jsonMatch.getString("date"));
                match.setHour(jsonMatch.getString("hour"));

                match.setTvs(jsonMatch.getString("tv"));

                matches.add(match);
            }

        }

        return matches;
    }
}
