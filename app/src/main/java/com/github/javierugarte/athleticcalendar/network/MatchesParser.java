package com.github.javierugarte.athleticcalendar.network;

import com.github.javierugarte.athleticcalendar.Match;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MatchesParser {

    public List<Match> parse(String json) throws Exception {
        if (json == null) {
            return null;
        }

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
        } catch (Exception e) {
            return null;
        }

        if (!jsonObject.has("matches")) {
            return null;
        }

        JSONArray leaguesArray = jsonObject.getJSONArray("matches");
        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < leaguesArray.length(); i++) {
            JSONObject leaguesJson = leaguesArray.getJSONObject(i);
            JSONArray matchesArray = leaguesJson.getJSONArray("matches");
            for (int j = 0; j < matchesArray.length(); j++) {
                JSONObject matchJson = matchesArray.getJSONObject(j);
                Match match = new Match();

                if (matchJson.getInt("status") != -1) {
                    continue;
                }

                if (matchJson.getBoolean("no_hour")) {
                    continue;
                }

                if (matchJson.has("id")) {
                    match.setId(matchJson.getString("id"));
                }

                if (matchJson.has("t1_name")) {
                    match.setTeam1(matchJson.getString("t1_name"));
                }

                if (matchJson.has("t1_shield")) {
                    match.setTeam1Shield(matchJson.getString("t1_shield"));
                }

                if (matchJson.has("t2_name")) {
                    match.setTeam2(matchJson.getString("t2_name"));
                }

                if (matchJson.has("t2_shield")) {
                    match.setTeam2Shield(matchJson.getString("t2_shield"));
                }

                if (matchJson.has("shedule")) {
                    String date = matchJson.getString("shedule");
                    match.setStartTime(date);
                }

                if (matchJson.has("channels")) {
                    match.setTvs(getTvs(matchJson));
                }


                matches.add(match);

            }
        }

        Collections.sort(matches);
        return matches;

    }

    private Date getDate(String shedule) {
        return new Date(shedule);
    }

    private String getTvs(JSONObject match) throws JSONException {
        JSONArray channelsJson = match.getJSONArray("channels");
        String tvs = "";
        for (int i = 0; i < channelsJson.length(); i++) {
            JSONObject channelJson = channelsJson.getJSONObject(i);
            if (i == channelsJson.length() - 1) {
                tvs += channelJson.getString("name");
            } else {
                tvs += channelJson.getString("name") + ", ";
            }

        }
        return tvs;
    }

}
