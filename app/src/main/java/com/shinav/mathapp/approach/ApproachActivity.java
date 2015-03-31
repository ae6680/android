package com.shinav.mathapp.approach;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.InjectedActionBarActivity;
import com.shinav.mathapp.injection.module.ActivityModule;
import com.shinav.mathapp.progress.Storyteller;
import com.shinav.mathapp.question.Question;
import com.shinav.mathapp.repository.RealmRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ApproachActivity extends InjectedActionBarActivity {

    @InjectView(R.id.approach_part_list) ApproachDragRecyclerView approachPartList;
    @InjectView(R.id.question_text) TextView questionText;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    @Inject Storyteller storyteller;
    @Inject RealmRepository realmRepository;

    private List<ApproachPart> approachParts = Collections.emptyList();
    private Question question;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approach);
        ButterKnife.inject(this);

        String questionKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);
        question = realmRepository.getQuestion(questionKey);

        approachParts = new ArrayList<>(question.getApproach().getApproachParts());
        approachPartList.setApproachParts(approachParts);

        questionText.setText(question.getValue());

        initToolbar();
    }

    @Override public ActivityModule getModules() {
        return new ActivityModule(this);
    }

    private void initToolbar() {
        toolbar.setTitle(question.getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
        storyteller.setCurrentApproach(approachParts);
        storyteller.next();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }

}
