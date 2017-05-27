package com.quintype.autholeaderboards;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;

import com.quintype.autholeaderboards.activities.FragmentActivity;
import com.quintype.autholeaderboards.adapters.HomePagerAdapter;
import com.quintype.autholeaderboards.fragments.HomeFragment;

import butterknife.Unbinder;

public class MainActivity extends FragmentActivity {

    //    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.content_view_pager)
//    @BindView(R.id.root_coordinator_layout)
    CoordinatorLayout rootCoordinatorLayout;
    //    @BindView(R.id.sections_list)
    RecyclerView sectionsList;
    //    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    private Unbinder unbinder;
    ActionBar actionBar;

    HomePagerAdapter pagerAdapter;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
//        unbinder = ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rootCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.root_coordinator_layout);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        fragmentManager = getFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        setupHomeScreen();
//        actionBar.setDisplayHomeAsUpEnabled(true);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
//                .navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();


    }

    private void setupHomeScreen() {
        //if the backstack is empty/the app has just been launched
        if (getmFragment() == null) {
            addFragment(HomeFragment.newInstance(), null);
        } else {
            //clearing the intent data; if the user had opened a notification story and then
            //clicks on Home, the story will be loaded again in the next "if" block if the intent
            //data remains.
            // if the current fragment is not the home fragment, pop all fragments
            // till you reach the home fragment
            for (int i = fragmentManager.getBackStackEntryCount(); i > 0; i--) {
                fragmentManager.popBackStack();
            }
        }
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
