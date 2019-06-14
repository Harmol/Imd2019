package com.example.huaronggame;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ScoreViewHolder> {
    private final LinkedList<String> mScoreList;
    private LayoutInflater mInflater;

    public ScoreListAdapter(Context context, LinkedList<String> ScoreList) {
        mInflater = LayoutInflater.from(context);
        mScoreList = ScoreList;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.score_layout, null);
        return new ScoreViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder scoreViewHolder, int i) {
        String mCurrent = mScoreList.get(i);
        scoreViewHolder.scoreItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mScoreList.size();
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {
        public final TextView scoreItemView;
        final ScoreListAdapter mAdapter;

        public ScoreViewHolder(@NonNull View itemView, ScoreListAdapter adapter) {
            super(itemView);
            scoreItemView = itemView.findViewById(R.id.textView_score);
            this.mAdapter = adapter;
        }
    }
}
