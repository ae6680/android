package com.shinav.mathapp.tutorial;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.view.ButterKnifeLayout;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

public class TutorialView extends ButterKnifeLayout {

    @InjectView(R.id.gender_male_button) Button maleButton;
    @InjectView(R.id.gender_female_button) Button femaleButton;
    @InjectView(R.id.start_tutorial_button) TextView startButton;

    @Inject Bus bus;

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
        ComponentFactory.getViewComponent(this.getContext()).inject(this);

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
//        String perspective = null;

//        if (maleButton.isSelected()) {
//            perspective = TutorialManagingService.PERSPECTIVE_MALE;

            // Hack for now because we have only female perspective content.
//            perspective = TutorialManagingService.PERSPECTIVE_FEMALE;
//        } else if (femaleButton.isSelected()) {
//            perspective = TutorialManagingService.PERSPECTIVE_FEMALE;
//        }

//        bus.post(new TutorialStartButtonClicked(perspective));
    }

}
