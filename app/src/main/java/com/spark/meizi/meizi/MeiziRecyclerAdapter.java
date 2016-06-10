package com.spark.meizi.meizi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.spark.meizi.R;
import com.spark.meizi.widget.RatioImageView;

import java.util.List;

/**
 * Created by Spark on 12/16/2015.
 */
public class MeiziRecyclerAdapter extends RecyclerView.Adapter<MeiziRecyclerAdapter.MeiziViewHolder> {

    private Context context;
    private List<Meizi> meiziList;
    private OnMeiziClickListener onMeiziClickListener;

    public MeiziRecyclerAdapter(Context context, List<Meizi> meizis) {
        super();
        this.context = context;
        meiziList = meizis;
    }

    @Override
    public MeiziViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.
                from(parent.getContext()).inflate(R.layout.rv_item, parent,
                false);
        return new MeiziViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MeiziViewHolder holder, int position) {
        Meizi meizi = meiziList.get(position);

        Glide.with(context)
                .load(meizi.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        if (meizi.getWidth() != 0 && meizi.getHeight() != 0) {
            holder.imageView.setOriginalSize(meizi.getWidth(), meizi.getHeight());
        }

    }

    @Override
    public int getItemCount() {
        return meiziList.size();
    }

    class MeiziViewHolder extends RecyclerView.ViewHolder {

        public RatioImageView imageView;

        public MeiziViewHolder(View itemView) {
            super(itemView);
            imageView = (RatioImageView) itemView.findViewById(R.id.riv_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMeiziClickListener.onMeiziClick(v, getAdapterPosition());
                }
            });
        }
    }

    public void setOnMeiziClickListener(OnMeiziClickListener listener) {
        this.onMeiziClickListener = listener;
    }
}
