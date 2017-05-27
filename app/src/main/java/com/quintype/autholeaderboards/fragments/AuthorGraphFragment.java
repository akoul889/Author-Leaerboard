package com.quintype.autholeaderboards.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.quintype.autholeaderboards.Author;
import com.quintype.autholeaderboards.AuthorResult;
import com.quintype.autholeaderboards.LeaderboardApiClient;
import com.quintype.autholeaderboards.LeaderboardResponse;
import com.quintype.autholeaderboards.R;
import com.quintype.autholeaderboards.models.ViewTimeResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AuthorGraphFragment extends BaseFragment implements
        OnChartValueSelectedListener {

    private static final String ARG_AUTHOR = "author";
    private static final String ARG_AUTHOR_RESULT = "author_result";


    public static final String filters = "{\"start-ts\":1495305000000, \"end-ts\":1495823400000," +
            "  " +
            "\"tags\":[], \"author-ids\":[], \"sections\":[], \"assignee-ids\":[], " +
            "\"story-templates\":[], \"devices\":[], \"referrer-ids\":[]}";

    private Author author;
    ProgressBar progressBar;
    private AuthorResult authorResult;
    LineChart mChart;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;

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
        mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
        mChart = (LineChart) view.findViewById(R.id.chart1);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_view);
        setUpChart();
        getLastWeeksdataForAuthor();
        return view;
    }

    private void setUpChart() {
//        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        // add data
//        setData(100, 30,System.currentTimeMillis());
        mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new IAxisValueFormatter() {


            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return toDate((long) value);
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100000f);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
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
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(LeaderboardResponse<ViewTimeResult> value) {
                        for (ViewTimeResult viewTimeResult :
                                value.getResult()) {
                            Timber.i("Date = %s and count is %d", toDate(viewTimeResult
                                    .getTimestamp()), viewTimeResult.getCount());

                        }
                        progressBar.setVisibility(View.GONE);
                        setData(value.getResult());
                        mChart.invalidate();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    private String toDate(long timestamp) {
        Date date = new Date(timestamp);
        return new SimpleDateFormat("dd-MM").format(date);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        mChart.centerViewToAnimated(e.getX(), e.getY(), mChart.getData().getDataSetByIndex(h
                .getDataSetIndex())
                .getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {

    }

    private void setData(List<ViewTimeResult> results) {

        // now in hours

        ArrayList<Entry> values = new ArrayList<Entry>();
        for (ViewTimeResult viewTimeResult : results) {
            values.add(new Entry(viewTimeResult.getTimestamp(), viewTimeResult.getCount()));

        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        // create a data object with the datasets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
    }


    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }
}
