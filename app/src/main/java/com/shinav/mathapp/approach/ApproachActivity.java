package com.shinav.mathapp.approach;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.mapper.ApproachMapper;
import com.shinav.mathapp.db.mapper.ApproachPartListMapper;
import com.shinav.mathapp.db.mapper.QuestionMapper;
import com.shinav.mathapp.db.model.Approach;
import com.shinav.mathapp.db.model.ApproachPart;
import com.shinav.mathapp.db.model.Question;
import com.shinav.mathapp.injection.InjectedActionBarActivity;
import com.shinav.mathapp.injection.module.ActivityModule;
import com.shinav.mathapp.progress.Storyteller;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ApproachActivity extends InjectedActionBarActivity {

    @InjectView(R.id.approach_part_list) ApproachDragRecyclerView approachPartList;
    @InjectView(R.id.question_text) TextView questionText;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    @Inject Storyteller storyteller;
    @Inject SqlBrite db;

    private List<ApproachPart> approachParts = Collections.emptyList();

    private Subscription questionSubscription;
    private Subscription approachSubscription;
    private Subscription approachPartSubscription;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approach);
        ButterKnife.inject(this);
    }

    @Override public ActivityModule getModules() {
        return new ActivityModule(this);
    }

    @Override protected void onResume() {
        super.onResume();

        String questionKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);

        questionSubscription = db.createQuery(
                Tables.Question.TABLE_NAME,
                "SELECT * FROM " + Tables.Question.TABLE_NAME +
                        " WHERE " + Tables.Question.KEY + " = ?"
        )
                .map(new QuestionMapper())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Question>() {
                    @Override public void call(Question question) {
                        questionText.setText(question.getValue());
                        initToolbar(question.getTitle());
                    }
                });

        approachSubscription = db.createQuery(
                Tables.Approach.TABLE_NAME,
                "SELECT * FROM " + Tables.Approach.TABLE_NAME +
                        " WHERE " + Tables.Approach.QUESTION_KEY + " = ?"
                , questionKey
        )
                .map(new ApproachMapper())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Approach>() {
                    @Override public void call(Approach approach) {

                        approachPartSubscription = db.createQuery(
                                Tables.ApproachPart.TABLE_NAME,
                                "SELECT * FROM " + Tables.ApproachPart.TABLE_NAME +
                                        " WHERE " + Tables.ApproachPart.APPROACH_KEY + " = ?"
                                , approach.getKey()
                        )
                                .map(new ApproachPartListMapper())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<List<ApproachPart>>() {
                                    @Override public void call(List<ApproachPart> approachParts) {
                                        approachPartList.setApproachParts(approachParts);
                                    }
                                });

                    }
                });
    }

    @Override protected void onPause() {
        super.onPause();
        questionSubscription.unsubscribe();
        approachSubscription.unsubscribe();
        approachPartSubscription.unsubscribe();
    }

    private void initToolbar(String title) {
        toolbar.setTitle(title);
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
