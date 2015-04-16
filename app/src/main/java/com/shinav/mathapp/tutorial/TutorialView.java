package com.shinav.mathapp.tutorial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.event.TutorialStartButtonClicked;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.view.ButterKnifeLayout;
import com.squareup.otto.Bus;

import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

public class TutorialView extends ButterKnifeLayout {

    private final String TAG_MAIN_FEMALE_1 = "main_female_1";
    private final String TAG_MAIN_FEMALE_2 = "main_female_2";
    private final String TAG_MAIN_MALE_1 = "main_male_1";
    private final String TAG_MAIN_MALE_2 = "main_male_2";

    @InjectViews({
            R.id.main_female_1_button,
            R.id.main_female_2_button,
            R.id.main_male_1_button,
            R.id.main_male_2_button
    }) List<ImageButton> characterButtons;

    @InjectView(R.id.start_tutorial_button) TextView startButton;

    @Inject Bus bus;

    private View selectedView;

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
                    TutorialView.this.selectedView = v;
                }
            });
        }

    }

    @OnClick(R.id.start_tutorial_button)
    public void onTutorialStartButtonClicked() {

        if (selectedView != null) {

            String tag = (String) selectedView.getTag();

            int resourceId = 0;
            switch (tag) {
                case TAG_MAIN_FEMALE_1:
                    resourceId = R.drawable.main_female_1_profile;
                    break;
                case TAG_MAIN_FEMALE_2:
                    resourceId = R.drawable.main_female_2_profile;
                    break;
                case TAG_MAIN_MALE_1:
                    resourceId = R.drawable.main_male_1_profile;
                    break;
                case TAG_MAIN_MALE_2:
                    resourceId = R.drawable.main_male_2_profile;
                    break;
            }

//            int resourceId = getResId(tag, Drawable.class);

            bus.post(new TutorialStartButtonClicked(resourceId));
        }

    }

    public int getResId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

}
