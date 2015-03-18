package com.shinav.mathapp.injection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.shinav.mathapp.MyApplication;

public class InjectedFragment extends Fragment {
    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MyApplication) getActivity().getApplication()).inject(this);
    }
}
