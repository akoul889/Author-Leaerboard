package com.quintype.autholeaderboards.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BulkStoryList {

    @SerializedName("stories")
    private List<Story> stories = new ArrayList<>();

    public List<Story> getStories() {
        return stories;
    }

    @Override
    public String toString() {
        return stories.toString();
    }
}
