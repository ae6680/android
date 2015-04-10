package com.shinav.mathapp.conversation;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.shinav.mathapp.db.pojo.ConversationLine;
import com.shinav.mathapp.injection.component.ComponentFactory;

import javax.inject.Inject;

public class ConversationLineRecyclerView extends RecyclerView {

    @Inject ConversationLineAdapter conversationLineAdapter;

    public ConversationLineRecyclerView(Context context) {
        super(context);
        init();
    }

    public ConversationLineRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConversationLineRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        ComponentFactory.getViewComponent(this.getContext()).inject(this);

        setAdapter(conversationLineAdapter);
        setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    public void addConversationLine(ConversationLine conversationLine) {
        conversationLineAdapter.addConversationLine(conversationLine);
    }

}
