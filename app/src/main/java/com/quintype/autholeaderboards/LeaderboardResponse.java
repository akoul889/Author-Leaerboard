package com.quintype.autholeaderboards;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by akshaykoul on 26/05/17.
 */

public class LeaderboardResponse<T> {
    @SerializedName("result")
    @Expose
    private List<T> result = null;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
