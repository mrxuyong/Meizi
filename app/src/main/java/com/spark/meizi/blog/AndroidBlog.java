package com.spark.meizi.blog;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Spark on 4/20/2016 18:45.
 */
public class AndroidBlog extends RealmObject{

    /**
     * error : false
     * results : [{"_id":"57962735421aa90d39e7094f","createdAt":"2016-07-25T22:50:29.136Z","desc":"Android：学习AIDL，这一篇文章就够了(下)","publishedAt":"2016-07-26T10:30:11.357Z","source":"web","type":"Android","url":"http://blog.csdn.net/luoyanglizi/article/details/52029091","used":true,"who":"lypeer"},{"_id":"5795fdbe421aa90d36e96079","createdAt":"2016-07-25T19:53:34.386Z","desc":"一个利用RxJava在TextView和EditText上渲染的markdown解析器，支持大部分语法以及部分语法在EditText上实时预览","publishedAt":"2016-07-26T10:30:11.357Z","source":"web","type":"Android","url":"https://github.com/yydcdut/RxMarkdown","used":true,"who":"yydcdut"},{"_id":"5795cd9c421aa90d39e70948","createdAt":"2016-07-25T16:28:12.741Z","desc":"遇见 LoopBar 从Cleveroad。在Android环境中导航的新方法","publishedAt":"2016-07-26T10:30:11.357Z","source":"web","type":"Android","url":"https://github.com/Cleveroad/LoopBar","used":true,"who":null},{"_id":"5795551c421aa90d39e7093b","createdAt":"2016-07-25T07:54:04.930Z","desc":"电影票在线选座","publishedAt":"2016-07-26T10:30:11.357Z","source":"chrome","type":"Android","url":"https://github.com/qifengdeqingchen/SeatTable","used":true,"who":"Jason"},{"_id":"5794f758421aa90d36e96070","createdAt":"2016-07-25T01:14:00.293Z","desc":"一个滑动动画的textview（更好的显示温度，金额等）","publishedAt":"2016-07-26T10:30:11.357Z","source":"chrome","type":"Android","url":"https://github.com/robinhood/ticker","used":true,"who":"有时放纵"}]
     */

    private boolean error;
    /**
     * _id : 57962735421aa90d39e7094f
     * createdAt : 2016-07-25T22:50:29.136Z
     * desc : Android：学习AIDL，这一篇文章就够了(下)
     * publishedAt : 2016-07-26T10:30:11.357Z
     * source : web
     * type : Android
     * url : http://blog.csdn.net/luoyanglizi/article/details/52029091
     * used : true
     * who : lypeer
     */

    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        @PrimaryKey
        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
