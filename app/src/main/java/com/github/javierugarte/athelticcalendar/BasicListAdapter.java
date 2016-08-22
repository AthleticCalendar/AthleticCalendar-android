package com.github.javierugarte.athelticcalendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2016 Bitban Technologies, S.L.
 * All right reserved.
 */
public class BasicListAdapter extends RecyclerView.Adapter<MatchCellViewHolder> {

    protected List<Match> mData = new ArrayList<>();
    private final Context context;

    public BasicListAdapter(Context context) {
        this.context = context;
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
    public void onBindViewHolder(MatchCellViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addEntity(int i, Match entity) {
        mData.add(i, entity);
        notifyItemInserted(i);
    }

    public void deleteEntity(int i) {
        mData.remove(i);
        notifyItemRemoved(i);
    }

    public void moveEntity(int i, int loc) {
        move(mData, i, loc);
        notifyItemMoved(i, loc);
    }

    private void move(List<Match> data, int a, int b) {
        Match temp = data.remove(a);
        data.add(b, temp);
    }

    public void setData(final List<Match> data) {
        // Remove all deleted items.
        for (int i = mData.size() - 1; i >= 0; --i) {
            if (getLocation(data, mData.get(i)) < 0) {
                deleteEntity(i);
            }
        }

        // Add and move items.
        for (int i = 0; i < data.size(); ++i) {
            Match entity = data.get(i);
            int loc = getLocation(mData, entity);
            if (loc < 0) {
                addEntity(i, entity);
            } else if (loc != i) {
                moveEntity(i, loc);

            }
        }
    }

    private int getLocation(List<Match> data, Match entity) {
        for (int j = 0; j < data.size(); ++j) {
            Match newEntity = data.get(j);
            if (entity.equals(newEntity)) {
                return j;
            }
        }

        return -1;
    }

}

