package com.example.susa.Adapter;

import static com.example.susa.Web_service.ApiClient.Base_image_url;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.susa.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.ViewHolder> {


    JsonArray ja;
    Context context;


    public AttachmentsAdapter(JsonArray ja, Context context) {
        this.ja = ja;
        this.context = context;
    }

    @NonNull
    @Override
    public AttachmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attachments_item, parent, false);
        return new AttachmentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttachmentsAdapter.ViewHolder holder, int position) {
        holder.title.setText(ja.get(position).getAsString());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String documentUrl  = Base_image_url +  ja.get(position).getAsString();
                Log.d("documentUrldocumentUrl", "documentUrl: "+ documentUrl);
                // Set the appropriate MIME type based on the file extension
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(documentUrl));
                try {
                    // Start the activity to open the document URL in a web browser
                    context.startActivity(intent1);
                } catch (ActivityNotFoundException e) {
                    Log.d("documentUrldocumentUrl", "errr r: "+ e);

                    // Handle exception if no suitable app is found to open the URL
                    // For example, show a message to the user or provide alternative options
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ja.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
