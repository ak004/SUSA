package com.example.susa.Adapter;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.susa.CourseDetailActivity;
import com.example.susa.R;
import com.example.susa.SharedPreferencesData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Set;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {


    JsonArray ja;
    Context context;
    SharedPreferencesData sharedPreferencesData;

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
        sharedPreferencesData = SharedPreferencesData.getInstance(context);
        Set<String> bookmarkedIds = sharedPreferencesData.getBookmarkedIds();


        holder.course_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailActivity.class);
                intent.putExtra("course_id", listItem.get("_id").getAsString());
                context.startActivity(intent);
            }
        });
        boolean isBookmarked = false;
        holder.cat_title.setText(listItem.get("cat").getAsJsonObject().get("title").getAsString());
        holder.txt_title.setText(listItem.get("title").getAsString());
        holder.mentor_name.setText("By " + listItem.get("mentor").getAsJsonObject().get("name").getAsString());
        Glide.with(context)
                .load(Base_image_url+listItem.get("image").getAsString())
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(holder.img_v);

        updateBookmarkDrawable(holder.book_icon, bookmarkedIds.contains(listItem.get("_id").getAsString()));

        holder.book_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Drawable bookmarkBorderDrawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.baseline_bookmark_border_24);
                Drawable bookmarkDrawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.baseline_bookmark_24);

                String tag = holder.book_icon.getTag().toString();
                if ("bookmark_border".equals(tag)) {
                    holder.book_icon.setTag("bookmark");
                    holder.book_icon.setImageDrawable(bookmarkDrawable);
                    bookmarkedIds.add(listItem.get("_id").getAsString());
                } else if ("bookmark".equals(tag)) {
                    holder.book_icon.setTag("bookmark_border");
                    holder.book_icon.setImageDrawable(bookmarkBorderDrawable);
                    bookmarkedIds.remove(listItem.get("_id").getAsString());

                }

                sharedPreferencesData.putBookmarkedIds(bookmarkedIds);
                updateBookmarkDrawable(holder.book_icon, bookmarkedIds.contains(listItem.get("_id").getAsString()));
                Log.d("get_booked_id", sharedPreferencesData.getBookmarkedIds() + " Ths ");
            }
        });
    }

    private void updateBookmarkDrawable(ImageView imageView, boolean isBookmarked) {
        if (isBookmarked) {
            imageView.setImageResource(R.drawable.baseline_bookmark_24);
        } else {
            imageView.setImageResource(R.drawable.baseline_bookmark_border_24);
        }
    }

    @Override
    public int getItemCount() {
        return ja.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txt_title,mentor_name,cat_title;
        ImageView img_v,book_icon;

        CardView course_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            course_img = itemView.findViewById(R.id.course_img);
            img_v = itemView.findViewById(R.id.img_v);
            txt_title = itemView.findViewById(R.id.txt_title);
            mentor_name = itemView.findViewById(R.id.mentor_name);
            cat_title = itemView.findViewById(R.id.cat_title);
            book_icon = itemView.findViewById(R.id.book_icon);
        }
    }
}
