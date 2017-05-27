package com.quintype.autholeaderboards;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;

import com.quintype.autholeaderboards.activities.FragmentActivity;
import com.quintype.autholeaderboards.adapters.HomePagerAdapter;
import com.quintype.autholeaderboards.fragments.TopAuthorFragment;

import butterknife.Unbinder;

public class MainActivity extends FragmentActivity {

//    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    @BindView(R.id.content_view_pager)
    ViewPager contentViewPager;
//    @BindView(R.id.root_coordinator_layout)
    CoordinatorLayout rootCoordinatorLayout;
//    @BindView(R.id.sections_list)
    RecyclerView sectionsList;
//    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    private Unbinder unbinder;
    ActionBar actionBar;

    HomePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        unbinder = ButterKnife.bind(this);
        toolbar =  (Toolbar)findViewById(R.id.toolbar);
        contentViewPager = (ViewPager) findViewById(R.id.content_view_pager);
        rootCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.root_coordinator_layout);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

//        actionBar.setDisplayHomeAsUpEnabled(true);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
//                .navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        setUpViewPager(contentViewPager);

    }


    private void setUpViewPager(ViewPager viewPager) {
        pagerAdapter = new HomePagerAdapter(getFragmentManager());

        Bundle topAuthorsBundle = new Bundle();
        Bundle topStoriesBundle = new Bundle();
        topAuthorsBundle.putString(getResources().getString(R.string.tab_name_text), getResources
                ().getString(R.string
                .title_top_authors));
        topStoriesBundle.putString(getResources().getString(R.string.tab_name_text), getResources
                ().getString(R.string
                .title_top_stories));
        pagerAdapter.addFragment(new TopAuthorFragment(), getResources().getString(R.string
                .title_top_authors), topAuthorsBundle);
        pagerAdapter.addFragment(new TopAuthorFragment(), getResources().getString(R.string
                .title_top_stories), topStoriesBundle);
//        pagerAdapter.addFragment(new WatchFragment(), getResources().getString(R.string
//                .title_watch), new Bundle());
//
//        Bundle dedicateBundle = new Bundle();
//        dedicateBundle.putString(getResources().getString(R.string.tab_name_text), getResources
// ().getString(R.string
//                .title_dedicate));
//        pagerAdapter.addFragment(new HomeStoriesFragment(), getResources().getString(R.string
//                .title_dedicate), dedicateBundle);
//
//        pagerAdapter.addFragment(new SoundcloudListFragment(), getResources().getString(R.string
//                .title_listen), new
//                Bundle());
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbinder.unbind();
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void clickAnalyticsEvent(String categoryId, String actionId, String labelId, long
            value) {

    }

    @Override
    public void propagateEvent(Pair<String, Object> event) {

    }
}
