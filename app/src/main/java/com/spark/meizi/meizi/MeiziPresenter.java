package com.spark.meizi.meizi;

import com.spark.meizi.base.BasePresenter;
import com.spark.meizi.base.FooterRecyclerAdapter;
import com.spark.meizi.meizi.entity.Meizi;
import com.spark.meizi.meizi.entity.MeiziRealmEntity;
import com.spark.meizi.net.RequestFactory;
import com.spark.meizi.net.api.GankApi;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
                        List<Meizi.ResultsBean> list = getViewRef().getAdapter().getWrapped().getData();
                        if (list != null) {
                            list.addAll(meizi.getResults());
                        } else {
                            list = meizi.getResults();
                        }
                        getViewRef().getAdapter().getWrapped().setData(list);
                        getViewRef().getAdapter().getWrapped().notifyItemRangeInserted(
                                list.size() - COUNT - 1,
                                list.size() - 1);
                        getViewRef().getAdapter().removeFooter();
                        getViewRef().setRefresh(false);
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
            list.add(temp);
        }
        return list;
    }
}

interface IMeizi {
    FooterRecyclerAdapter getAdapter();

    void setRefresh(boolean isRefresh);

}

