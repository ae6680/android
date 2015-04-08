package com.shinav.mathapp.conversation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.ConversationPart;
import com.shinav.mathapp.event.ConversationMessageShown;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.view.ButterKnifeLayout;
import com.squareup.otto.Bus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class ConversationPartView extends ButterKnifeLayout {

    public static final String IS_TYPING_TEXT = "aan het typen";
    public static final int DELAY_MILLIS = 300;

    private ViewHolder holder;
    private ConversationPart conversationPart;

    @Inject Bus bus;

    public ConversationPartView(Context context, ConversationPart conversationPart) {
        super(context);
        init(conversationPart);
    }

    private void init(ConversationPart conversationPart) {
        this.conversationPart = conversationPart;

        ComponentFactory.getViewComponent(this.getContext()).inject(this);

        int layout;

        if (conversationPart.getAlignment() == 0) {
            layout = R.layout.conversation_list_item_left;
        } else {
            layout = R.layout.conversation_list_item_right;
        }

        View view = inflate(layout, this, false);

        holder =  new ViewHolder(view);

        addView(view);
    }

    public void startTyping() {

        holder.message.setText(IS_TYPING_TEXT);

        final Subscription typingSubscription = Observable.interval(DELAY_MILLIS, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {

            @Override public String call(Long aLong) {

                String text = IS_TYPING_TEXT + "   ";

                switch ((int) (aLong % 3)) {
                    case 0:
                        text = IS_TYPING_TEXT + ".  ";
                        break;
                    case 1:
                        text = IS_TYPING_TEXT + ".. ";
                        break;
                    case 2:
                        text = IS_TYPING_TEXT + "...";
                }

                return text;
            }

        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override public void call(String text) {
                        Log.d("HOI", "Message text :" + text);
                        holder.message.setText(text);
                    }
                });

        Observable<Long> timer = Observable.timer(conversationPart.getTypingDuration(), TimeUnit.MILLISECONDS);
        timer
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override public void call(Long aLong) {
                        typingSubscription.unsubscribe();
                        showMessage();
                    }
                });

    }

    public void showMessage() {
        holder.message.setText(conversationPart.getMessage());

        bus.post(new ConversationMessageShown(conversationPart.getPosition()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.conversation_part_message) TextView message;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
