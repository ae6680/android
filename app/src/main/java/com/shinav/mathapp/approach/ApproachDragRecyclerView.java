package com.shinav.mathapp.approach;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.view.DragSortRecycler;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ApproachDragRecyclerView extends RecyclerView {

    @Inject ApproachPartAdapter approachPartAdapter;

    private List<ApproachPart> approachParts = Collections.emptyList();

    public ApproachDragRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public ApproachDragRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ApproachDragRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        ComponentFactory.getViewComponent(context).inject(this);

        setAdapter(approachPartAdapter);
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
                approachParts.add(to, approachParts.remove(from));
                approachPartAdapter.setApproachParts(approachParts);
            }
        });

        addItemDecoration(dragSortRecycler);
        addOnItemTouchListener(dragSortRecycler);
        setOnScrollListener(dragSortRecycler.getScrollListener());
    }

    public void setApproachParts(List<ApproachPart> approachParts) {
        this.approachParts = approachParts;
        Collections.shuffle(approachParts);
        approachPartAdapter.setApproachParts(approachParts);
    }

}
