package com.github.javierugarte.athleticcalendar.network;

import android.util.Log;

import com.github.javierugarte.athleticcalendar.Match;
import com.github.javierugarte.athleticcalendar.OnNextMatchesResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */
public class NextMatchesRequest {

    private static final String URL = "http://www.resultados-futbol.com/scripts/api/api.php?key=b3fcd6725e03f4e5d588f6624cac5522&format=json&rm=0&tz=Europe/Madrid&lang=es&clang=es&isocode=es&site=ResultadosAndroid&v=327&req=matches_team&id=347";

    public void getNextMatches(String teamId, final OnNextMatchesResponse onNextMatchesResponse) {

        Request request = new Request.Builder()
                .url(URL)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onNextMatchesResponse.onErrorResponse(new Exception(e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                List<Match> matches = null;
                try {
                    matches = new MatchesParser().parse(json);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("", "It has not been possible to obtain the service response.");
                }

                onNextMatchesResponse.onSuccess(matches);
            }
        });

    }
}
