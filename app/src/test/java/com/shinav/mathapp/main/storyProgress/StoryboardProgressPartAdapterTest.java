package com.shinav.mathapp.main.storyProgress;

import com.shinav.mathapp.db.pojo.StoryboardFrame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, manifest = Config.NONE)
public class StoryboardProgressPartAdapterTest {

    private StoryboardFrameAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new StoryboardFrameAdapter();
    }

    @Test
    public void testItemCountIsAmountOfQuestions() throws Exception {
        List<StoryboardFrame> storyboardFrames = Arrays.asList(
                Mockito.mock(StoryboardFrame.class),
                Mockito.mock(StoryboardFrame.class)
        );

        adapter.setStoryboardFrames(storyboardFrames);

        assertThat(adapter.getItemCount(), is(2));
    }

}
