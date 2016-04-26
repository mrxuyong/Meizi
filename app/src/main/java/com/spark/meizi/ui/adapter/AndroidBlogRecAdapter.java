package com.spark.meizi.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spark.meizi.R;
import com.spark.meizi.data.model.AndroidBlog;
import com.spark.meizi.ui.activity.WebActivity;

import java.util.List;

/**
 * Created by Spark on 4/25/2016 20:50.
 */
public class AndroidBlogRecAdapter extends RecyclerView.Adapter<AndroidBlogViewHolder> {

    private List<AndroidBlog> blogs;
    Context context;

    public AndroidBlogRecAdapter(List<AndroidBlog> blogs, Context context) {
        super();
        this.blogs = blogs;
        this.context = context;
    }

    @Override
    public AndroidBlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.android_blog_item, parent, false);

        return new AndroidBlogViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(AndroidBlogViewHolder holder, final int position) {
        holder.title.setText(blogs.get(position).getDesc() + " (By:" + blogs.get(position).getWho() + ")");
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", blogs.get(position).getUrl());
                intent.putExtra("title", blogs.get(position).getDesc());
                context.startActivity(intent);
            }
        });
    }

}

class AndroidBlogViewHolder extends RecyclerView.ViewHolder {

    TextView title;

    public AndroidBlogViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.tv_android_blog_title);
    }
}
