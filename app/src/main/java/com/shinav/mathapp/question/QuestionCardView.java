package com.shinav.mathapp.question;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.event.OnAnswerSubmittedEvent;
import com.shinav.mathapp.view.Card;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionCardView extends Card {

    @InjectView(R.id.question) TextView questionBody;
    @InjectView(R.id.answer_input) EditText answerField;
    @InjectView(R.id.next_question_button) TextView submitButton;

    private Question question;

    public QuestionCardView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View view = inflater.inflate(R.layout.question_card, this, false);
        ButterKnife.inject(this, view);

        setLayoutParams(view);

        answerField.addTextChangedListener(new AnswerTextWatcher());

        addView(view);
    }

    private void setLayoutParams(View view) {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                getDefaultCardHeight()
        );

        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.card_pager_margin);
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.card_pager_margin);

        view.setLayoutParams(params);
    }

    public void setQuestion(Question question) {
        this.question = question;
        setQuestionText(question.getValue());
    }

    private void setQuestionText(String value) {
        questionBody.setText(value);
    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
        String answer = answerField.getText().toString();
        OnAnswerSubmittedEvent event = new OnAnswerSubmittedEvent(question.getFirebaseKey(), answer);
        BusProvider.getUIBusInstance().post(event);
    }

    public void onAnswerChanged(String answer) {
        answerField.setText(answer);
    }

    private class AnswerTextWatcher implements TextWatcher {

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {  }

        @Override public void afterTextChanged(Editable s) {
            submitButton.setEnabled(answerField.getText().toString().length() > 0);
        }
    }

}
