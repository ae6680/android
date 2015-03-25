package com.shinav.mathapp.conversation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.view.ButterKnifeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

@SuppressLint("ViewConstructor")
public class ConversationEntryView extends ButterKnifeLayout {

    public static final String IS_TYPING_TEXT = "aan het typen";
    public static final int DELAY_MILLIS = 300;

    private ViewHolder holder;
    private boolean showTyping = true;
    private ConversationEntry conversationEntry;
    private Runnable runnable;
    private Handler handler;
    private Context context;

    public ConversationEntryView(Context context, ConversationEntry conversationEntry) {
        super(context);
        init(context, conversationEntry);
    }

    private void init(Context context, ConversationEntry conversationEntry) {
        this.conversationEntry = conversationEntry;
        this.context = context;

        int layout;

        if (conversationEntry.isLeft()) {
            layout = R.layout.conversation_list_item_left;
        } else {
            layout = R.layout.conversation_list_item_right;
        }

        View view = inflate(layout, this, false);

        holder =  new ViewHolder(view);

        addView(view);

        initIsTyping();
    }

    private void initIsTyping() {

        holder.message.setText(IS_TYPING_TEXT);

        handler = new Handler();
        runnable = new Runnable() {

            public int counter = 0;

            @Override public void run() {
                if (showTyping) {

                    String text = IS_TYPING_TEXT+"   ";
                    if (counter == 0) {
                        text = IS_TYPING_TEXT+".  ";
                        counter++;
                    } else if (counter == 1) {
                        text = IS_TYPING_TEXT+".. ";
                        counter++;
                    } else if (counter == 2) {
                        text = IS_TYPING_TEXT+"...";
                        counter++;
                    } else {
                        counter = 0;
                    }

                    holder.message.setText(text);

                    handler.postDelayed(this, DELAY_MILLIS);
                }
            }
        };
        runnable.run();

    }

    public void showMessage() {
        showTyping = false;
        handler.removeCallbacks(runnable);

        holder.message.setText(conversationEntry.getMessage());

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        holder.message.startAnimation(animation);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.conversation_entry_message) TextView message;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

    }

}
