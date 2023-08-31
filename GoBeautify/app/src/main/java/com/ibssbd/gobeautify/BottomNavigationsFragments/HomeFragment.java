package com.ibssbd.gobeautify.BottomNavigationsFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ibssbd.gobeautify.Activities.ChangeBackground;
import com.ibssbd.gobeautify.Activities.EditPhoto;
import com.ibssbd.gobeautify.Activities.PhotoUpscale;
import com.ibssbd.gobeautify.Activities.RemoveBackground;
import com.ibssbd.gobeautify.R;

public class HomeFragment extends Fragment {

    ImageView editPhoto, removeBackground, upscale;
    ImageView changeBackground;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        editPhoto = (ImageView) view.findViewById(R.id.edit_photo);
        Glide.with(this).load(R.drawable.background_change).into(editPhoto);

        removeBackground = (ImageView) view.findViewById(R.id.remove_background);
        Glide.with(this).load(R.drawable.girl).into(removeBackground);

        upscale = (ImageView) view.findViewById(R.id.photo_upscale);
        Glide.with(this).load(R.drawable.man).into(upscale);

//        changeBackground = (ImageView) view.findViewById(R.id.change_background);
//        Glide.with(this).load(R.drawable.girl).into(changeBackground);

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditPhoto.class);
                startActivity(intent);
            }
        });

        removeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeBackground.class);
                startActivity(intent);
            }
        });

        upscale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PhotoUpscale.class);
                startActivity(intent);
            }
        });

//        changeBackground.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ChangeBackground.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }
}