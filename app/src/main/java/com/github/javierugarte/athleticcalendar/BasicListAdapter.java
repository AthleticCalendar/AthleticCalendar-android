package com.github.javierugarte.athleticcalendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2016 Bitban Technologies, S.L.
 * All right reserved.
 */
public class BasicListAdapter extends RecyclerView.Adapter<MatchCellViewHolder> {

    public interface OnClickItem {
        void onClick(View view, int position, Match match);
    }

    private List<Match> dataMerge = new ArrayList<>();

    private List<Match> dataCalendar;
    private List<Match> dataServer;
    private OnClickItem onClick = null;
    private final Context context;

    public BasicListAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClick = onClickItem;
    }

    public void setDataCalendar(List<Match> dataCalendar) {
        this.dataCalendar = dataCalendar;
        mergeData();
        notifyDataSetChanged();
    }

    public void setDataServer(List<Match> dataServer) {
        this.dataServer = dataServer;
        mergeData();
        notifyDataSetChanged();
    }

    private void mergeData() {
        if (dataServer == null || dataCalendar == null) {
            return;
        }

        for (Match matchServer : this.dataServer) {
            for (Match matchCalendar : this.dataCalendar) {
                if (matchServer.getTitle().equalsIgnoreCase(matchCalendar.getTitle())) {
                    matchServer.setExists(true);
                    if (matchServer.getStartTime().getTime() != matchCalendar.getStartTime().getTime()) {
                        matchServer.setDifferentDate(true);
                    }
                    matchServer.setCalendarId(matchCalendar.getCalendarId());
                } else {
                }
            }

            dataMerge.add(matchServer);
        }
    }

    @Override
    public MatchCellViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MatchCellViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.section_item, viewGroup, false),
                context
        );
    }

    @Override
    public void onBindViewHolder(MatchCellViewHolder holder, final int position) {
        holder.bind(dataMerge.get(position));
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick != null) {
                    onClick.onClick(view, position, dataServer.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataServer != null && dataCalendar != null) {
            return dataServer.size();
        }

        return 0;
    }

}

