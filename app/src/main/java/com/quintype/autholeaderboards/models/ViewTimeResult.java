package com.quintype.autholeaderboards.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akshaykoul on 27/05/17.
 */

public class ViewTimeResult {
    @SerializedName("timestamp")
    @Expose
    private long timestamp;
    @SerializedName("count")
    @Expose
    private long count;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
