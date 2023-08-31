package com.ibssbd.gobeautify.BottomNavigationsFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibssbd.gobeautify.Adapters.NotificationAdapter;
import com.ibssbd.gobeautify.Models.NotificationModel;
import com.ibssbd.gobeautify.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    RecyclerView notificationView;
    List<NotificationModel> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        items = new ArrayList<>();
        items.add(new NotificationModel(R.drawable.logo_only_512x512, "Notification Title", "A quick brown fox jumped over the poor lazy dog. A quick brown fox jumped over the poor lazy dog."));
        items.add(new NotificationModel(R.drawable.logo_only_512x512, "Notification Title", "A quick brown fox jumped over the poor lazy dog. A quick brown fox jumped over the poor lazy dog."));
        items.add(new NotificationModel(R.drawable.logo_only_512x512, "Notification Title", "A quick brown fox jumped over the poor lazy dog. A quick brown fox jumped over the poor lazy dog."));
        items.add(new NotificationModel(R.drawable.logo_only_512x512, "Notification Title", "A quick brown fox jumped over the poor lazy dog. A quick brown fox jumped over the poor lazy dog."));
        items.add(new NotificationModel(R.drawable.logo_only_512x512, "Notification Title", "A quick brown fox jumped over the poor lazy dog. A quick brown fox jumped over the poor lazy dog."));
        items.add(new NotificationModel(R.drawable.logo_only_512x512, "Notification Title", "A quick brown fox jumped over the poor lazy dog. A quick brown fox jumped over the poor lazy dog."));
        items.add(new NotificationModel(R.drawable.logo_only_512x512, "Notification Title", "A quick brown fox jumped over the poor lazy dog. A quick brown fox jumped over the poor lazy dog."));
        items.add(new NotificationModel(R.drawable.logo_only_512x512, "Notification Title", "A quick brown fox jumped over the poor lazy dog. A quick brown fox jumped over the poor lazy dog."));
        items.add(new NotificationModel(R.drawable.logo_only_512x512, "Notification Title", "A quick brown fox jumped over the poor lazy dog. A quick brown fox jumped over the poor lazy dog."));

        notificationView = (RecyclerView) view.findViewById(R.id.notification_view);
        notificationView.setHasFixedSize(true);
        notificationView.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationView.setAdapter(new NotificationAdapter(items));

        return view;
    }
}