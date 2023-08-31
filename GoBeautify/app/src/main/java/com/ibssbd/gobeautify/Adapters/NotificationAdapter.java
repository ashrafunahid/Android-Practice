package com.ibssbd.gobeautify.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibssbd.gobeautify.Models.NotificationModel;
import com.ibssbd.gobeautify.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<NotificationModel> items;
    public NotificationAdapter(List<NotificationModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {

        holder.notificationImage.setImageResource(items.get(position).getNotificationImage());
        holder.notificationTitle.setText(items.get(position).getNotificationTitle());
        holder.notificationBody.setText(items.get(position).getNotificationBody());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView notificationImage;
        TextView notificationTitle;
        TextView notificationBody;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationImage = (ImageView) itemView.findViewById(R.id.notification_image);
            notificationTitle = (TextView) itemView.findViewById(R.id.notification_title);
            notificationBody = (TextView) itemView.findViewById(R.id.notification_body);

        }
    }
}
