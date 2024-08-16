package com.ashrafunahid.customlistview.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashrafunahid.customlistview.Models.CountryModel;
import com.ashrafunahid.customlistview.R;

import java.util.ArrayList;

public class CountryAdapter extends ArrayAdapter<CountryModel> {

    private ArrayList<CountryModel> countryList;
    Context context;

    public CountryAdapter(ArrayList<CountryModel> countryList, Context context) {
        super(context, R.layout.custom_list_item);
        this.countryList = countryList;
        this.context = context;
    }

    // View lookup cache
    private static class ViewHolder{
        TextView countryName;
        TextView countryCupAchived;
        ImageView countryFlagImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the Data item for this position
        CountryModel country = getItem(position);

        // Check if any existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);

            viewHolder.countryName = (TextView) convertView.findViewById(R.id.country_name);
            viewHolder.countryCupAchived = (TextView) convertView.findViewById(R.id.country_cup_achived);
            viewHolder.countryFlagImage = (ImageView) convertView.findViewById(R.id.flag_image);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.countryName.setText(country.getCountryName());
        viewHolder.countryCupAchived.setText(country.getCountryCupAchived());
        viewHolder.countryFlagImage.setImageResource(country.getCountryFlag());

        return convertView;

    }
}
