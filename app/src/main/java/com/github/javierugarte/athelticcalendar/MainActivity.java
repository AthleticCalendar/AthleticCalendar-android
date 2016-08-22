package com.github.javierugarte.athelticcalendar;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final BasicListAdapter adapter = new BasicListAdapter(this);
        recyclerView.setAdapter(adapter);

        MatchDataManager matchDataManager = new MatchDataManager(this);
        matchDataManager.getNextMatches("", new OnNextMatchesResponse() {
            @Override
            public void onSuccess(List<Match> matches) {
                adapter.setData(matches);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    public class BasicListAdapter extends RecyclerView.Adapter<ViewHolder> {

        protected List<Match> mData = new ArrayList<>();
        private final Context context;

        public BasicListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(
                    LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.section_item, viewGroup, false),
                    context
            );
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        private final TextView textView;
        private final ImageView team1ImageView;
        private final ImageView team2ImageView;

        public ViewHolder(View v, Context context) {
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
}
