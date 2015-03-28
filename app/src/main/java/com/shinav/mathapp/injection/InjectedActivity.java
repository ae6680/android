package com.shinav.mathapp.injection;

import android.app.Activity;
import android.os.Bundle;

import com.shinav.mathapp.MyApplication;

import dagger.ObjectGraph;

public abstract class InjectedActivity extends Activity {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectGraph objectGraph = ((MyApplication) getApplication()).getApplicationGraph();
        objectGraph.plus(getModules()).inject(this);
    }

   public abstract ActivityModule getModules();

}
