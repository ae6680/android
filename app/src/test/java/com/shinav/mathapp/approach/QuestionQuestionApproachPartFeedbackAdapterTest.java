package com.shinav.mathapp.approach;

import com.shinav.mathapp.approach.feedback.ApproachPartFeedbackAdapter;
import com.shinav.mathapp.db.pojo.ApproachPart;

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
public class QuestionQuestionApproachPartFeedbackAdapterTest {

    private ApproachPartFeedbackAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new ApproachPartFeedbackAdapter();
    }

    @Test
    public void testCorrectWhenPositionEqualsItemPosition() throws Exception {
        int pos1 = 0;

        ApproachPart approachPart = new ApproachPart();
        approachPart.setPosition(pos1);

        adapter.setApproachParts(Arrays.asList(approachPart));

        assertThat(adapter.isCorrect(pos1), is(true));
    }

    @Test
    public void testIncorrectWhenPositionNotEqualsItemPosition() throws Exception {
        int approachPosition = 1;
        int pos1 = 0;

        ApproachPart approachPart = new ApproachPart();
        approachPart.setPosition(approachPosition);

        adapter.setApproachParts(Arrays.asList(approachPart));

        assertThat(adapter.isCorrect(pos1), is(false));
    }

}
