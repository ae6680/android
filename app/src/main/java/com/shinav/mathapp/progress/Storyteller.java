package com.shinav.mathapp.progress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.shinav.mathapp.R;
import com.shinav.mathapp.approach.ApproachActivity;
import com.shinav.mathapp.approach.ApproachPart;
import com.shinav.mathapp.approach.feedback.ApproachFeedbackActivity;
import com.shinav.mathapp.conversation.ConversationActivity;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.question.QuestionActivity;
import com.shinav.mathapp.repository.RealmRepository;
import com.shinav.mathapp.story.Story;
import com.shinav.mathapp.story.StoryPart;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Storyteller {

    public static final String TYPE_KEY = "type_key";

    private static int current = 0;
    private static List<ApproachPart> currentApproachPart;

    private final Context context;
    private final Story story;

    @Inject
    public Storyteller(@ForActivity Context context, RealmRepository realmRepository) {
        this.context = context;
        story = realmRepository.getStory("story-0");
    }

    public void next() {
        List<StoryPart> storyParts = new ArrayList<>();
        storyParts.addAll(story.getStoryParts());

        if (current+1 < storyParts.size()) {
            StoryPart storyPart = storyParts.get(current+1);
            startBasedOnType(storyPart);
            current++;
        }
    }

    public String getNextQuestionKey() {
        List<StoryPart> storyParts = new ArrayList<>();
        storyParts.addAll(story.getStoryParts());

        StoryPart storyPart = new StoryPart();
        int counter = current;
        while (counter+1 < storyParts.size() && !storyPart.isQuestion()) {
            storyPart = storyParts.get(counter+1);
            counter++;
        }
        return storyPart.getTypeKey();
    }

    public void current() {
        List<StoryPart> storyParts = new ArrayList<>();
        storyParts.addAll(story.getStoryParts());

        StoryPart storyPart = storyParts.get(current);

        startBasedOnType(storyPart);
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
