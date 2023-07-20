package com.ashrafunahid.firebasetutorial.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashrafunahid.firebasetutorial.Models.UploadModel;
import com.ashrafunahid.firebasetutorial.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private List<UploadModel> uploadModels;

    private OnItemClickListener listener;

    public ImageAdapter(Context context, List<UploadModel> uploadModels) {
        this.context = context;
        this.uploadModels = uploadModels;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_card, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        UploadModel singleUploadModel = uploadModels.get(position);

        holder.textView.setText(singleUploadModel.getName());
        Picasso.get()
                .load(singleUploadModel.getImageUrl())
                .placeholder(R.drawable.baseline_image_24)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uploadModels.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textView;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textView = (TextView) itemView.findViewById(R.id.image_name);
            this.imageView = (ImageView) itemView.findViewById(R.id.view_image);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {

            if(listener != null) {

                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    listener.onItemClick(position);

                }

            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle("Select Action");
            MenuItem doWhatEver = menu.add(Menu.NONE, 1, 1,"Do Whatever");
            MenuItem delete = menu.add(Menu.NONE, 2, 2,"Delete");

            doWhatEver.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(@NonNull MenuItem item) {
            if(listener != null) {

                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            listener.onWhatEverClick(position);
                            return true;
                        case 2:
                            listener.onDeleteClick(position);
                            return true;
                    }

                }

            }
            return false;
        }
    }

    public interface OnItemClickListener {

        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;

    }
}
