package com.quintype.autholeaderboards.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class ImageMetaData implements Parcelable {

    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;
    @SerializedName("focus-point")
    private int[] focusPoints;

    @Override
    public String toString() {
        return "ImageMetaData{" +
                "width=" + width +
                ", height=" + height +
                ", focusPoints=" + Arrays.toString(focusPoints) +
                '}';
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int[] focusPoints() {
        return focusPoints;
    }


    public ImageMetaData() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeIntArray(this.focusPoints);
    }

    protected ImageMetaData(Parcel in) {
        this.width = in.readInt();
        this.height = in.readInt();
        this.focusPoints = in.createIntArray();
    }

    public static final Creator<ImageMetaData> CREATOR = new Creator<ImageMetaData>() {
        public ImageMetaData createFromParcel(Parcel source) {
            return new ImageMetaData(source);
        }

        public ImageMetaData[] newArray(int size) {
            return new ImageMetaData[size];
        }
    };
}