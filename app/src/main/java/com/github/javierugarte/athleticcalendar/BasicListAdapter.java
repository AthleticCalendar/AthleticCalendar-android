package com.github.javierugarte.athleticcalendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Copyright 2016 Bitban Technologies, S.L.
 * All right reserved.
 */
public class BasicListAdapter extends RecyclerView.Adapter<MatchCellViewHolder> {

    public interface OnClickItem {
        void onClick(View view, int position, Match match);
    }

    public interface OnLongClickItem {
        void onLongClick(View view, int position, Match match);
    }

    private List<Match> matches = null;

    private OnClickItem onClick = null;
    private OnLongClickItem onLongClickItem = null;
    private final Context context;

    public BasicListAdapter(Context context) {
        this.context = context;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClick = onClickItem;
    }

    public void setOnLongClickItem(OnLongClickItem onLongClickItem) {
        this.onLongClickItem = onLongClickItem;
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
        holder.bind(matches.get(position));
        final Match match = matches.get(position);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick != null) {
                    if (!match.exists() || match.isDifferent()) {
                        onClick.onClick(view, position, match);
                    }
                }
            }
        });
        holder.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onLongClickItem != null) {
                    onLongClickItem.onLongClick(view, position, match);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (matches != null) {
            return matches.size();
        }

        return 0;
    }

}

