package com.ashrafunahid.weatherupdate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashrafunahid.weatherupdate.Domains.Hourly;
import com.ashrafunahid.weatherupdate.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {
    ArrayList<Hourly> items;
    Context context;

    public HourlyAdapter(ArrayList<Hourly> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public HourlyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_view_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyAdapter.ViewHolder holder, int position) {

        holder.hourText.setText(items.get(position).getHour());
        holder.tempText.setText(items.get(position).getTemp()+"°C");

        int drawableResourceId = holder.itemView
                .getResources()
                .getIdentifier(items.get(position).getPicPath(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.hourlyPicture);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView hourText, tempText;
        ImageView hourlyPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hourText = itemView.findViewById(R.id.hour_text);
            tempText = itemView.findViewById(R.id.temp_text);
            hourlyPicture = itemView.findViewById(R.id.hourly_picture);

        }
    }
}
