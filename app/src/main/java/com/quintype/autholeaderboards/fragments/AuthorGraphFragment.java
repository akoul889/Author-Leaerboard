package com.quintype.autholeaderboards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.quintype.autholeaderboards.Author;
import com.quintype.autholeaderboards.AuthorResult;
import com.quintype.autholeaderboards.LeaderboardApiClient;
import com.quintype.autholeaderboards.LeaderboardResponse;
import com.quintype.autholeaderboards.R;
import com.quintype.autholeaderboards.models.ViewTimeResult;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AuthorGraphFragment extends BaseFragment {

    private static final String ARG_AUTHOR = "author";
    private static final String ARG_AUTHOR_RESULT = "author_result";


    public static final String filters = "{\"start-ts\":1495305000000, \"end-ts\":1495823400000," +
            "  " +
            "\"tags\":[], \"author-ids\":[], \"sections\":[], \"assignee-ids\":[], " +
            "\"story-templates\":[], \"devices\":[], \"referrer-ids\":[]}";

    private Author author;
    ProgressBar progressBar;
    private AuthorResult authorResult;

    public AuthorGraphFragment() {
        // Required empty public constructor
    }

    public static AuthorGraphFragment newInstance(Author author, AuthorResult authorResult) {
        AuthorGraphFragment fragment = new AuthorGraphFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_AUTHOR, author);
        args.putParcelable(ARG_AUTHOR_RESULT, authorResult);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            author = getArguments().getParcelable(ARG_AUTHOR);
            authorResult = getArguments().getParcelable(ARG_AUTHOR_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_author_graph, container, false);
        getLastWeeksdataForAuthor();
        return view;
    }

    private void getLastWeeksdataForAuthor() {
        LeaderboardApiClient.getAnalyticsApiService().topAuthorGraphData("view", "1", filters
                .replace
                ("\"author-ids\":[]", "\"author-ids\":[" + author.id() + "]"), "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LeaderboardResponse<ViewTimeResult>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LeaderboardResponse<ViewTimeResult> value) {
                        for (ViewTimeResult viewTimeResult :
                                value.getResult()) {
                            Timber.i("Date = %s and count is %d", toDate(viewTimeResult
                                    .getTimestamp()), viewTimeResult.getCount());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String toDate(long timestamp) {
        Date date = new Date(timestamp);
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }


}
