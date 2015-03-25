package com.shinav.mathapp.approach;

import com.shinav.mathapp.approach.feedback.ApproachFeedbackAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class ApproachFeedbackAdapterTest {

    private ApproachFeedbackAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new ApproachFeedbackAdapter();
    }

    @Test
    public void testCorrectWhenPositionEqualsItemPosition() throws Exception {
        int pos1 = 0;

        Approach approach = new Approach();
        approach.setPosition(pos1);

        adapter.setApproaches(Arrays.asList(approach));

        assertThat(adapter.isCorrect(pos1), is(true));
    }

    @Test
    public void testIncorrectWhenPositionNotEqualsItemPosition() throws Exception {
        int approachPosition = 1;
        int pos1 = 0;

        Approach approach = new Approach();
        approach.setPosition(approachPosition);

        adapter.setApproaches(Arrays.asList(approach));

        assertThat(adapter.isCorrect(pos1), is(false));
    }

}
