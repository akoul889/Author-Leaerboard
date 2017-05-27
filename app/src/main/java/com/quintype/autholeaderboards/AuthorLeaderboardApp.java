package com.quintype.autholeaderboards;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by akshaykoul on 26/05/17.
 */

public class AuthorLeaderboardApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
