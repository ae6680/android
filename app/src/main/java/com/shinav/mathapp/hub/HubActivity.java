package com.shinav.mathapp.hub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.shinav.mathapp.R;
import com.shinav.mathapp.story.StoryActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HubActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hub);

        ButterKnife.inject(this);

    }

    @OnClick(R.id.story)
    public void onStoryClicked() {
        startActivity(new Intent(this, StoryActivity.class));
        overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

}
