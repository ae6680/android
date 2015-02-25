package com.shinav.mathapp.question;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import com.shinav.mathapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionActivity extends Activity {

    @InjectView(R.id.question_fase) TextView questionFase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ButterKnife.inject(this);

        questionFase.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
}
