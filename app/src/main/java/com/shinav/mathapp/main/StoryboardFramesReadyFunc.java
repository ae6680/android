package com.shinav.mathapp.main;

import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.main.storyboard.StoryboardFrameListItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.functions.Func3;

class StoryboardFramesReadyFunc implements Func3<
        List<StoryboardFrame>,
        List<StoryboardFrameListItem>,
        List<StoryboardFrameListItem>,
        List<StoryboardFrameListItem>
        > {

    @Override
    public List<StoryboardFrameListItem> call(
            List<StoryboardFrame> storyboardFrames,
            List<StoryboardFrameListItem> storyboardFrameListItems,
            List<StoryboardFrameListItem> storyboardFrameListItems2
    ) {

        storyboardFrameListItems.addAll(storyboardFrameListItems2);

        StoryboardFrameListItem[] frameArray = orderOnFrameOrder(
                getArrayOfKeys(storyboardFrames),
                storyboardFrameListItems
        );

        return Arrays.asList(frameArray);
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

}
