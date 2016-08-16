package com.spark.meizi.meizi;

import android.graphics.Bitmap;

import com.spark.meizi.MeiziContext;
import com.spark.meizi.base.BasePresenter;
import com.spark.meizi.base.FooterRecyclerAdapter;
import com.spark.meizi.base.listener.BaseRecyclerAdapter;
import com.spark.meizi.meizi.entity.Meizi;
import com.spark.meizi.meizi.entity.MeiziRealmEntity;
import com.spark.meizi.net.RequestFactory;
import com.spark.meizi.net.api.GankApi;
import com.spark.meizi.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by SparkYuan on 7/28/2016.
 * Github: github.com/SparkYuan
 */
public class MeiziPresenter extends BasePresenter<IMeizi> {
    private Realm realm;
    private GankApi gankApi;
    private final int COUNT = 10;
    public static final String MEIZIS = "meizis";
    public static final String INDEX = "index";

    public MeiziPresenter(IMeizi view) {
        super(view);
        realm = Realm.getDefaultInstance();
        gankApi = RequestFactory.createApi(GankApi.class);
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public void requestMeizi(int page) {
        getViewRef().getAdapter().addFooter();
        gankApi.latest(COUNT, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Meizi>() {
                    @Override
                    public void call(Meizi meizi) {

                        // 加载图片缓存，并保存尺寸数据到meizi
                        Observable.just(meizi)
                                .map(new Func1<Meizi, Meizi>() {
                                    @Override
                                    public Meizi call(Meizi meizi) {
                                        for (final Meizi.ResultsBean bean : meizi.getResults()) {
                                            Bitmap bitmap = ImageLoader.loadImageBitmap(bean.getUrl(),
                                                    MeiziContext.getInstance().getContext());
                                            if (bitmap != null) {
                                                bean.setWidth(bitmap.getWidth());
                                                bean.setHeight(bitmap.getHeight());
                                            }
                                        }
                                        return meizi;
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<Meizi>() {
                                    @Override
                                    public void call(Meizi meizi) {
                                        //只能在创建的线程使用
                                        for (final Meizi.ResultsBean bean : meizi.getResults()) {
                                            realm.beginTransaction();
                                            realm.copyToRealmOrUpdate(convert2Realm(bean));
                                            realm.commitTransaction();
                                        }

                                        BaseRecyclerAdapter adapter = getViewRef().getAdapter().getWrapped();
                                        List<Meizi.ResultsBean> list = adapter.getData();
                                        if (list != null) {
                                            list.addAll(meizi.getResults());
                                        } else {
                                            list = meizi.getResults();
                                        }
                                        adapter.setData(list);
                                        getViewRef().getAdapter().getWrapped().notifyItemRangeInserted(
                                                list.size() - COUNT - 1,
                                                list.size() - 1);
                                        getViewRef().getAdapter().removeFooter();
                                        getViewRef().setRefresh(false);
                                    }
                                });
                    }

                });
    }

    public List<Meizi.ResultsBean> loadDataFromDB() {
        List<Meizi.ResultsBean> list = new ArrayList<>();
        RealmResults<MeiziRealmEntity> results = realm.where(MeiziRealmEntity.class)
                .findAllSorted("publishedAt", Sort.DESCENDING);
        for (MeiziRealmEntity entity : results) {
            Meizi.ResultsBean temp = new Meizi.ResultsBean();
            temp.setUrl(entity.getUrl());
            temp.setHeight(entity.getHeight());
            temp.setWidth(entity.getWidth());
            temp.setPublishedAt(entity.getPublishedAt());
            list.add(temp);
        }
        if (list.size() >= 30) {
            return list.subList(0, 29);
        } else {
            return list;
        }
    }

    private MeiziRealmEntity convert2Realm(Meizi.ResultsBean resultsBean) {
        MeiziRealmEntity realmEntity = new MeiziRealmEntity();
        realmEntity.set_id(resultsBean.get_id());
        realmEntity.setCreatedAt(resultsBean.getCreatedAt());
        realmEntity.setDesc(resultsBean.getDesc());
        realmEntity.setPublishedAt(resultsBean.getPublishedAt());
        realmEntity.setUrl(resultsBean.getUrl());
        realmEntity.setWidth(resultsBean.getWidth());
        realmEntity.setHeight(resultsBean.getHeight());
        return realmEntity;
    }
}

interface IMeizi {
    FooterRecyclerAdapter getAdapter();

    void setRefresh(boolean isRefresh);

}

