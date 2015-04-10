package com.shinav.mathapp.conversation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.ConversationLine;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ConversationLineAdapter extends RecyclerView.Adapter<ConversationLineAdapter.ViewHolder> {

    public static final String IS_TYPING_TEXT = "aan het typen";
    public static final int DELAY_MILLIS = 150;

    @Inject Bus bus;

    private List<ConversationLine> conversationLines = new ArrayList<>();
    private ViewHolder holder;

    @Inject
    public ConversationLineAdapter() { }

    @Override
    public ConversationLineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout;

        switch (viewType) {
            case ConversationLine.ALIGNMENT_RIGHT:
                layout = R.layout.conversation_list_item_right;
                break;
            default:
                layout = R.layout.conversation_list_item_left;
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConversationLineAdapter.ViewHolder holder, int position) {

        if (position == conversationLines.size()-1) {
            this.holder = holder;
            startTyping();
        }

    }

    @Override public int getItemViewType(int position) {
        return conversationLines.get(position).getAlignment();
    }

    @Override public int getItemCount() {
        return conversationLines.size();
    }

    public void addConversationLine(ConversationLine conversationLine) {
        conversationLines.add(conversationLine);
        notifyItemInserted(conversationLines.size()-1);
//        notifyDataSetChanged();
    }

    public void startTyping() {

        holder.line_value.setText(IS_TYPING_TEXT);

        // Too performance heavy for now.
//        final Subscription typingSubscription = Observable.interval(DELAY_MILLIS, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {
//
//            @Override public String call(Long aLong) {
//
//                String text = IS_TYPING_TEXT + "   ";
//
//                switch ((int) (aLong % 3)) {
//                    case 0:
//                        text = IS_TYPING_TEXT + ".  ";
//                        break;
//                    case 1:
//                        text = IS_TYPING_TEXT + ".. ";
//                        break;
//                    case 2:
//                        text = IS_TYPING_TEXT + "...";
//                }
//
//                return text;
//            }
//
//        })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override public void call(String text) {
//                        Log.d("HOI", "Message text :" + text);
//                        holder.line_value.setText(text);
//                    }
//                });

        final ConversationLine conversationLine = conversationLines.get(conversationLines.size()-1);

        Observable<Long> timer = Observable.timer(conversationLine.getTypingDuration()-2000, TimeUnit.MILLISECONDS);
        timer
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override public void call(Long aLong) {
//                        typingSubscription.unsubscribe();
                        showMessage(conversationLine);
                    }
                });
    }

    public void showMessage(ConversationLine conversationLine) {
        holder.line_value.setText(conversationLine.getValue());
//        bus.post(new ConversationMessageShownEvent(conversationLine.getPosition()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.conversation_line_value) TextView line_value;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
