package com.spark.meizi.meizi;

import android.graphics.Bitmap;
import android.view.View;

import com.spark.meizi.R;
import com.spark.meizi.base.listener.BaseRecyclerAdapter;
import com.spark.meizi.meizi.entity.Meizi;
import com.spark.meizi.utils.ImageLoader;
import com.spark.meizi.widget.ScaleImageView;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Spark on 8/2/2016.
 * Github: github/SparkYuan
 */
public class MeiziAdapter extends BaseRecyclerAdapter<Meizi.ResultsBean, MeiziViewHolder> {

    @Override
    protected int getLayoutId() {
        return R.layout.meizi_rv_item;
    }

    @Override
    protected MeiziViewHolder createViewHolder(View view) {
        return new MeiziViewHolder(view);
    }

}

class MeiziViewHolder extends BaseRecyclerAdapter.BaseViewHolder<Meizi.ResultsBean> {

    private static final String TAG = "MeiziViewHolder";
    @BindView(R.id.iv_item)
    ScaleImageView itemImageView;

    public MeiziViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void bindData(Meizi.ResultsBean data) {
        Observable.just(data)
                .map(new Func1<Meizi.ResultsBean, Bitmap>() {
                    @Override
                    public Bitmap call(Meizi.ResultsBean resultsBean) {
                        return ImageLoader.loadImageBitmap(resultsBean.getUrl(), itemView.getContext());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        if (bitmap != null) {
                            itemImageView.setImageBitmap(bitmap);
                            itemImageView.setOriginalSize(bitmap.getWidth(), bitmap.getHeight());
                        }
                    }
                });
    }

    @OnClick(R.id.iv_item)
    public void onClick() {
    }
}