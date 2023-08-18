package com.example.susa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.susa.CourseDetailActivity;
import com.example.susa.R;
import com.example.susa.SharedPreferencesData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Set;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    JsonArray ja;
    Context context;
    SharedPreferencesData sharedPreferencesData;
    public BookmarkAdapter(JsonArray ja, Context context) {
        this.ja = ja;
        this.context = context;
    }

    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_item, parent, false);
        return new BookmarkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.ViewHolder holder, int position) {
        final JsonObject listItem = ja.get(position).getAsJsonObject();
        sharedPreferencesData = SharedPreferencesData.getInstance(context);
        Set<String> bookmarkedIds = sharedPreferencesData.getBookmarkedIds();

        holder.course_title.setText(listItem.get("title").getAsString());
        holder.course_amount.setText("$"+listItem.get("price").getAsString());


        holder.boookmark_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Drawable bookmarkBorderDrawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.baseline_bookmark_border_24);
                Drawable bookmarkDrawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.baseline_bookmark_24);

                String tag = holder.boookmark_image.getTag().toString();
                if ("bookmark_border".equals(tag)) {
                    holder.boookmark_image.setTag("bookmark");
                    holder.boookmark_image.setImageDrawable(bookmarkDrawable);
                    bookmarkedIds.add(listItem.get("_id").getAsString());
                } else if ("bookmark".equals(tag)) {
                    holder.boookmark_image.setTag("bookmark_border");
                    holder.boookmark_image.setImageDrawable(bookmarkBorderDrawable);
                    bookmarkedIds.remove(listItem.get("_id").getAsString());

                }

                sharedPreferencesData.putBookmarkedIds(bookmarkedIds);
                updateBookmarkDrawable(holder.boookmark_image, bookmarkedIds.contains(listItem.get("_id").getAsString()));
                Log.d("get_booked_id", sharedPreferencesData.getBookmarkedIds() + " Ths ");
            }
        });

        holder.course_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailActivity.class);
                intent.putExtra("course_id", listItem.get("_id").getAsString());
                context.startActivity(intent);
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

        ImageView coure_image, boookmark_image;
        TextView course_title,course_amount;
        LinearLayout course_click;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coure_image = itemView.findViewById(R.id.coure_image);
            boookmark_image = itemView.findViewById(R.id.boookmark_image);
            course_title = itemView.findViewById(R.id.course_title);
            course_amount = itemView.findViewById(R.id.course_amount);
            course_click = itemView.findViewById(R.id.course_click);


        }
    }
}
