package com.spark.meizi.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.spark.meizi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailActivity extends BaseActivity {

    @Bind(R.id.iv_detail)
    ImageView detailImageView;
    private PhotoViewAttacher attacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        final String url = getIntent().getStringExtra("url");
        supportStartPostponedEnterTransition();
        ViewCompat.setTransitionName(detailImageView, url);
//        Picasso.with(this).load(url).into(detailImageView);
        Glide.with(this)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(detailImageView);

    }
    private void initalAttacher(){
        attacher = new PhotoViewAttacher(detailImageView);
    }
}
