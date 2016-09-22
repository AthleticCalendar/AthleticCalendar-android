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

    private final Context context;
    private final View view;

    private final TextView titleTextView;
    private final TextView dateTextView;
    private final ImageView team1ImageView;
    private final ImageView team2ImageView;

    public MatchCellViewHolder(View v, Context context) {
        super(v);
        this.context = context;
        this.view = v;
        titleTextView = (TextView) v.findViewById(R.id.tv_title);
        dateTextView = (TextView) v.findViewById(R.id.tv_date);
        team1ImageView = (ImageView) v.findViewById(R.id.iv_team1);
        team2ImageView = (ImageView) v.findViewById(R.id.iv_team2);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void bind(Match match){

        CharSequence date = null;
        if (match.getStartTime() != null) {
            date = DateFormat.format("dd-MM-yyyy kk:mm", match.getStartTime());
        }

        titleTextView.setText(match.getTitle());
        dateTextView.setText(date);
        Glide.with(context).load(match.getTeam1Shield()).into(team1ImageView);
        Glide.with(context).load(match.getTeam2Shield()).into(team2ImageView);

        if (match.isDifferentDate()) {
            view.setBackgroundColor(Color.RED);
        } else {
            view.setBackgroundColor(Color.GREEN);
        }

        if (!match.exists()) {
            view.setBackgroundColor(Color.BLUE);
        }
    }

    public View getView() {
        return view;
    }

}
