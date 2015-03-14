package com.shinav.mathapp.story;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.approach.ApproachActivity;
import com.shinav.mathapp.progress.ProgressProvider;
import com.shinav.mathapp.question.Question;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.realm.Realm;

public class StoryActivity extends Activity {

    @InjectView(R.id.chapter_title) TextView chapterTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story);

        ButterKnife.inject(this);

        setTitleUnderline();

        // Crappy solution for now.
        ProgressProvider.setCurrentQuestion(Realm.getInstance(this).where(Question.class).findFirst());
    }

    private void setTitleUnderline() {
        chapterTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick(R.id.chapter_start_button)
    public void onChapterStartClicked() {
        startActivity(new Intent(this, ApproachActivity.class));
        overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }
}
