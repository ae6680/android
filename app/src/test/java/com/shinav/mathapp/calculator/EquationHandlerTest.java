package com.shinav.mathapp.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class EquationHandlerTest {

    private EquationHandler equationHandler;

    @Before
    public void setUp() throws Exception {
        equationHandler = new EquationHandler();
    }

    @Test
    public void testReturnsOpenParenthesisWhenNotOpened() throws Exception {
        String equation = "";

        equation = equationHandler.handleParenthesis(equation);

        assertThat(equation, equalTo("("));
    }

    @Test
    public void testReturnsClosedParenthesisWhenOpened() throws Exception {
        String equation = "(13";

        equation = equationHandler.handleParenthesis(equation);

        assertThat(equation, equalTo("(13)"));
    }

    @Test
    public void testReturnsNothingWhenParenthesisOnlyOpened() throws Exception {
        String equation = "(";

        equation = equationHandler.handleParenthesis(equation);

        assertThat(equation, equalTo(equation));
    }

    @Test
    public void testReturnsOpenParenthesisWhenClosedMultipleTimes() throws Exception {
        String equation = "(13)";

        equation = equationHandler.handleParenthesis(equation);

        assertThat(equation, equalTo("(13)("));
    }

    @Test
    public void testReturnsClosedParenthesisWhenOpenedWithMultipleParenthesisGroups() throws Exception {
        String equation = "(13) + (13";

        equation = equationHandler.handleParenthesis(equation);

        assertThat(equation, equalTo("(13) + (13)"));
    }

    @Test
    public void testReturnsNothingWhenLastOpenedParenthesisIsOnlyOpened() throws Exception {
        String equation = "(13) + (";

        equation = equationHandler.handleParenthesis(equation);

        assertThat(equation, equalTo(equation));
    }

    @Test
    public void testBackspaceRemovesOneCharacter() throws Exception {
        String equation = "1 + 1";

        equation = equationHandler.handleBackspace(equation);

        assertThat(equation, equalTo("1 + "));
    }

    @Test
    public void testBackspaceRemovesOperatorWhenLastAction() throws Exception {
        String equation = "1 + ";

        equation = equationHandler.handleBackspace(equation);

        assertThat(equation, equalTo("1"));
    }

    @Test
    public void testOperatorHaveAdjacentSpaces() throws Exception {
        String equation = "1";

        equation = equationHandler.handleOperator(equation, "+");

        assertThat(equation, equalTo("1 + "));
    }

    @Test
    public void testNumberIsAddedToEquation() throws Exception {
        String equation = "1";
        String number = "1";

        equation = equationHandler.handleNumber(equation, number);

        assertThat(equation, equalTo("11"));

    }
}
