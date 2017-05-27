package com.quintype.autholeaderboards;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akshaykoul on 01/04/16.
 */
public class Author implements Parcelable {

    /**
     * id : 705
     * publisher-id : 13
     * name : Radhesh Kanumury
     * twitter-handle : null
     * bio : Radhesh Kanumury leads the Global Entrepreneur Program for IBM India and South Asia.
     * avatar-url : http://d9zv4zsfqrm9s.cloudfront
     * .net/staging_swarajya/2016-02/eccf5406-4f5f-4787-a37e-b7a971393239/54e719f20385c.jpg
     * avatar-s3-key : staging_swarajya/2016-02/eccf5406-4f5f-4787-a37e-b7a971393239
     * /54e719f20385c.jpg
     * slug : radhesh-kanumury
     */


    @SerializedName("id")
    private String id;
    @SerializedName("publisher-id")
    private String publisherId;
    @SerializedName("name")
    private String name;
    @SerializedName("twitter-handle")
    private String twitterHandle;
    @SerializedName("bio")
    private String bio;
    @SerializedName("avatar-url")
    private String avatarUrl;
    @SerializedName("avatar-s3-key")
    private String avatarS3Key;
    @SerializedName("slug")
    private String slug;
    @SerializedName("email")
    private String email;

    private Author(Builder builder) {
        id = builder.id;
        publisherId = builder.publisherId;
        name = builder.name;
        twitterHandle = builder.twitterHandle;
        bio = builder.bio;
        avatarUrl = builder.avatarUrl;
        avatarS3Key = builder.avatarS3Key;
        slug = builder.slug;
        email = builder.email;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", twitterHandle=" + twitterHandle +
                ", bio=" + bio +
                ", avatarUrl=" + avatarUrl +
                ", avatarS3Key=" + avatarS3Key +
                ", slug=" + slug +
                ", email='" + email + '\'' +
                ", publisherId='" + publisherId + '\'' +
                '}';
    }

    public String id() {
        return id;
    }

    public String publisherId() {
        return publisherId;
    }

    public String name() {
        return name;
    }

    public String twitterHandle() {
        return twitterHandle;
    }

    public String bio() {
        return bio;
    }

    public String avatarUrl() {
        return avatarUrl;
    }

    public String avatarS3Key() {
        return avatarS3Key;
    }

    public String slug() {
        return slug;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.publisherId);
        dest.writeString(this.name);
        dest.writeString(this.twitterHandle);
        dest.writeString(this.bio);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.avatarS3Key);
        dest.writeString(this.slug);
        dest.writeString(this.email);
    }

    public Author() {
    }

    protected Author(Parcel in) {
        this.id = in.readString();
        this.publisherId = in.readString();
        this.name = in.readString();
        this.twitterHandle = in.readString();
        this.bio = in.readString();
        this.avatarUrl = in.readString();
        this.avatarS3Key = in.readString();
        this.slug = in.readString();
        this.email = in.readString();
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel source) {
            return new Author(source);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };


    public static final class Builder {
        private String id;
        private String publisherId;
        private String name;
        private String twitterHandle;
        private String bio;
        private String avatarUrl;
        private String avatarS3Key;
        private String slug;
        private String email;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder publisherId(String val) {
            publisherId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder twitterHandle(String val) {
            twitterHandle = val;
            return this;
        }

        public Builder bio(String val) {
            bio = val;
            return this;
        }

        public Builder avatarUrl(String val) {
            avatarUrl = val;
            return this;
        }

        public Builder avatarS3Key(String val) {
            avatarS3Key = val;
            return this;
        }

        public Builder slug(String val) {
            slug = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Author build() {
            return new Author(this);
        }
    }
}
