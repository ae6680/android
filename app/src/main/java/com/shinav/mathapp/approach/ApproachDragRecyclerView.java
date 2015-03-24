package com.shinav.mathapp.approach;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.drag.DragSortRecycler;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ApproachDragRecyclerView extends RecyclerView {

    @Inject ApproachAdapter approachAdapter;

    private List<Approach> approach = Collections.emptyList();

    public ApproachDragRecyclerView(Context context) {
        super(context);
        init();
    }

    public ApproachDragRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ApproachDragRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setAdapter(approachAdapter);
        setLayoutManager(new LinearLayoutManager(this.getContext()));
        setItemAnimator(null);

        setupDragSortRecycler();
        setLayoutParams();
    }

    private void setupDragSortRecycler() {
        DragSortRecycler dragSortRecycler = new DragSortRecycler();
        dragSortRecycler.setViewHandleId(R.id.approach_list_item);
        dragSortRecycler.setFloatingBgColor(Color.parseColor("#ffffff"));
        dragSortRecycler.setOnItemMovedListener(new DragSortRecycler.OnItemMovedListener() {
            @Override public void onItemMoved(int from, int to) {
                approach.add(to, approach.remove(from));
                approachAdapter.setApproaches(approach);
            }
        });

        addItemDecoration(dragSortRecycler);
        addOnItemTouchListener(dragSortRecycler);
        setOnScrollListener(dragSortRecycler.getScrollListener());
    }

    private void setLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                MyApplication.screenWidth,
                (int) (MyApplication.screenHeight * 0.41f)
        );
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.next_question_button);
        setLayoutParams(layoutParams);
    }

    public void setApproaches(List<Approach> approach) {
        this.approach = approach;
        Collections.shuffle(approach);
        approachAdapter.setApproaches(approach);
    }
}
