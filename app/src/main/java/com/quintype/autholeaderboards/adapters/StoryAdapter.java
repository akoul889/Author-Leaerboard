package com.quintype.autholeaderboards.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quintype.autholeaderboards.Author;
import com.quintype.autholeaderboards.AuthorResult;
import com.quintype.autholeaderboards.R;
import com.quintype.autholeaderboards.holders.AuthorHolder;
import com.quintype.autholeaderboards.holders.StoryHolder;
import com.quintype.autholeaderboards.models.Story;
import com.quintype.autholeaderboards.models.StoryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int maxViews = 0;
    int VIEW_TYPE_HEADER = 0;
    int VIEW_TYPE_ITEM = 1;
    List<Story> storyList = new ArrayList<>();
    Map<String, StoryResult> resultMap = new HashMap<String, StoryResult>();
    Author author;
    AuthorResult authorResult;
    boolean isAuthorStories;

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setAuthorResult(AuthorResult authorResult) {
        this.authorResult = authorResult;
    }

    public StoryAdapter(List<Story> storyList, boolean isAuthorStories) {
        this.storyList = storyList;
        this.isAuthorStories = isAuthorStories;
    }

    public void setResultMap(Map<String, StoryResult> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                            .layout_author_header,
                    parent, false);
            return AuthorHolder.create(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                            .layout_story_list_item,
                    parent, false);
            return StoryHolder.create(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof StoryHolder) {
            StoryResult storyResult = resultMap.get(storyList.get(position).contentId());
            ((StoryHolder) holder).bind(storyList.get(position), storyResult,
                    isAuthorStories ? authorResult
                            .getCount() : maxViews);
            if (isAuthorStories) {
                ((StoryHolder) holder).showMedal(position == 1);
            } else {
                ((StoryHolder) holder).showMedal(position == 0);
            }
        } else if (holder instanceof AuthorHolder) {
            ((AuthorHolder) holder).bind(author, authorResult, authorResult.getCount());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && isAuthorStories) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public void addStories(List<Story> newStories) {
        int start = storyList.size();
        storyList.addAll(newStories);
        int end = storyList.size();
        notifyItemRangeInserted(start, end);
    }

    public void setMaxViews(int maxViews) {
        this.maxViews = maxViews;
    }

    public void clearAll() {
        storyList.clear();
        notifyDataSetChanged();
    }
}

