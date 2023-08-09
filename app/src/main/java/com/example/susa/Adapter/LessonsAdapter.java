package com.example.susa.Adapter;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import android.content.Context;
import android.content.Intent;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.susa.R;
import com.example.susa.VideoContentActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

        final JsonObject listItem = ja.get(position).getAsJsonObject();
        Glide.with(context)
                .load(Base_image_url+listItem.get("thumb_img").getAsString())
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(holder.vid_img);

        holder.vid_id.setText(listItem.get("title").getAsString());
        holder.lesson_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoContentActivity.class);
                intent.putExtra("vid_id", listItem.get("_id").getAsString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ja.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView lesson_card;
        ImageView vid_img;
        TextView vid_id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lesson_card = itemView.findViewById(R.id.lesson_card);
            vid_img = itemView.findViewById(R.id.vid_img);
            vid_id = itemView.findViewById(R.id.vid_id);
        }
    }
}
