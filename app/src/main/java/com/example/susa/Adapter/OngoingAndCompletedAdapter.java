package com.example.susa.Adapter;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.susa.CourseDetailActivity;
import com.example.susa.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
        final JsonObject listItem = ja.get(position).getAsJsonObject();

        holder.title_.setText(listItem.get("title").getAsString());
        holder.durattion.setText(listItem.get("course").getAsJsonObject().get("duration").getAsString() + " Hour of course ");

        Glide.with(context)
                .load(Base_image_url+listItem.get("course").getAsJsonObject().get("image").getAsString())
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(holder.courr_img);

        holder.course_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailActivity.class);
                intent.putExtra("course_id", listItem.get("module_id").getAsString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ja.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView courr_img;
        TextView title_,durattion;
        LinearLayout course_click;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_ = itemView.findViewById(R.id.title_);
            durattion = itemView.findViewById(R.id.durattion);
            courr_img = itemView.findViewById(R.id.courr_img);
            course_click = itemView.findViewById(R.id.course_click);

        }
    }
}
