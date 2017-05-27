package com.quintype.autholeaderboards.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quintype.autholeaderboards.Author;
import com.quintype.autholeaderboards.AuthorResponse;
import com.quintype.autholeaderboards.AuthorResult;
import com.quintype.autholeaderboards.LeaderboardApiClient;
import com.quintype.autholeaderboards.R;

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

public class TopAuthorFragment extends Fragment {

    //    @BindView(R.id.author_recycler_view)
    RecyclerView authorRecyclerView;
    //    @BindView(R.id.fragment_title)
    TextView fragmentTitle;
    private FragmentCallbacks mListener;
    private String title;
    List<Author> authorArrayList = new ArrayList<>();
    Map<Integer,AuthorResult> resultMap = new HashMap<Integer, AuthorResult>();
    public static final String filters = "{\"start-ts\":1495564200000, \"end-ts\":1495756799000," +
            "  " +
            "\"tags\":[], \"author-ids\":[], \"sections\":[], \"assignee-ids\":[], " +
            "\"story-templates\":[], \"devices\":[], \"referrer-ids\":[]}";

    public TopAuthorFragment() {
        // Required empty public constructor
    }

    public static TopAuthorFragment newInstance(String param1, String param2) {
        TopAuthorFragment fragment = new TopAuthorFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(getString(R.string.tab_name_text));
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_author, container, false);
        fragmentTitle = (TextView) view.findViewById(R.id.fragment_title);
        authorRecyclerView = (RecyclerView) view.findViewById(R.id.author_recycler_view);
        fragmentTitle.setText(title);
        getAuthorList("view", "1", filters, "10");
        return view;
    }

    private void getAuthorList(String fact, String publisherId, String filters, String count) {
        LeaderboardApiClient.getAnalyticsApiService().topAuthorsRx(fact, publisherId, filters,
                count)
                .map(new Function<AuthorResponse, List<AuthorResult>>() {
                    @Override
                    public List<AuthorResult> apply(AuthorResponse authorResponse) throws
                            Exception {
                        return authorResponse.getResult();
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
                        resultMap.put(authorResult.getAuthorId(),authorResult);
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

                        Timber.i("onSubscribe");
                    }

                    @Override
                    public void onNext(Author value) {
                        Timber.i("onNext = %s", value.name());
                        authors.add(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("onError" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        authorArrayList = authors;
                        Timber.i("onComplete");
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallbacks) {
            mListener = (FragmentCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }


}