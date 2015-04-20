package com.shinav.mathapp.reporting;

import android.util.Log;

import com.parse.ParseAnalytics;

import java.util.HashMap;

import timber.log.Timber;

public class ParseTree extends Timber.HollowTree {
    @Override public void e(final String message, final Object... args) {
        ParseAnalytics.trackEventInBackground("Error", new HashMap<String, String>() {{
            put("message", args.length == 0 ? message : String.format(message, args));
        }});
    }

    @Override public void e(final Throwable t, final String message, final Object... args) {
        ParseAnalytics.trackEventInBackground("Error", new HashMap<String, String>() {{
            put("message",  args.length == 0 ? message : String.format(message, args));
            put("stack trace", Log.getStackTraceString(t));
        }});
    }
}
