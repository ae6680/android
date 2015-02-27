package com.shinav.mathapp.question;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.animation.AnimationFactory;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.calculator.CalculatorFragment;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionFragment extends Fragment {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";

    private static final String QUESTION = "question";

    @InjectView(R.id.view_flipper) ViewFlipper viewFlipper;
    @InjectView(R.id.answer_flipper) ViewFlipper answerFlipper;
    @InjectView(R.id.answer_field) TextView answerField;

    @InjectView(R.id.question_container) ViewGroup questionContainer;
    @InjectView(R.id.question_title) TextView questionTitle;
    @InjectView(R.id.question) TextView questionBody;

    @InjectView(R.id.hint_container) ViewGroup hintContainer;
    @InjectView(R.id.hint_title) TextView hintTitle;

    @InjectView(R.id.submit_button) ImageButton submitButton;

    private Question question;
    private ViewGroup view;

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putSerializable(QUESTION, question);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = (Question) getArguments().getSerializable(QUESTION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_question, container, false);

        ButterKnife.inject(this, view);

        submitButton.setEnabled(false);

        hintTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        questionTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        questionTitle.setText(question.getTitle());

        questionBody.setText(question.getValue());

        questionContainer.getLayoutParams().height = calculateHeight();
        questionContainer.getLayoutParams().width = calculateWidth();

        hintContainer.getLayoutParams().height = calculateHeight();
        hintContainer.getLayoutParams().width = calculateWidth();

        initCalculator();

        return view;
    }

    private int calculateWidth() {
        return (int) Math.floor(MyApplication.screenWidth * 0.8F);
    }

    private int calculateHeight() {
        return (int) Math.floor(MyApplication.screenHeight * 0.55F);
    }

    private void initCalculator() {
        getChildFragmentManager().beginTransaction()
                .add(R.id.calculator_container, new CalculatorFragment(), CALCULATOR_FRAGMENT).commit();
    }

    @OnClick(R.id.hint)
    public void onHintClicked() {
        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT, 300);
    }

    @OnClick(R.id.hint_close)
    public void onHintCloseClicked() {
        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT, 300);
    }

    @OnClick(R.id.answer_button)
    public void onAnswerClicked() {
        AnimationFactory.flipTransition(answerFlipper, AnimationFactory.FlipDirection.BOTTOM_TOP, 300);
        submitButton.setEnabled(true);
    }

    @OnClick(R.id.submit_button)
    public void onSubmitClicked() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.question_answer_layout, this.view, false);

        View questionAnswerContainer = view.findViewById(R.id.question_answer_container);
        TextView answerTitle = (TextView) view.findViewById(R.id.answer_title);
        ImageView answerIcon = (ImageView) view.findViewById(R.id.question_answer_icon);
        TextView answerMessage = (TextView) view.findViewById(R.id.answer_message);
        View nextButton = view.findViewById(R.id.next_question_button);

        questionAnswerContainer.getLayoutParams().height = calculateHeight();
        questionAnswerContainer.getLayoutParams().width = calculateWidth();

        answerTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        if (!answerField.getText().toString().equals(question.getAnswer())) {
            answerTitle.setText("Jammer");
            answerIcon.setImageResource(R.drawable.close_icon);
            answerMessage.setText("Het goede antwoord is: \n" + question.getAnswer());
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getUIBusInstance().post(new OnNextQuestionClickedEvent());
            }
        });

        viewFlipper.addView(view, 1);

        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT, 300);
    }

    public void onAnswerChangedEvent(String answer) {
        answerField.setText(answer);
    }

}
