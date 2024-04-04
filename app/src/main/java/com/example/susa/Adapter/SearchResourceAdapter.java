package com.example.susa.Adapter;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.susa.R;
import com.example.susa.ResourceDetails;
import com.example.susa.SharedPreferencesData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SearchResourceAdapter extends RecyclerView.Adapter<SearchResourceAdapter.ViewHolder> implements Filterable {

    JsonArray ja;
    Context context;
    SharedPreferencesData sharedPreferencesData;
    JsonArray itemlist_filter;

    public SearchResourceAdapter(JsonArray ja, Context context) {
        this.ja = ja;
        this.context = context;
        this.itemlist_filter = ja;
    }




    @NonNull
    @Override
    public SearchResourceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_item_filter, parent, false);
        return new SearchResourceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResourceAdapter.ViewHolder holder, int position) {
        final JsonObject listItem = ja.get(position).getAsJsonObject();

        holder.redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ResourceDetails.class);
                intent.putExtra("resource_id", listItem.get("_id").getAsString());
                intent.putExtra("username", listItem.get("username").getAsString());
                intent.putExtra("catagory", listItem.get("catagory").getAsString());
                intent.putExtra("title", listItem.get("title").getAsString());
                intent.putExtra("extenstion", listItem.get("extenstion").getAsString());
                intent.putExtra("created_at", listItem.get("created_at").getAsString());
                intent.putExtra("desc", listItem.get("desc").getAsString());
                intent.putExtra("no_download", listItem.get("no_download").getAsString());
                intent.putExtra("url", listItem.get("url").getAsString());

                context.startActivity(intent);
            }
        });
        holder.owner_user.setText(listItem.get("username").getAsString());
        holder.cat_title.setText(listItem.get("catagory").getAsString());
        holder.extension.setText(listItem.get("extenstion").getAsString());
        holder.title.setText(listItem.get("title").getAsString());
        holder.created_at.setText(listItem.get("created_at").getAsString());
        holder.no_downloads.setText(listItem.get("no_download").getAsString());
        Glide.with(context)
                .load(Base_image_url +  listItem.get("user_profile").getAsString())
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(holder.profile_img);

    }

    @Override
    public int getItemCount() {
        return itemlist_filter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView owner_user, cat_title, extension, title,created_at,no_downloads;
        ImageView profile_img;
        LinearLayout redirect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            owner_user = itemView.findViewById(R.id.mentore);
            cat_title = itemView.findViewById(R.id.cat_title);
            extension = itemView.findViewById(R.id.extension);
            title = itemView.findViewById(R.id.title);
            created_at = itemView.findViewById(R.id.created_at);
            no_downloads = itemView.findViewById(R.id.no_downloads);

            profile_img = itemView.findViewById(R.id.profile_img);
            redirect = itemView.findViewById(R.id.redirect);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemlist_filter = ja;
                } else {
                    JsonArray filteredList = new JsonArray();
                    for (int i = 0; i < ja.size(); i++) {
                        JsonObject jsonObject = ja.get(i).getAsJsonObject();
                        if (jsonObject.get("title").getAsString().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(jsonObject);
                        }
                    }
                    itemlist_filter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemlist_filter;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemlist_filter = (JsonArray) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

}
