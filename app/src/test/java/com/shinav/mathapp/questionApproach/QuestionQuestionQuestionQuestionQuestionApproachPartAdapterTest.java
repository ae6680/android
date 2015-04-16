package com.shinav.mathapp.questionApproach;

import com.shinav.mathapp.db.pojo.QuestionApproachPart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, manifest = Config.NONE)
public class QuestionQuestionQuestionQuestionQuestionApproachPartAdapterTest {

    private QuestionApproachPartAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new QuestionApproachPartAdapter();
    }

    @Test
    public void testItemCountIsZeroIfNotSet() throws Exception {
        assertThat(adapter.getItemCount(), is(0));
    }

    @Test
    public void testItemCountIsAmountOfApproachEntries() throws Exception {
        List<QuestionApproachPart> approachEntries = Arrays.asList(
                new QuestionApproachPart(),
                new QuestionApproachPart()
        );

        adapter.setQuestionApproachParts(approachEntries);

        assertThat(adapter.getItemCount(), is(2));
    }

    @Test
    public void testGetItemReturnsApproachEntryBasedOnPosition() throws Exception {
        List<QuestionApproachPart> approachEntries = Arrays.asList(
                new QuestionApproachPart(),
                new QuestionApproachPart()
        );

        adapter.setQuestionApproachParts(approachEntries);

        assertThat(adapter.getItem(1), is(approachEntries.get(1)));
    }

}
