package com.shinav.mathapp.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.time.TimeUtils;

import javax.inject.Inject;

import static com.shinav.mathapp.MyApplication.PREF_DATA_UPDATED_AT;
import static com.shinav.mathapp.MyApplication.PREF_TUTORIAL_COMPLETED;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class UpdateChecker {

    private Context context;
    private SharedPreferences prefs;
    private TimeUtils timeUtils;

    @Inject public UpdateChecker(
            @ForActivity Context context,
            SharedPreferences prefs,
            TimeUtils timeUtils
    ) {
        this.context = context;
        this.prefs = prefs;
        this.timeUtils = timeUtils;
    }

    public boolean check() {
        boolean bool = shouldUpdate();
        if (bool) {
            updatePrefDataUpdatedAt();
        }

        return bool;
    }

    private boolean shouldUpdate() {
        return (!isTutorialCompleted() || isAtLeastDayLaterThanUpdated()) && isOnline();
    }

    private boolean isAtLeastDayLaterThanUpdated() {
        long dayOfLatestUpdate = MILLISECONDS.toDays(prefs.getLong(PREF_DATA_UPDATED_AT, 0));
        long today = MILLISECONDS.toDays(timeUtils.getCurrentTimeMillis());

        return today > dayOfLatestUpdate;
    }

    private boolean isTutorialCompleted() {
        return prefs.getBoolean(PREF_TUTORIAL_COMPLETED, false);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void updatePrefDataUpdatedAt() {
        prefs.edit().putLong(
                PREF_DATA_UPDATED_AT,
                timeUtils.getCurrentTimeMillis()
        ).apply();
    }

}
