package com.example.susa.Adapter;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.susa.CourseDetailActivity;
import com.example.susa.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
        final JsonObject listItem = ja.get(position).getAsJsonObject();

        holder.course_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailActivity.class);
                intent.putExtra("course_id", listItem.get("_id").getAsString());
                context.startActivity(intent);
            }
        });

        holder.cat_title.setText(listItem.get("cat").getAsJsonObject().get("title").getAsString());
        holder.txt_title.setText(listItem.get("title").getAsString());
        holder.mentor_name.setText("By " + listItem.get("mentor").getAsJsonObject().get("name").getAsString());
        Glide.with(context)
                .load(Base_image_url+listItem.get("image").getAsString())
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(holder.img_v);

    }

    @Override
    public int getItemCount() {
        return ja.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txt_title,mentor_name,cat_title;
        ImageView img_v;

        CardView course_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            course_img = itemView.findViewById(R.id.course_img);
            img_v = itemView.findViewById(R.id.img_v);
            txt_title = itemView.findViewById(R.id.txt_title);
            mentor_name = itemView.findViewById(R.id.mentor_name);
            cat_title = itemView.findViewById(R.id.cat_title);
        }
    }
}
