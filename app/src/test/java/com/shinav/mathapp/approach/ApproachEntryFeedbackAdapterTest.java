package com.shinav.mathapp.approach;

import com.shinav.mathapp.approach.feedback.ApproachEntryFeedbackAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, manifest = Config.NONE)
public class ApproachEntryFeedbackAdapterTest {

    private ApproachEntryFeedbackAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new ApproachEntryFeedbackAdapter();
    }

    @Test
    public void testCorrectWhenPositionEqualsItemPosition() throws Exception {
        int pos1 = 0;

        ApproachEntry approachEntry = new ApproachEntry();
        approachEntry.setPosition(pos1);

        adapter.setApproachEntries(Arrays.asList(approachEntry));

        assertThat(adapter.isCorrect(pos1), is(true));
    }

    @Test
    public void testIncorrectWhenPositionNotEqualsItemPosition() throws Exception {
        int approachPosition = 1;
        int pos1 = 0;

        ApproachEntry approachEntry = new ApproachEntry();
        approachEntry.setPosition(approachPosition);

        adapter.setApproachEntries(Arrays.asList(approachEntry));

        assertThat(adapter.isCorrect(pos1), is(false));
    }

}
