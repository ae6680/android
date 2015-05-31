package com.shinav.mathapp.main.storyboard.func;

import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.main.storyboard.StoryboardFrameListItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.functions.Func2;

import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_CLOSED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_OPENED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.TYPE_CUTSCENE;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.TYPE_QUESTION;

public class StoryboardFrameListItemReadyFunc implements Func2<
        List<StoryboardFrameListItem>,
        List<StoryboardFrameListItem>,
        List<StoryboardFrameListItem>
        > {

    private final List<StoryboardFrame> storyboardFrames;

    public StoryboardFrameListItemReadyFunc(List<StoryboardFrame> storyboardFrames) {
        this.storyboardFrames = storyboardFrames;
    }

    @Override
    public List<StoryboardFrameListItem> call(
            List<StoryboardFrameListItem> storyboardFrameListItems,
            List<StoryboardFrameListItem> storyboardFrameListItems2
    ) {

        storyboardFrameListItems.addAll(storyboardFrameListItems2);

        StoryboardFrameListItem[] frameArray = orderOnFrameOrder(
                getArrayOfKeys(storyboardFrames),
                storyboardFrameListItems
        );

        List<StoryboardFrameListItem> frames = Arrays.asList(frameArray);
        setupStoryFrameStates(frames);

        return frames;
    }

    private StoryboardFrameListItem[] orderOnFrameOrder(
            ArrayList<String> frameObjectKeys,
            List<StoryboardFrameListItem> storyboardFrameListItems
    ) {

        StoryboardFrameListItem[] frameArray =
                new StoryboardFrameListItem[frameObjectKeys.size()];

        for (StoryboardFrameListItem frameListItem : storyboardFrameListItems) {
            int index = frameObjectKeys.indexOf(frameListItem.getKey());
            frameArray[index] = frameListItem;
        }

        return frameArray;
    }

    private ArrayList<String> getArrayOfKeys(List<StoryboardFrame> storyboardFrames) {
        ArrayList<String> frameObjectKeys = new ArrayList<>(storyboardFrames.size());

        for (StoryboardFrame frame : storyboardFrames) {
            frameObjectKeys.add(frame.getFrameTypeKey());
        }
        return frameObjectKeys;
    }

    private void setupStoryFrameStates(List<StoryboardFrameListItem> listItems) {
        for (StoryboardFrameListItem listItem : listItems) {

            // Open up all cutscenes until current open
            // question is found.
            if (listItem.getType().equals(TYPE_CUTSCENE)) {
                listItem.setState(STATE_OPENED);
            }

            if (listItem.getType().equals(TYPE_QUESTION)) {
                // Open up first question if closed after tutorial.
                if (listItem.getState() == STATE_CLOSED) {
                    listItem.setState(STATE_OPENED);
                }

                // Currently open question is found, break.
                if (listItem.getState() == STATE_OPENED) {
                    break;
                }
            }
        }
    }

}
