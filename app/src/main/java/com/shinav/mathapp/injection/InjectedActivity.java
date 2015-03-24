package com.shinav.mathapp.injection;

import android.app.Activity;
import android.os.Bundle;

import com.shinav.mathapp.MyApplication;

public class InjectedActivity extends Activity {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApplication) getApplication()).inject(this);
    }
}
