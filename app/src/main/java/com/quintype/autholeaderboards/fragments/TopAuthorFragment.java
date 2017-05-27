package com.quintype.autholeaderboards.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quintype.autholeaderboards.Author;
import com.quintype.autholeaderboards.AuthorResult;
import com.quintype.autholeaderboards.LeaderboardApiClient;
import com.quintype.autholeaderboards.LeaderboardResponse;
import com.quintype.autholeaderboards.R;
import com.quintype.autholeaderboards.adapters.AuthorAdapter;
import com.quintype.autholeaderboards.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TopAuthorFragment extends BaseFragment {

    RecyclerView recyclerView;
    ProgressBar progressView;
    private String title;
    List<Author> authorArrayList = new ArrayList<>();
    AuthorAdapter authorAdapter = new AuthorAdapter(authorArrayList);
    int maxViews = 0;
    Map<Integer, AuthorResult> resultMap = new HashMap<Integer, AuthorResult>();

    public static final String filters = "{\"start-ts\":1495564200000, \"end-ts\":1495756799000," +
            "  " +
            "\"tags\":[], \"author-ids\":[], \"sections\":[], \"assignee-ids\":[], " +
            "\"story-templates\":[], \"devices\":[], \"referrer-ids\":[]}";

    public TopAuthorFragment() {
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
        recyclerView.setAdapter(authorAdapter);
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
                                Author author = authorArrayList.get(position);
                                AuthorResult authorResult = resultMap.get(Integer.parseInt(author
                                        .id()));
                                fragmentCallbacks.addFragment(AuthorFragment.newInstance(author,
                                        authorResult), AuthorFragment.class.getSimpleName());
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                Toast.makeText(getActivity(), "Long Clicked " + authorArrayList
                                        .get(position)
                                        .name(), Toast.LENGTH_SHORT).show();
                            }
                        }));
        if (title.equals(getString(R.string.title_top_authors))) {
            getAuthorList("view", "1", filters, "10");
        }
        return view;
    }

    private void getAuthorList(String fact, String publisherId, String filters, String count) {
        LeaderboardApiClient.getAnalyticsApiService().topAuthorsRx(fact, publisherId, filters,
                count)
                .map(new Function<LeaderboardResponse, List<AuthorResult>>() {
                    @Override
                    public List<AuthorResult> apply(LeaderboardResponse leaderboardResponse) throws
                            Exception {
                        return leaderboardResponse.getResult();
                    }
                })
                .flatMapIterable(new Function<List<AuthorResult>, List<AuthorResult>>() {
                    @Override
                    public List<AuthorResult> apply(List<AuthorResult> authorResults) throws
                            Exception {
                        return authorResults;
                    }
                })
                .doOnNext(new Consumer<AuthorResult>() {
                    @Override
                    public void accept(AuthorResult authorResult) throws Exception {
                        maxViews = Math.max(maxViews, authorResult.getCount());
                        resultMap.put(authorResult.getAuthorId(), authorResult);
                    }
                })
                .flatMap(new Function<AuthorResult, ObservableSource<Author>>() {
                    @Override
                    public ObservableSource<Author> apply(AuthorResult authorResult) throws
                            Exception {
                        Timber.i("Calling author %s", authorResult.getAuthorId());
                        return LeaderboardApiClient.getApiService().getAuthorRx
                                (Integer.toString(authorResult.getAuthorId())).observeOn
                                (Schedulers.io());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Author>() {
                    List<Author> authors = new ArrayList<Author>();

                    @Override
                    public void onSubscribe(Disposable d) {
                        progressView.setVisibility(View.VISIBLE);

                        Timber.i("onSubscribe");
                    }

                    @Override
                    public void onNext(Author value) {
                        Timber.i("onNext = %s", value.name());
                        authors.add(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressView.setVisibility(View.GONE);
                        Timber.e("onError" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        progressView.setVisibility(View.GONE);
                        authorArrayList = authors;
                        authorAdapter.setResultMap(resultMap);
                        authorAdapter.setMaxViews(maxViews);
                        authorAdapter.clearAll();
                        authorAdapter.addAuthors(authors);
                        authorAdapter.notifyDataSetChanged();
                        Timber.i("onComplete");
                    }
                });
    }


}