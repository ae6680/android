package com.shinav.mathapp.conversation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.ConversationLine;
import com.shinav.mathapp.event.ConversationMessageShownEvent;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.view.ButterKnifeLayout;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class ConversationLineView extends ButterKnifeLayout {

    public static final String IS_TYPING_TEXT = "aan het typen";
    public static final int DELAY_MILLIS = 150;

    private ViewHolder holder;
    private ConversationLine conversationLine;

    @Inject Bus bus;
    @Inject SharedPreferences sharedPreferences;

    public ConversationLineView(Context context, ConversationLine conversationLine) {
        super(context);
        init(conversationLine);
    }

    private void init(ConversationLine conversationLine) {
        this.conversationLine = conversationLine;

        ComponentFactory.getViewComponent(this.getContext()).inject(this);

        int layout;

        switch (conversationLine.getAlignment()) {
            case ConversationLine.ALIGNMENT_RIGHT:
                layout = R.layout.conversation_list_item_right;
                break;
            default:
                layout = R.layout.conversation_list_item_left;
        }

        View view = inflate(layout, this, false);

        holder =  new ViewHolder(view);

        if (conversationLine.getImageUrl().equals("main")) {

            int characterResId = sharedPreferences.getInt(MyApplication.PREF_CHOSEN_CHARACTER, 0);
            holder.image.setImageResource(characterResId);

        } else {
            Picasso.with(this.getContext())
                    .load(conversationLine.getImageUrl())
                    .centerCrop()
                    .fit()
                    .into(holder.image);
        }

        addView(view);
    }

    public void startTyping() {

        holder.line_value.setText(IS_TYPING_TEXT);

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
                        holder.line_value.setText(text);
                    }
                });

        Observable<Long> timer = Observable.timer(conversationLine.getTypingDuration(), TimeUnit.MILLISECONDS);
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
        holder.line_value.setText(conversationLine.getValue());
        bus.post(new ConversationMessageShownEvent(conversationLine.getPosition()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.conversation_line_value) TextView line_value;
        @InjectView(R.id.conversation_line_image) ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
