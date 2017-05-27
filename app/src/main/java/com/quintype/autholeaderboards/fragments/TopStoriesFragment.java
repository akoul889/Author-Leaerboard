package com.quintype.autholeaderboards.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quintype.autholeaderboards.LeaderboardApiClient;
import com.quintype.autholeaderboards.LeaderboardResponse;
import com.quintype.autholeaderboards.R;
import com.quintype.autholeaderboards.adapters.StoryAdapter;
import com.quintype.autholeaderboards.models.BulkStoryList;
import com.quintype.autholeaderboards.models.Story;
import com.quintype.autholeaderboards.models.StoryResult;
import com.quintype.autholeaderboards.utils.RecyclerItemClickListener;

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

public class TopStoriesFragment extends BaseFragment {

    RecyclerView recyclerView;
    ProgressBar progressView;
    private String title;
    List<Story> storiesArrayList = new ArrayList<>();
    StoryAdapter storyAdapter = new StoryAdapter(storiesArrayList, false);
    int maxViews = 0;
    Map<String, StoryResult> resultMap = new HashMap<>();

    public TopStoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(getString(R.string.tab_name_text));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_list, container, false);
        progressView = (ProgressBar) view.findViewById(R.id.progress_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(storyAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener
                (getActivity().getApplicationContext(), recyclerView, new
                        RecyclerItemClickListener
                                .OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
//                        currentlyPlaying = position;
//                        nowPlayingAdapter.setCurrentlyPlaying(position);
//                        nowPlayingAdapter.notifyDataSetChanged();
//                        presenter.playNewTrack(position, storageUtil);
//                                Author author = authorArrayList.get(position);
//                                AuthorResult authorResult = resultMap.get(Integer.parseInt(author
//                                        .id()));
//                                fragmentCallbacks.replaceFragment(AuthorFragment.newInstance
// (author,
//                                        authorResult), AuthorFragment.class.getSimpleName());
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                Toast.makeText(getActivity(), "Long Clicked " + storiesArrayList
                                        .get(position)
                                        .headline(), Toast.LENGTH_SHORT).show();
                            }
                        }));
        if (title.equals(getString(R.string.title_top_stories))) {
            getStoriesList("view", 1, filters, "10");
        }
        return view;
    }

    private void getStoriesList(String fact, int publisherId, String filters, String count) {
        LeaderboardApiClient.getAnalyticsApiService().topStoryRx("view", "1", filters, count)
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
                        progressView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(BulkStoryList value) {
                        progressView.setVisibility(View.GONE);
                        storyAdapter.setResultMap(resultMap);
                        storyAdapter.setMaxViews(maxViews);
                        storyAdapter.clearAll();
                        List<Story> stories = value.getStories();
                        storyAdapter.addStories(stories);
                        storyAdapter.notifyDataSetChanged();
                        Timber.i("onNext");

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressView.setVisibility(View.GONE);
                        Timber.e("onError" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("onComplete");
                        progressView.setVisibility(View.GONE);
                    }
                });
    }


}