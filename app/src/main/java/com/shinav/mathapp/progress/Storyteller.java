package com.shinav.mathapp.progress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.shinav.mathapp.R;
import com.shinav.mathapp.approach.ApproachActivity;
import com.shinav.mathapp.approach.feedback.ApproachFeedbackActivity;
import com.shinav.mathapp.conversation.ConversationActivity;
import com.shinav.mathapp.db.mapper.StoryPartMapper;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.shinav.mathapp.db.pojo.StoryPart;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.question.QuestionActivity;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

public class Storyteller {

    public static final String TYPE_KEY = "type_key";

    private static int current = 0;
    private static List<ApproachPart> currentApproachPart;

    private final Context context;
    private List<StoryPart> storyParts;

    @Inject
    public Storyteller(@ForActivity Context context, StoryPartMapper storyPartMapper) {
        this.context = context;
        String storyKey = "-Jm5qeuosdf";

        storyPartMapper.getByStoryKey(storyKey, new Action1<List<StoryPart>>() {
            @Override public void call(List<StoryPart> storyParts) {
                Storyteller.this.storyParts = storyParts;
            }
        });
    }

    public void current() {
        startBasedOnType(storyParts.get(current));
    }

    public void next() {
        if (current+1 < storyParts.size()) {
            StoryPart storyPart = storyParts.get(current+1);
            startBasedOnType(storyPart);
            current++;
        }
    }

    public String getNextQuestionKey() {
        StoryPart storyPart = new StoryPart();
        int counter = current;
        while (counter+1 < storyParts.size() && !storyPart.isQuestion()) {
            storyPart = storyParts.get(counter+1);
            counter++;
        }
        return storyPart.getTypeKey();
    }

    private void startBasedOnType(StoryPart storyPart) {
        if (storyPart.isApproach()) {
            start(ApproachActivity.class, storyPart.getTypeKey());

        } else if (storyPart.isApproachFeedback()) {
            start(ApproachFeedbackActivity.class, storyPart.getTypeKey());

        } else if (storyPart.isQuestion()) {
            start(QuestionActivity.class, storyPart.getTypeKey());

        } else if (storyPart.isConversation()) {
            start(ConversationActivity.class, storyPart.getTypeKey());
        }
    }

    private void start(Class<? extends Activity> cls, String typeKey) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra(Storyteller.TYPE_KEY, typeKey);
        context.startActivity(intent);

        ((Activity) context).overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

    public void setCurrentApproach(List<ApproachPart> currentApproachPart) {
        Storyteller.currentApproachPart = currentApproachPart;
    }

    public List<ApproachPart> getCurrentApproach() {
        return currentApproachPart;
    }

}
