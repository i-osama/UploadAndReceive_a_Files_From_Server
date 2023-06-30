package com.example.uploadfilestoserver;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServerViewHolder extends RecyclerView.ViewHolder {
    ImageView img;
    TextView name, info;

    public ServerViewHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.imgFromServer);
        name = itemView.findViewById(R.id.nameFromServer);
        info = itemView.findViewById(R.id.infoFromServer);
    }
}
