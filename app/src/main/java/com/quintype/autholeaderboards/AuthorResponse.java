package com.quintype.autholeaderboards;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by akshaykoul on 26/05/17.
 */

public class AuthorResponse {
    @SerializedName("result")
    @Expose
    private List<AuthorResult> result = null;

    public List<AuthorResult> getResult() {
        return result;
    }

    public void setResult(List<AuthorResult> result) {
        this.result = result;
    }
}
