package com.shinav.mathapp.questionApproach;

import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.shinav.mathapp.questionApproach.feedback.QuestionApproachPartFeedbackAdapter;

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
public class QuestionQuestionQuestionQuestionApproachPartFeedbackAdapterTest {

    private QuestionApproachPartFeedbackAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new QuestionApproachPartFeedbackAdapter();
    }

    @Test
    public void testCorrectWhenPositionEqualsItemPosition() throws Exception {
        int pos1 = 0;

        QuestionApproachPart questionApproachPart = new QuestionApproachPart();
        questionApproachPart.setPosition(pos1);

        adapter.setQuestionApproachParts(Arrays.asList(questionApproachPart));

        assertThat(adapter.isCorrect(pos1), is(true));
    }

    @Test
    public void testIncorrectWhenPositionNotEqualsItemPosition() throws Exception {
        int approachPosition = 1;
        int pos1 = 0;

        QuestionApproachPart questionApproachPart = new QuestionApproachPart();
        questionApproachPart.setPosition(approachPosition);

        adapter.setQuestionApproachParts(Arrays.asList(questionApproachPart));

        assertThat(adapter.isCorrect(pos1), is(false));
    }

}
