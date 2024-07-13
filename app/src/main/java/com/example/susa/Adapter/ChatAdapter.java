package com.example.susa.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.susa.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    JsonArray ja;
    Context context;

    public ChatAdapter(JsonArray ja, Context context) {
        this.ja = ja;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesg_item, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        JsonObject jo = ja.get(position).getAsJsonObject();
//        holder.user_msg.setVisibility(View.GONE);
//        holder.mentor_msg.setVisibility(View.GONE);


        if (jo.has("type_user")) {
            String type = jo.get("type_user").getAsString();
            if (type.equalsIgnoreCase("user")) {
                holder.user_msg.setText(jo.get("msg").getAsString());
                holder.user_msg.setVisibility(View.VISIBLE);
                holder.mentor_msg.setVisibility(View.GONE);
            } else if (type.equalsIgnoreCase("mentor")) {
                holder.mentor_msg.setText(jo.get("msg").getAsString());
                holder.mentor_msg.setVisibility(View.VISIBLE);
                holder.user_msg.setVisibility(View.GONE);
            } else {
                Log.e("JsonObject", "Unknown user type: " + type);
            }
        } else {
            Log.e("JsonObject", "Missing user type");
        }

        Log.d("the_chatss", jo + "");
        Log.d("the_chatss", jo.get("type_user") + "");
    }

    @Override
    public int getItemCount() {
        return ja.size();
    }



    public void addMessage(JsonObject message) {
        ja.add(message);
        notifyItemInserted(ja.size() - 1);
    }

    public void removeMessage(JsonObject message) {
        int index = findMessageIndex(message);
        if (index != -1) {
            ja.remove(index);
            notifyItemRemoved(index);
        }
    }

    private int findMessageIndex(JsonObject message) {
        for (int i = 0; i < ja.size(); i++) {
            if (ja.get(i).equals(message)) {
                return i;
            }
        }
        return -1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_msg, mentor_msg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_msg = itemView.findViewById(R.id.user_msg);
            mentor_msg = itemView.findViewById(R.id.mentor_msg);

        }
    }
}
