package com.shinav.mathapp.injection;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.injection.modules.ActivityModule;

import dagger.ObjectGraph;

public abstract class InjectedActionBarActivity extends ActionBarActivity {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectGraph objectGraph = ((MyApplication) getApplication()).getApplicationGraph();
        objectGraph.plus(getModules()).inject(this);
    }

    public abstract ActivityModule getModules();

}
