package com.nada.photoweatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nada.photoweatherapp.R;
import com.nada.photoweatherapp.database.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private List<Album> picList = new ArrayList<>();

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.album_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.text.setText(picList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return picList.size();
    }

    public void setList(List<Album> imgList) {
        this.picList = imgList;
        notifyDataSetChanged();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {
//        private ImageView iv_pic;
        private TextView text;
        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
//            iv_pic = itemView.findViewById(R.id.iv_pic);
            text = itemView.findViewById(R.id.text);
        }
    }

}
