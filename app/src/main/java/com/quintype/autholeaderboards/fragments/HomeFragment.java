package com.quintype.autholeaderboards.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quintype.autholeaderboards.R;
import com.quintype.autholeaderboards.adapters.HomePagerAdapter;

public class HomeFragment extends BaseFragment {
    public static final String TAG = HomeFragment.class.getSimpleName();
    ViewPager viewPager;
    TabLayout tabLayout;
    HomePagerAdapter pagerAdapter;

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_fragment, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.home_pager_container);

        setUpViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.home_tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {
        pagerAdapter = new HomePagerAdapter(getChildFragmentManager());

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
        pagerAdapter.addFragment(new TopStoriesFragment(), getResources().getString(R.string
                .title_top_stories), topStoriesBundle);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        pagerAdapter = null;
    }

}