package com.example.uploadfilestoserver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uploadfilestoserver.ServerModel.ServerModel;

import java.util.List;

public class ServerAdapter extends RecyclerView.Adapter<ServerViewHolder> {
    List<ServerModel> modelList;
    Context context;

    public ServerAdapter(List<ServerModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ServerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_recycler, parent, false);
        return new ServerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServerViewHolder holder, int position) {
        ServerModel model = modelList.get(position);
        String url = "https://zeeshan-has-this.000webhostapp.com/PHP/images/";

        try {
            holder.name.setText(model.getName());
        }catch (Exception e){
            holder.name.setText(e.getLocalizedMessage());
        }

        try {
            holder.info.setText(model.getInfo());
        }catch (Exception e){
            holder.info.setText(e.getLocalizedMessage());
        }

        try {
            Glide.with(context).load(url+model.getImg()).into(holder.img);
        }catch (Exception e){}


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
