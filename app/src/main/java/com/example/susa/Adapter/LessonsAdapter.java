package com.example.susa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.susa.R;
import com.example.susa.VideoContentActivity;
import com.google.gson.JsonArray;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {


    JsonArray ja;
    Context context;

    public LessonsAdapter(JsonArray ja, Context context) {
        this.ja = ja;
        this.context = context;
    }

    @NonNull
    @Override
    public LessonsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_items, parent, false);
        return new LessonsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsAdapter.ViewHolder holder, int position) {
        holder.lesson_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoContentActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView lesson_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lesson_card = itemView.findViewById(R.id.lesson_card);
        }
    }
}
