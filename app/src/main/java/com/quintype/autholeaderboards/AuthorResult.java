package com.quintype.autholeaderboards;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akshaykoul on 26/05/17.
 */

public class AuthorResult {

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
}
