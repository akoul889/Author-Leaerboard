package com.quintype.autholeaderboards.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akshaykoul on 26/05/17.
 */

public class StoryResult implements Parcelable {

    @SerializedName("story-content-id")
    @Expose
    private String storyContentId;
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("count")
    @Expose
    private int count;

    public String getStoryContentId() {
        return storyContentId;
    }

    public void setStoryContentId(String storyContentId) {
        this.storyContentId = storyContentId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.storyContentId);
        dest.writeString(this.headline);
        dest.writeInt(this.count);
    }

    public StoryResult() {
    }

    protected StoryResult(Parcel in) {
        this.storyContentId = in.readString();
        this.headline = in.readString();
        this.count = in.readInt();
    }

    public static final Parcelable.Creator<StoryResult> CREATOR = new Parcelable
            .Creator<StoryResult>() {
        @Override
        public StoryResult createFromParcel(Parcel source) {
            return new StoryResult(source);
        }

        @Override
        public StoryResult[] newArray(int size) {
            return new StoryResult[size];
        }
    };
}
