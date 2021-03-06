package com.shinav.mathapp.question.card;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.card.Card;
import com.shinav.mathapp.event.AnswerFieldClickedEvent;
import com.shinav.mathapp.event.AnswerSubmittedEvent;
import com.shinav.mathapp.event.NumpadOperationClickedEvent;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionCardView extends Card {

    @InjectView(R.id.question) TextView questionBody;
    @InjectView(R.id.answer_input) EditText answerField;
    @InjectView(R.id.submit_button) TextView submitButton;

    private final Bus bus;

    @Inject
    public QuestionCardView(@ForActivity Context context, Bus bus) {
        super(context);
        this.bus = bus;
        init();
    }

    private void init() {
        inflate(R.layout.question_card, this, true);

        submitButton.setEnabled(false);
        answerField.addTextChangedListener(new AnswerTextWatcher());

        answerField.setOnTouchListener(new OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                bus.post(new AnswerFieldClickedEvent());
                return true;
            }
        });
    }

    public void setQuestionValue(String questionValue) {
        setQuestionText(questionValue);
    }

    private void setQuestionText(String value) {
        questionBody.setText(value);
        questionBody.setMovementMethod(new ScrollingMovementMethod());
    }

    @OnClick(R.id.submit_button)
    public void onSubmitClicked() {
        String answer = answerField.getText().toString();
        bus.post(new AnswerSubmittedEvent(answer));
    }

    public void onCalculatorNumpadClicked(NumpadOperationClickedEvent event) {
        String value = event.getValue();
        int cursorPosition = answerField.getSelectionStart();
        String currentText = answerField.getText().toString();

        StringBuilder sb = new StringBuilder(currentText);

        switch (event.getOperation()) {

            case NumpadOperationClickedEvent.OPERATION_INSERT:
                sb.insert(cursorPosition, value);
                answerField.setText(sb.toString());
                answerField.setSelection(cursorPosition + 1);
                break;

            case NumpadOperationClickedEvent.OPERATION_BACKSPACE:
                if (cursorPosition != 0) {
                    sb.deleteCharAt(cursorPosition-1);
                    answerField.setText(sb.toString());
                    answerField.setSelection(cursorPosition-1);
                }
                break;

            case NumpadOperationClickedEvent.OPERATION_REMOVE_ALL:
                answerField.setText("");
        }
    }

    public void releaseFocus() {
        answerField.clearFocus();
    }

    public void setAnswerFieldEnabled(boolean enabled) {
        answerField.setEnabled(enabled);
    }

    public void setSubmitButtonEnabled(boolean enabled) {
        submitButton.setEnabled(enabled);
    }

    private class AnswerTextWatcher implements TextWatcher {

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {  }

        @Override public void afterTextChanged(Editable s) {
            submitButton.setEnabled(answerField.getText().toString().length() > 0);
        }
    }

}
