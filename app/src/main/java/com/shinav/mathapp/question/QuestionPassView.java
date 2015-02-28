package com.shinav.mathapp.question;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.view.FlipCard;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionPassView extends FlipCard {

    @InjectView(R.id.answer_title) TextView answerTitle;
    @InjectView(R.id.next_question_button) View nextButton;

    public QuestionPassView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_answer_pass, null, false);

        ButterKnife.inject(this, view);

        setParams(view);
        setTitleUnderline();

        addView(view);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getUIBusInstance().post(new OnNextQuestionClickedEvent());
            }
        });
    }

    private void setTitleUnderline() {
        answerTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

}
