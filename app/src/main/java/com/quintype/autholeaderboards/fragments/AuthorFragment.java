package com.quintype.autholeaderboards.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.quintype.autholeaderboards.Author;
import com.quintype.autholeaderboards.AuthorResult;
import com.quintype.autholeaderboards.LeaderboardApiClient;
import com.quintype.autholeaderboards.LeaderboardResponse;
import com.quintype.autholeaderboards.R;
import com.quintype.autholeaderboards.adapters.StoryAdapter;
import com.quintype.autholeaderboards.models.BulkStoryList;
import com.quintype.autholeaderboards.models.Story;
import com.quintype.autholeaderboards.models.StoryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.quintype.autholeaderboards.fragments.TopAuthorFragment.filters;


public class AuthorFragment extends BaseFragment {
    private static final String ARG_AUTHOR = "author";
    private static final String ARG_AUTHOR_RESULT = "author_result";

    private Author author;
    ProgressBar progressBar;
    private AuthorResult authorResult;
    RecyclerView authorStoriesRecycler;

    List<Story> stories = new ArrayList<Story>();
    StoryAdapter storyAdapter = new StoryAdapter(stories, true);
    int maxViews = 0;
    Map<String, StoryResult> resultMap = new HashMap<String, StoryResult>();

//    TextView authorName, viewCount;

    public AuthorFragment() {
        // Required empty public constructor
    }

    public static AuthorFragment newInstance(Author author, AuthorResult authorResult) {
        AuthorFragment fragment = new AuthorFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_AUTHOR, author);
        args.putParcelable(ARG_AUTHOR_RESULT, authorResult);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            author = getArguments().getParcelable(ARG_AUTHOR);
            authorResult = getArguments().getParcelable(ARG_AUTHOR_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_author, container, false);
        authorStoriesRecycler = (RecyclerView) view.findViewById(R.id.author_stories_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_view);
        storyAdapter.setAuthor(author);
        storyAdapter.setAuthorResult(authorResult);
        authorStoriesRecycler.setAdapter(storyAdapter);
//        viewCount = (TextView) view.findViewById(R.id.view_count);
//        authorName.setText(author.name());
//        viewCount.setText(String.format(Constants.VIEV_FORMAT, authorResult.getCount()));
        getTopAuthorStories();
        return view;
    }

    private void getTopAuthorStories() {
        LeaderboardApiClient.getAnalyticsApiService().topStoryRx("view", "1", filters.replace
                ("\"author-ids\":[]", "\"author-ids\":[" + author.id() + "]"), "10")
                .flatMap(new Function<LeaderboardResponse<StoryResult>,
                        ObservableSource<BulkStoryList>>() {

                    @Override
                    public ObservableSource<BulkStoryList> apply(LeaderboardResponse<StoryResult>
                                                                         storyResultLeaderboardResponse)
                            throws Exception {
                        String ids = "";
                        for (StoryResult storyResult :
                                storyResultLeaderboardResponse.getResult()) {
                            ids += storyResult.getStoryContentId() + ",";
                            maxViews = Math.max(maxViews, storyResult.getCount());
                            resultMap.put(storyResult.getStoryContentId(), storyResult);
                        }
                        return LeaderboardApiClient.getApiService().getBulkStoryDetailsRx(ids);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BulkStoryList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Timber.i("onSubscribe");
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(BulkStoryList value) {
                        progressBar.setVisibility(View.GONE);
                        storyAdapter.setResultMap(resultMap);
                        storyAdapter.clearAll();
                        List<Story> stories = value.getStories();
                        stories.add(0, Story.INVALID_STORY);
                        storyAdapter.addStories(stories);
                        storyAdapter.notifyDataSetChanged();
                        Timber.i("onNext");

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Timber.e("onError" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        Timber.i("onComplete");
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.analytics_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_analytics) {
            fragmentCallbacks.addFragment(AuthorGraphFragment.newInstance(author, authorResult),
                    AuthorGraphFragment.class.getName());
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}
