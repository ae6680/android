package com.shinav.mathapp.approach;

import android.os.Bundle;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.InjectedActivity;
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

public class ApproachActivity extends InjectedActivity {

    @InjectView(R.id.approach_list) ApproachDragRecyclerView approachList;
    @InjectView(R.id.question_title) TextView questionTitle;
    @InjectView(R.id.question_text) TextView questionText;

    @Inject Storyteller storyteller;
    @Inject RealmRepository realmRepository;

    private List<Approach> approach = Collections.emptyList();

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approach);
        ButterKnife.inject(this);

        String questionKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);
        Question question = realmRepository.getQuestion(questionKey);

        approach = new ArrayList<>(question.getApproach());
        approachList.setApproaches(approach);

        questionTitle.setText(question.getTitle());
        questionText.setText(question.getValue());
    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
        storyteller.setCurrentApproach(approach);
        storyteller.next();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }

}
