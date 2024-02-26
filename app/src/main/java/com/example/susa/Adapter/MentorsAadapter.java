package com.example.susa.Adapter;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import android.content.Context;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MentorsAadapter  extends RecyclerView.Adapter<MentorsAadapter.ViewHolder> {

    JsonArray ja;
    Context context;

    public MentorsAadapter(JsonArray ja, Context context) {
        this.ja = ja;
        this.context = context;
    }


    @NonNull
    @Override
    public MentorsAadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catagores_item, parent, false);
        return new MentorsAadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorsAadapter.ViewHolder holder, int position) {
        final JsonObject listItem = ja.get(position).getAsJsonObject();

        holder.txt_title.setText(listItem.get("name").getAsString());
        Glide.with(context)
                .load("")
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(holder.imgea_vw);

    }

    @Override
    public int getItemCount() {
        return ja.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        ImageView imgea_vw;
        CardView cat_click;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.txt_title);
            imgea_vw = itemView.findViewById(R.id.imgea_vw);
            cat_click = itemView.findViewById(R.id.cat_click);
        }
    }
}
