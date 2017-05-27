package com.quintype.autholeaderboards.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quintype.autholeaderboards.Author;
import com.quintype.autholeaderboards.AuthorResult;
import com.quintype.autholeaderboards.R;
import com.quintype.autholeaderboards.holders.AuthorHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int maxViews = 0;
    List<Author> authorList = new ArrayList<>();
    Map<Integer, AuthorResult> resultMap = new HashMap<Integer, AuthorResult>();

    public AuthorAdapter(List<Author> authorList) {
        this.authorList = authorList;
    }

    public void setResultMap(Map<Integer, AuthorResult> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .layout_author_list_item,
                parent, false);
        return AuthorHolder.create(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AuthorHolder) {
            AuthorResult authorResult = resultMap.get(Integer.parseInt(authorList.get(position).id
                    ()));
            ((AuthorHolder) holder).bind(authorList.get(position), authorResult,maxViews);
        }
    }

    @Override
    public int getItemCount() {
        return authorList.size();
    }

    public void addAuthors(List<Author> newAuthors) {
        int start = authorList.size();
        authorList.addAll(newAuthors);
        int end = authorList.size();
        notifyItemRangeInserted(start, end);
    }

    public void setMaxViews(int count){
        maxViews = count;
    }

    public void clearAll() {
        authorList.clear();
        notifyDataSetChanged();
    }
}

