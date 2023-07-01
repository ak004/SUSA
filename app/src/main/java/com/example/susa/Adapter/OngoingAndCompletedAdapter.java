package com.example.susa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.susa.R;
import com.google.gson.JsonArray;

public class OngoingAndCompletedAdapter extends RecyclerView.Adapter<OngoingAndCompletedAdapter.ViewHolder> {

    JsonArray ja;
    Context context;

    public OngoingAndCompletedAdapter(JsonArray ja, Context context) {
        this.ja = ja;
        this.context = context;
    }

    @NonNull
    @Override
    public OngoingAndCompletedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ongoing_and_completd_item, parent, false);
        return new OngoingAndCompletedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingAndCompletedAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
