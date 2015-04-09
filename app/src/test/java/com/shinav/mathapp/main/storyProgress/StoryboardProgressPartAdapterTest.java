package com.shinav.mathapp.main.storyProgress;

import com.shinav.mathapp.db.pojo.StoryProgressPart;
import com.squareup.otto.Bus;

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

    private StoryProgressPartAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new StoryProgressPartAdapter(Mockito.mock(Bus.class));
    }

    @Test
    public void testItemCountIsAmountOfQuestions() throws Exception {
        List<StoryProgressPart> storyProgressParts = Arrays.asList(
                Mockito.mock(StoryProgressPart.class),
                Mockito.mock(StoryProgressPart.class)
        );

        adapter.setStoryProgressParts(storyProgressParts);

        assertThat(adapter.getItemCount(), is(2));
    }

}
