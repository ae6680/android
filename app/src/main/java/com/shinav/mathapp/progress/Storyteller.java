package com.shinav.mathapp.progress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.shinav.mathapp.R;
import com.shinav.mathapp.approach.Approach;
import com.shinav.mathapp.approach.ApproachActivity;
import com.shinav.mathapp.approach.feedback.ApproachFeedbackActivity;
import com.shinav.mathapp.conversation.ConversationActivity;
import com.shinav.mathapp.question.QuestionActivity;
import com.shinav.mathapp.repository.RealmRepository;
import com.shinav.mathapp.story.Story;
import com.shinav.mathapp.story.StoryEntry;

import java.util.ArrayList;
import java.util.List;

public class Storyteller {

    public static final String TYPE_KEY = "type_key";

    private static int current = 0;
    private static List<Approach> currentApproach;

    private final Context context;
    private final Story story;

    public Storyteller(Context context) {
        this.context = context;
        story = RealmRepository.getInstance().getStory("story-0");
    }

    public void next() {
        List<StoryEntry> entries = new ArrayList<>();
        entries.addAll(story.getStoryEntries());

        if (current+1 < entries.size()) {
            StoryEntry storyEntry = entries.get(current+1);
            startBasedOnType(storyEntry);
            current++;
        }
    }

    public void current() {
        List<StoryEntry> entries = new ArrayList<>();
        entries.addAll(story.getStoryEntries());

        StoryEntry storyEntry = entries.get(current);

        startBasedOnType(storyEntry);
    }

    private void startBasedOnType(StoryEntry storyEntry) {
        if (storyEntry.isApproach()) {
            start(ApproachActivity.class, storyEntry.getTypeKey());

        } else if (storyEntry.isApproachFeedback()) {
            start(ApproachFeedbackActivity.class, storyEntry.getTypeKey());

        } else if (storyEntry.isQuestion()) {
            start(QuestionActivity.class, storyEntry.getTypeKey());

        } else if (storyEntry.isConversation()) {
            start(ConversationActivity.class, storyEntry.getTypeKey());
        }
    }

    private void start(Class<? extends Activity> cls, String typeKey) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra(Storyteller.TYPE_KEY, typeKey);
        context.startActivity(intent);

        ((Activity) context).overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

    public void setCurrentApproach(List<Approach> currentApproach) {
        Storyteller.currentApproach = currentApproach;
    }

    public List<Approach> getCurrentApproach() {
        return currentApproach;
    }

}
