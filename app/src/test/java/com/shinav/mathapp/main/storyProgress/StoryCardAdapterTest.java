package com.shinav.mathapp.main.storyProgress;

import com.shinav.mathapp.story.StoryEntry;

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
public class StoryCardAdapterTest {

    private StoryCardAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new StoryCardAdapter();
    }

    @Test
    public void testItemCountIsAmountOfApproachEntries() throws Exception {
        List<StoryEntry> storyEntries = Arrays.asList(
                Mockito.mock(StoryEntry.class),
                Mockito.mock(StoryEntry.class)
        );

        adapter.setStoryEntries(storyEntries);

        assertThat(adapter.getItemCount(), is(2));
    }

}
