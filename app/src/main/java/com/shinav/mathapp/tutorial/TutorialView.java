package com.shinav.mathapp.tutorial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.view.ButterKnifeLayout;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

public class TutorialView extends ButterKnifeLayout {

    @InjectViews({
            R.id.main_female_1_button,
            R.id.main_female_2_button,
            R.id.main_male_1_button,
            R.id.main_male_2_button
    }) List<ImageButton> characterButtons;

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

        for (View view : characterButtons) {
            view.setSelected(true);

            view.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {

                    for (View view : characterButtons) {
                        view.setSelected(false);
                    }

                    v.setSelected(true);

                }
            });
        }

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
