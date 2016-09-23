package org.florescu.java8examples;

import android.app.Application;

import timber.log.Timber;

public class Java8ExamplesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            // TODO
        }
    }

}
