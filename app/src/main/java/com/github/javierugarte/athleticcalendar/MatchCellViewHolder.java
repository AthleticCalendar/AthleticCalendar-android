package com.github.javierugarte.athleticcalendar;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Copyright 2016 Bitban Technologies, S.L.
 * All right reserved.
 */
public class MatchCellViewHolder extends RecyclerView.ViewHolder {

    enum State {
        ADDED,
        ADD,
        MODIFY
    }

    private final Context context;
    private final View view;

    private State state = State.ADD;

    private final TextView team1TextView;
    private final TextView team2TextView;
    private final TextView dateTextView;
    private final TextView hourTextView;
    private final ImageView team1ImageView;
    private final ImageView team2ImageView;
    private final TextView stateTextView;
    private final TextView tvsTextView;

    public MatchCellViewHolder(View v, Context context) {
        super(v);
        this.context = context;
        this.view = v;
        team1TextView = (TextView) v.findViewById(R.id.tv_team1);
        team2TextView = (TextView) v.findViewById(R.id.tv_team2);
        dateTextView = (TextView) v.findViewById(R.id.tv_date);
        hourTextView = (TextView) v.findViewById(R.id.tv_hour);
        team1ImageView = (ImageView) v.findViewById(R.id.iv_team1);
        team2ImageView = (ImageView) v.findViewById(R.id.iv_team2);
        stateTextView = (TextView) v.findViewById(R.id.tv_state);
        tvsTextView = (TextView) v.findViewById(R.id.tv_tvs);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void bind(Match match){

        CharSequence date = null;
        CharSequence hour = null;
        if (match.getStartTime() != null) {
            date = DateFormat.format("dd/MM/yyyy", match.getStartTime());
            hour = DateFormat.format("kk:mm", match.getStartTime());
        }

        team1TextView.setText(match.getTeam1());
        team2TextView.setText(match.getTeam2());
        dateTextView.setText(date);
        tvsTextView.setText(match.getTvs());
        hourTextView.setText(hour);

        Glide.with(context).load(match.getTeam1Shield()).into(team1ImageView);
        Glide.with(context).load(match.getTeam2Shield()).into(team2ImageView);

        if (match.isDifferent()) {
            state = State.MODIFY;
        } else {
            state = State.ADDED;
        }
        if (!match.exists()) {
            state = State.ADD;
        }

        setState(state);

    }

    private void setState(State state) {
        switch (state) {
            case ADDED:
                stateTextView.setText("ADDED");
                stateTextView.setTextColor(getColor(R.color.added));
                break;
            case ADD:
                stateTextView.setText("ADD");
                stateTextView.setTextColor(getColor(R.color.add));
                break;
            case MODIFY:
                stateTextView.setText("MODIFY");
                stateTextView.setTextColor(getColor(R.color.modify));
                break;
        }
    }

    private int getColor(int color) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            return context.getResources().getColor(color, context.getTheme());
        } else {
            return context.getResources().getColor(color);
        }
    }

    public View getView() {
        return view;
    }

}
