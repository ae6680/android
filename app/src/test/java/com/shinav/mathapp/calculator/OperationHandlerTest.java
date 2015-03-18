package com.shinav.mathapp.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class OperationHandlerTest {

    private OperationHandler operationHandler;

    @Before
    public void setUp() throws Exception {
        operationHandler = new OperationHandler();
    }

    @Test
    public void testReturnsOpenParenthesisWhenNotOpened() throws Exception {
        String equation = "";

        equation = operationHandler.handleParenthesis(equation);

        assertThat(equation, containsString("("));
    }

    @Test
    public void testReturnsClosedParenthesisWhenOpened() throws Exception {
        String equation = "(13";

        equation = operationHandler.handleParenthesis(equation);

        assertThat(equation, containsString(")"));
    }

    @Test
    public void testReturnsNothingWhenParenthesisOnlyOpened() throws Exception {
        String equation = "(";

        equation = operationHandler.handleParenthesis(equation);

        assertThat(equation, equalTo(equation));
    }

    @Test
    public void testReturnsOpenParenthesisWhenClosedMultipleTimes() throws Exception {
        String equation = "(13)";

        equation = operationHandler.handleParenthesis(equation);

        assertThat(equation, endsWith("("));
    }

    @Test
    public void testReturnsClosedParenthesisWhenOpenedWithMultipleParenthesisGroups() throws Exception {
        String equation = "(13) + (13";

        equation = operationHandler.handleParenthesis(equation);

        assertThat(equation, endsWith(")"));
    }

    @Test
    public void testReturnsNothingWhenLastOpenedParenthesisIsOnlyOpened() throws Exception {
        String equation = "(13) + (";

        equation = operationHandler.handleParenthesis(equation);

        assertThat(equation, equalTo(equation));
    }

}
