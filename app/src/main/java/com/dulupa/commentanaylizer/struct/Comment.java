package com.dulupa.commentanaylizer.struct;

import android.os.Parcel;
import android.os.Parcelable;

import com.dulupa.commentanaylizer.converter.HtmlParcer;
import com.dulupa.commentanaylizer.converter.ParcleUtil;

public class Comment implements Parcelable {
    String author;
    String author_img_url;
    String content;
    int like_count;
    String publish_data;
    String id;
    int reply_count;

    //轉回原本Comment
    public static final Parcelable.Creator<Comment> CREATOR
            = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel in) {
            return new Comment( ParcleUtil.readString(in),
                                ParcleUtil.readString(in),
                                ParcleUtil.readString(in),
                                ParcleUtil.readInteger(in),
                                ParcleUtil.readString(in),
                                ParcleUtil.readString(in),
                                ParcleUtil.readInteger(in));
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    //打包成parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(author_img_url);
        dest.writeString(content);
        dest.writeInt(like_count);
        dest.writeString(publish_data);
        dest.writeString(id);
        dest.writeInt(reply_count);
    }
    //parcel是否包含file descriptor
    @Override
    public int describeContents() {
        return 0;
    }



    public Comment(String author, String author_img_url, String content, int like_count, String publish_data, String id, int reply_count) {
        this.author = author;
        this.author_img_url = author_img_url;
        this.content = HtmlParcer.parce(content) ;
        this.like_count = like_count;
        this.publish_data = publish_data;
        this.id = id;
        this.reply_count = reply_count;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_img_url() {
        return author_img_url;
    }

    public void setAuthor_img_url(String author_img_url) {
        this.author_img_url = author_img_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getPublish_data() {
        return publish_data;
    }

    public void setPublish_data(String publish_data) {
        this.publish_data = publish_data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }



}
