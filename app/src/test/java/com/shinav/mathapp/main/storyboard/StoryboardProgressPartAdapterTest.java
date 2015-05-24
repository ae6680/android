package com.shinav.mathapp.main.storyboard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

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
//        List<StoryboardFrame> storyboardFrames = Arrays.asList(
//                Mockito.mock(StoryboardFrame.class),
//                Mockito.mock(StoryboardFrame.class)
//        );
//
//        adapter.setListItems(storyboardFrames);
//
//        assertThat(adapter.getItemCount(), is(2));
    }

}
