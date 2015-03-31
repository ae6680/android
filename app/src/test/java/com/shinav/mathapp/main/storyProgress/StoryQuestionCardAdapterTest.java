package com.shinav.mathapp.main.storyProgress;

import com.shinav.mathapp.question.Question;
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
public class StoryQuestionCardAdapterTest {

    private StoryQuestionCardAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new StoryQuestionCardAdapter(Mockito.mock(Bus.class));
    }

    @Test
    public void testItemCountIsAmountOfQuestions() throws Exception {
        List<Question> questions = Arrays.asList(
                Mockito.mock(Question.class),
                Mockito.mock(Question.class)
        );

        adapter.setQuestions(questions);

        assertThat(adapter.getItemCount(), is(2));
    }

}
