package com.shinav.mathapp.tutorial;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.view.ButterKnifeLayout;

import butterknife.InjectView;
import butterknife.OnClick;

public class TutorialView extends ButterKnifeLayout {

    @InjectView(R.id.gender_male_button) Button maleButton;
    @InjectView(R.id.gender_female_button) Button femaleButton;
    @InjectView(R.id.start_tutorial_button) TextView startButton;

    public TutorialView(Context context) {
        super(context);
        init();
    }

    public TutorialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TutorialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(R.layout.tutorial_layout, this, true);
        setVisibility(GONE);

        onMaleButtonClick();
    }

    @OnClick(R.id.gender_male_button)
    public void onMaleButtonClick() {
        maleButton.setSelected(true);
        femaleButton.setSelected(false);
    }

    @OnClick(R.id.gender_female_button)
    public void onFemaleButtonClick() {
        femaleButton.setSelected(true);
        maleButton.setSelected(false);
    }

    @OnClick(R.id.start_tutorial_button)
    public void onTutorialStartButtonClicked() {

    }

}
