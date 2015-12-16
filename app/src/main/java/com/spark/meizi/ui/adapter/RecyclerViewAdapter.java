package com.spark.meizi.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.spark.meizi.R;
import com.spark.meizi.data.model.Meizi;

import java.util.List;

/**
 * Created by Spark on 12/10/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MeiziViewHolder> {

    private Context context;
    private List<Meizi> meiziList;

    public RecyclerViewAdapter(Context context, List<Meizi> meizis) {
        this.context = context;
        meiziList = meizis;
    }
//
//    @Override
//    public MeiziViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.
//                from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
//        return new MeiziViewHolder(v);
//    }

//    @Override
//    public void onBindViewHolder(MeiziViewHolder holder, int position) {
//        MeiziViewHolder holder = (MeiziViewHolder)viewHolder;
//        Meizi meizi = getItem(position);
//        holder.textView.setText(meizi.getDesc());
//
//        Glide.with(context)
//                .load(meizi.getUrl())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.imageView);
//    }

    @Override
    public RecyclerViewAdapter.MeiziViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.
                from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new MeiziViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MeiziViewHolder holder, int position) {
        Meizi meizi = meiziList.get(position);
        holder.textView.setText(meizi.getDesc());

        Glide.with(context)
                .load(meizi.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return meiziList.size();
    }

    class MeiziViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;

        public MeiziViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.riv_item);
            textView = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }
}
