package com.shinav.mathapp.approach;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.view.DragSortRecycler;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ApproachDragRecyclerView extends RecyclerView {

    @Inject ApproachEntryAdapter approachEntryAdapter;

    private List<ApproachEntry> approachEntry = Collections.emptyList();

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
        setAdapter(approachEntryAdapter);
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
                approachEntry.add(to, approachEntry.remove(from));
                approachEntryAdapter.setApproachEntries(approachEntry);
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

    public void setApproaches(List<ApproachEntry> approachEntry) {
        this.approachEntry = approachEntry;
        Collections.shuffle(approachEntry);
        approachEntryAdapter.setApproachEntries(approachEntry);
    }
}
