package com.shinav.mathapp.reporting;

import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

public class CrashlyticsTree extends Timber.HollowTree {

    @Override public void e(final String message, final Object... args) {
        Crashlytics.log(message);
    }

    @Override public void e(final Throwable t, final String message, final Object... args) {
        Crashlytics.log(message);
    }

}

