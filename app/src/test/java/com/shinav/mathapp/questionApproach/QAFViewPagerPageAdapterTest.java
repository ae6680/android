package com.shinav.mathapp.questionApproach;

import com.shinav.mathapp.questionApproachFeedback.QAFViewPagerPageAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, manifest = Config.NONE)
public class QAFViewPagerPageAdapterTest {

    private QAFViewPagerPageAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new QAFViewPagerPageAdapter();
    }

    @Test
    public void testCorrectWhenPositionEqualsItemPosition() throws Exception {
//        int pos1 = 0;
//
//        QuestionApproachPart questionApproachPart = new QuestionApproachPart();
//        questionApproachPart.setPosition(pos1);
//
//        adapter.setQuestionApproachParts(Arrays.asList(questionApproachPart));
//
//        assertThat(adapter.isCorrect(questionApproachPart), is(true));
    }

    @Test
    public void testIncorrectWhenPositionNotEqualsItemPosition() throws Exception {
//        int approachPosition = 1;
//
//        QuestionApproachPart questionApproachPart = new QuestionApproachPart();
//        questionApproachPart.setPosition(approachPosition);
//
//        adapter.setQuestionApproachParts(Arrays.asList(questionApproachPart));
//
//        assertThat(adapter.isCorrect(questionApproachPart), is(false));
    }

}
