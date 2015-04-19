package com.shinav.mathapp.question.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.card.Card;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.questionApproach.QuestionApproachPartAdapter;
import com.shinav.mathapp.view.WrappedLinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

public class QuestionApproachCardView extends Card {

    @InjectView(R.id.approach_part_list) RecyclerView approachList;

    @Inject QuestionApproachPartAdapter adapter;

    @Inject
    public QuestionApproachCardView(@ForActivity Context context) {
        super(context);
        init();
    }

    public void init() {
        ComponentFactory.getViewComponent(this.getContext()).inject(this);
        inflate(R.layout.question_approach_card, this, true);
    }

    public void setApproachParts(List<QuestionApproachPart> questionApproachParts) {
        adapter.setQuestionApproachParts(questionApproachParts);

        approachList.setAdapter(adapter);
        approachList.setLayoutManager(new WrappedLinearLayoutManager(this.getContext()));
    }

}
