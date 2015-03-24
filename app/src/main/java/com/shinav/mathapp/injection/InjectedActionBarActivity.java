package com.shinav.mathapp.injection;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.shinav.mathapp.MyApplication;

public class InjectedActionBarActivity extends ActionBarActivity {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApplication) getApplication()).inject(this);
    }
}
