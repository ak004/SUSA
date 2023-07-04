package com.example.susa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.susa.CourseDetailActivity;
import com.example.susa.R;
import com.google.gson.JsonArray;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {


    JsonArray ja;
    Context context;

    public CourseAdapter(JsonArray ja, Context context) {
        this.ja = ja;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {

        holder.course_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView course_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            course_img = itemView.findViewById(R.id.course_img);
        }
    }
}
