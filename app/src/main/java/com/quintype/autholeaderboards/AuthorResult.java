package com.quintype.autholeaderboards;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akshaykoul on 26/05/17.
 */

public class AuthorResult implements Parcelable {

    @SerializedName("author-id")
    @Expose
    private int authorId;
    @SerializedName("count")
    @Expose
    private int count;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.authorId);
        dest.writeInt(this.count);
    }

    public AuthorResult() {
    }

    protected AuthorResult(Parcel in) {
        this.authorId = in.readInt();
        this.count = in.readInt();
    }

    public static final Parcelable.Creator<AuthorResult> CREATOR = new Parcelable
            .Creator<AuthorResult>() {
        @Override
        public AuthorResult createFromParcel(Parcel source) {
            return new AuthorResult(source);
        }

        @Override
        public AuthorResult[] newArray(int size) {
            return new AuthorResult[size];
        }
    };
}
