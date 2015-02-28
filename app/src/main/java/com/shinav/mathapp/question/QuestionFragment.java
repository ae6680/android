package com.shinav.mathapp.question;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shinav.mathapp.R;
import com.shinav.mathapp.animation.AnimationFactory;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.calculator.CalculatorFragment;
import com.shinav.mathapp.event.OnAnswerSubmittedEvent;
import com.shinav.mathapp.view.CustomViewFlipper;
import com.shinav.mathapp.view.FlipCard;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionFragment extends Fragment {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";

    private static final String QUESTION = "question";

    @InjectView(R.id.view_flipper) CustomViewFlipper viewFlipper;

    private Question question;
    private QuestionView questionView;

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
    public void onStart() {
        super.onStart();
        BusProvider.getUIBusInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getUIBusInstance().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_question, container, false);

        ButterKnife.inject(this, view);

        questionView = new QuestionView(getActivity(), question);
        viewFlipper.setFront(questionView);

        QuestionHintView questionHintView = new QuestionHintView(getActivity());
//        questionHintView.setTitle(question.getHint().getTitle());
//        questionHintView.setBody(question.getHint().getValue());

        viewFlipper.setBack(questionHintView);

        initCalculator();

        return view;
    }

    private void initCalculator() {
        getChildFragmentManager().beginTransaction()
                .add(R.id.calculator_container, new CalculatorFragment(), CALCULATOR_FRAGMENT).commit();
    }

    @Subscribe
    public void OnAnswerSubmittedEvent(OnAnswerSubmittedEvent event) {
        if (event.getQuestionKey().equals(question.getFirebaseKey())) {

            FlipCard backView;

            if (event.getAnswer().equals(question.getAnswer())) {
                backView = new QuestionPassView(getActivity());
            } else {
                QuestionFailView failView = new QuestionFailView(getActivity());
                failView.setAnswer(question.getAnswer());
                backView = failView;
            }

            viewFlipper.setBack(backView);
            AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT, 300);
        }
    }

    public void onAnswerChanged(String answer) {
        questionView.onAnswerChanged(answer);
    }

}
