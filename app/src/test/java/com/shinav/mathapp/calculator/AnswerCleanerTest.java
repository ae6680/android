package com.shinav.mathapp.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class AnswerCleanerTest {

    private AnswerCleaner answerCleaner;

    @Before
    public void setUp() throws Exception {
        answerCleaner = new AnswerCleaner();
    }

    @Test
    public void testStripDotZero() throws Exception {
        String answer = "2.0";

        answer = answerCleaner.clean(answer);

        assertThat(answer, equalTo("2"));
    }

    @Test
    public void testReplaceDotsWithCommas() throws Exception {
        String answer = "20.23";

        answer = answerCleaner.clean(answer);

        assertThat(answer, containsString(","));
    }

    @Test
    public void testRoundOnDecimals() throws Exception {
        String answer = "3.1415926535";

        answer = answerCleaner.clean(answer);

        assertThat(answer.split(",")[1].length(), is(5));
    }

}

