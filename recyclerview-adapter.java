package com.fildzachoirinamarhani.tugasakhirfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<AplikasiModel> mData;
    RequestOptions option;

    public RecyclerViewAdapter(Context mContext, List<AplikasiModel> mData) {
        this.mContext = mContext;
        this.mData = mData;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.list_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_namausaha.setText(mData.get(position).getNamausaha());
        holder.tv_harga.setText(mData.get(position).getHarga());
        holder.tv_nominal.setText(mData.get(position).getNominal());

        Glide.with(mContext).load(mData.get(position).getFoto()).apply(option).into(holder.img_foto);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<AplikasiModel> lstModel) {
        lstModel.addAll(lstModel);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_namausaha;
        private TextView tv_harga;
        private TextView tv_nominal;
        private ImageView img_foto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_namausaha = (TextView) itemView.findViewById(R.id.nameloc);
            tv_harga = (TextView) itemView.findViewById(R.id.price);
            tv_nominal = (TextView) itemView.findViewById(R.id.price);
            img_foto = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}
