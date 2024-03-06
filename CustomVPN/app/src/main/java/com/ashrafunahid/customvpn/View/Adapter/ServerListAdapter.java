package com.ashrafunahid.customvpn.View.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashrafunahid.customvpn.R;
import com.ashrafunahid.customvpn.View.Model.ServerInfo;
import com.ashrafunahid.customvpn.View.Model.Servers;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ServerListAdapter extends RecyclerView.Adapter<ServerListAdapter.MyViewHolder> {

    private Context context;
//    private ArrayList<Servers> serverList;
    private ArrayList<ServerInfo> serverList; // ServerList from Json

    public ServerListAdapter(Context context, ArrayList<ServerInfo> serverList) {
        this.context = context;
        this.serverList = serverList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.server_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Servers servers = serverList.get(position);
//        ServerList from json
        ServerInfo servers = serverList.get(position);
//        holder.countryName.setText(servers.getServerCountry());
        holder.countryName.setText(servers.getCountry());
        Glide.with(context).load(servers.getFlagUrl()).into(holder.countryFlag);

//        For set signal image according to server ping
        holder.countryNetwork.setImageResource(getSignalDrawable(servers.getPings()));
    }

    private int getSignalDrawable(int pings){
        if(pings <= 20) {
            return R.drawable.full_signal_cellular_alt_24;
        } else if (pings <= 30) {
            return R.drawable.medium_signal_cellular_alt_2_bar_24;
        } else {
            return R.drawable.baseline_signal_cellular_alt_1_bar_24;
        }
    }

    @Override
    public int getItemCount() {
        return serverList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView countryFlag, countryNetwork;
        TextView countryName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryFlag = itemView.findViewById(R.id.country_flag);
            countryName = itemView.findViewById(R.id.country_name);
            countryNetwork = itemView.findViewById(R.id.country_network);
        }
    }
}
