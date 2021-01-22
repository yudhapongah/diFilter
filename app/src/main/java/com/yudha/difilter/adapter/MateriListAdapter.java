package com.yudha.difilter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yudha.difilter.R;
import com.yudha.difilter.model.Materi;

import java.util.List;

public class MateriListAdapter extends RecyclerView.Adapter<MateriListAdapter.MateriListHolder> {

    private List<Materi> list;

    public void setList(List<Materi> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public MateriListAdapter(List<Materi> list) {
        this.list = list;
    }



    @NonNull
    @Override
    public MateriListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_materi, parent, false);

        return new MateriListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriListHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MateriListHolder extends  RecyclerView.ViewHolder{
        final ImageView ivImage;
        final TextView tvTitle;
        public MateriListHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.iv_materi_item_image);
            ivImage.setClipToOutline(true);
            tvTitle = itemView.findViewById(R.id.tv_materi_item_title);
            itemView.findViewById(R.id.dark_background).setClipToOutline(true);
        }

        public void bind(Materi materi){
            Picasso.get().load(materi.gambar).into(ivImage);
            tvTitle.setText(materi.judul);
        }
    }
}
