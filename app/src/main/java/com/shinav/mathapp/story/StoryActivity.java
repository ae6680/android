package com.shinav.mathapp.story;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.chapter.ChapterActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class StoryActivity extends Activity {

    @InjectView(R.id.chapter_title) TextView chapterTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story);

        ButterKnife.inject(this);

        setTitleUnderline();
    }

    private void setTitleUnderline() {
        chapterTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick(R.id.chapter_start_button)
    public void onChapterStartClicked() {
        startActivity(new Intent(this, ChapterActivity.class));
        overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }
}
