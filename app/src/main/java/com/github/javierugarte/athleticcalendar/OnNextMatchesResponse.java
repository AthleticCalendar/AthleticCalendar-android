package com.github.javierugarte.athleticcalendar;

import java.util.List;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */
public interface OnNextMatchesResponse {

    void onSuccess(List<Match> matches);

    void onErrorResponse(Exception error);
}
