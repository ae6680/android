package com.shinav.mathapp.questionApproach;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.view.DragSortRecycler;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class QuestionApproachDragRecyclerView extends RecyclerView {

    @Inject QuestionApproachPartAdapter questionApproachPartAdapter;

    private List<QuestionApproachPart> questionApproachParts = Collections.emptyList();

    public QuestionApproachDragRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public QuestionApproachDragRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuestionApproachDragRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        ComponentFactory.getViewComponent(context).inject(this);

        setAdapter(questionApproachPartAdapter);
        setLayoutManager(new LinearLayoutManager(this.getContext()));
        setItemAnimator(null);

        setupDragSortRecycler();
    }

    private void setupDragSortRecycler() {
        DragSortRecycler dragSortRecycler = new DragSortRecycler();
        dragSortRecycler.setViewHandleId(R.id.approach_list_item);
        dragSortRecycler.setFloatingBgColor(Color.parseColor("#ffffff"));
        dragSortRecycler.setOnItemMovedListener(new DragSortRecycler.OnItemMovedListener() {
            @Override public void onItemMoved(int from, int to) {
                questionApproachParts.add(to, questionApproachParts.remove(from));
                questionApproachPartAdapter.setQuestionApproachParts(questionApproachParts);
            }
        });

        addItemDecoration(dragSortRecycler);
        addOnItemTouchListener(dragSortRecycler);
        setOnScrollListener(dragSortRecycler.getScrollListener());
    }

    public void setQuestionApproachParts(List<QuestionApproachPart> questionApproachParts) {
        this.questionApproachParts = questionApproachParts;
        Collections.shuffle(questionApproachParts);
        questionApproachPartAdapter.setQuestionApproachParts(questionApproachParts);
    }

}
