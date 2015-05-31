package com.shinav.mathapp.time;

import javax.inject.Inject;

public class TimeUtils {
    @Inject public TimeUtils() { }

    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
