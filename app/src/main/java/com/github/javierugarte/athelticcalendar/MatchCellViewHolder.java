package com.github.javierugarte.athelticcalendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    private final TextView textView;
    private final ImageView team1ImageView;
    private final ImageView team2ImageView;

    public MatchCellViewHolder(View v, Context context) {
        super(v);
        this.context = context;
        textView = (TextView) v.findViewById(R.id.tv_title);
        team1ImageView = (ImageView) v.findViewById(R.id.iv_team1);
        team2ImageView = (ImageView) v.findViewById(R.id.iv_team2);
    }

    public void bind(Match match){
        textView.setText(match.getTitle());
        Glide.with(context).load(match.getTeam1Shield()).into(team1ImageView);
        Glide.with(context).load(match.getTeam2Shield()).into(team2ImageView);
    }

}
