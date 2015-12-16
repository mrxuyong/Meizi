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

/**
 * Created by Spark on 12/10/2015.
 */
public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<Meizi> {

    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MeiziViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.
                from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new MeiziViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        MeiziViewHolder holder = (MeiziViewHolder)viewHolder;
        Meizi meizi = getItem(position);
        holder.textView.setText(meizi.getDesc());

        Glide.with(context)
                .load(meizi.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
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
