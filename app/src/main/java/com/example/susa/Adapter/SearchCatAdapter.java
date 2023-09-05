package com.example.susa.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.susa.R;
import com.example.susa.SearchCourseActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SearchCatAdapter extends RecyclerView.Adapter<SearchCatAdapter.ViewHolder> {

    JsonArray ja;
    Context context;
    SearchCourseActivity searchCourseActivity;
    String selected;
    private int selectedItemPosition = -1;
    public SearchCatAdapter(JsonArray ja, Context context ,SearchCourseActivity searchCourseActivity, String selected)  {
        this.ja = ja;
        this.context = context;
        this.searchCourseActivity = searchCourseActivity;
        this.selected = selected;
    }

    @NonNull
    @Override
    public SearchCatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item, parent, false);
        return new SearchCatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCatAdapter.ViewHolder holder, int position) {
        final JsonObject listItem = ja.get(position).getAsJsonObject();

        holder.text.setText(listItem.get("title").getAsString());

        if(selected.equalsIgnoreCase(listItem.get("_id").getAsString())) {
            Toast.makeText(context, "T:"+ listItem.get("_id"), Toast.LENGTH_SHORT).show();
            selectedItemPosition = position;
        }

        if (position == selectedItemPosition) {
            int color = ContextCompat.getColor(context, R.color.colorPrimaryDark);
            holder.card_click.setBackgroundTintList(ColorStateList.valueOf(color));
            holder.text.setTextColor(Color.WHITE);
        } else {
            int color = ContextCompat.getColor(context, R.color.white);
            holder.card_click.setBackgroundTintList(ColorStateList.valueOf(color));

//            holder.card_click.setCardBackgroundColor(Color.WHITE);
            holder.text.setTextColor(Color.BLACK);
        }

        // Set an OnClickListener to handle item selection
        holder.card_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItemPosition != position) {
                    // If another item was previously selected, deselect it
                    int previousSelectedItemPosition = selectedItemPosition;
                    selectedItemPosition = position;
                    notifyItemChanged(previousSelectedItemPosition);
                    notifyItemChanged(selectedItemPosition);
                    onItemClick(position);

                } else {
                    // If the same item is clicked again, deselect it
                    selectedItemPosition = -1;
                    searchCourseActivity.filter_cat("");
                    notifyItemChanged(position);
                }

                // Handle the item click event here (e.g., open a new activity or perform an action)
            }
        });

    }

    private void onItemClick(int position) {
        // Handle the item click event here (e.g., open a new activity or perform an action)
        searchCourseActivity.filter_cat(ja.get(position).getAsJsonObject().get("_id").getAsString());

    }
    @Override
    public int getItemCount() {
        return ja.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        CardView card_click;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_click = itemView.findViewById(R.id.card_click);
            text = itemView.findViewById(R.id.text);
        }
    }
}
