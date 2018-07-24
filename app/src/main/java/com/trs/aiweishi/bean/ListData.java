package com.trs.aiweishi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.trs.aiweishi.base.BaseBean;

import java.util.List;

/**
 * Created by Liufan on 2018/7/3.
 */

public class ListData extends BaseBean implements Parcelable {
    public static final int BANNER_HEAD_TYPE = 0; //头布局
    public static final int ITEM_PIC_TYPE = 1; //图片布局类型
    public static final int ITEM_TITLE_TYPE = 2; //标题类型
    public static final int ITEM_COMMEN_TYPE = 3; //普通条目类型
    public static final int SCROOL_LINEAR_TYPE = 4; //普通条目类型
    public static final int PAGE_HEAD_TYPE = 6;

    public ListData() {
    }

    private String cid;
    private String cname;
    private String docid;
    private String title;
    private String source;
    private String author;
    private String articletype;
    private String listimgtype;
    private String body;
    private String time;
    private List<Img> images;
    private String abs;
    private String url;
    private String keywords;
    private String shareurl;
    private int drawable;
    private int channelType;
    private List<ListData> channel_list;


    protected ListData(Parcel in) {
        cid = in.readString();
        cname = in.readString();
        docid = in.readString();
        title = in.readString();
        source = in.readString();
        author = in.readString();
        articletype = in.readString();
        listimgtype = in.readString();
        body = in.readString();
        time = in.readString();
        abs = in.readString();
        url = in.readString();
        keywords = in.readString();
        drawable = in.readInt();
        channel_list = in.createTypedArrayList(CREATOR);
    }

    public static final Creator<ListData> CREATOR = new Creator<ListData>() {
        @Override
        public ListData createFromParcel(Parcel in) {
            return new ListData(in);
        }

        @Override
        public ListData[] newArray(int size) {
            return new ListData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cid);
        dest.writeString(cname);
        dest.writeString(docid);
        dest.writeString(title);
        dest.writeString(source);
        dest.writeString(author);
        dest.writeString(articletype);
        dest.writeString(listimgtype);
        dest.writeString(body);
        dest.writeString(time);
        dest.writeString(abs);
        dest.writeString(url);
        dest.writeString(keywords);
        dest.writeInt(drawable);
        dest.writeTypedList(channel_list);
    }

    public String getShareurl() {
        return shareurl;
    }

    public void setShareurl(String shareurl) {
        this.shareurl = shareurl;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Img> getImages() {
        return images;
    }

    public void setImages(List<Img> images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public List<ListData> getChannel_list() {
        return channel_list;
    }

    public void setChannel_list(List<ListData> channel_list) {
        this.channel_list = channel_list;
    }
}
