package com.shinav.mathapp.questionApproachFeedback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.view.ButterKnifeLayout;
import com.shinav.mathapp.view.WrappedLinearLayoutManager;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

public class QAFViewPagerPage extends ButterKnifeLayout {

    @InjectView(R.id.qaf_recycler_view) RecyclerView recyclerView;

    @Inject QAFViewPagerPageAdapter adapter;

    public QAFViewPagerPage(Context context) {
        super(context);
        init();
    }

    public QAFViewPagerPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QAFViewPagerPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Injector.getViewComponent(this.getContext()).inject(this);
        inflate(R.layout.question_approach_feedback_view_pager_page, this, true);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new WrappedLinearLayoutManager(this.getContext()));
    }

    public void setParts(QuestionApproachPart givenPart, QuestionApproachPart correctPart) {

        List<QuestionApproachPart> parts = Arrays.asList(
                givenPart,
                correctPart
        );

        adapter.setQuestionApproachParts(parts);
    }
}
