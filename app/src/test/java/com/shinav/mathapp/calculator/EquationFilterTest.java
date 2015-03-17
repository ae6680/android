package com.shinav.mathapp.calculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EquationFilterTest {

    public EquationFilterer equationFilterer;

    @Before
    public void setUp() throws Exception {
        equationFilterer = new EquationFilterer();
    }

    @Test
    public void testFilterMultiplyCharacterToCalculableCharacter() throws Exception {
        String equation = "1 × 1";

        equation = equationFilterer.filterMultiplyCharacter(equation);

        assertThat(equation, containsString("*"));
    }

    @Test
    public void testFilterDivideCharacterToCalculableCharacter() throws Exception {
        String equation = "1 ÷ 1";

        equation = equationFilterer.filterDivideCharacter(equation);

        assertThat(equation, containsString("/"));
    }

    @Test
    public void testFilterCommaCharacterToCalculableCharacter() throws Exception {
        String equation = "3,1415926535";

        equation = equationFilterer.filterCommaCharacter(equation);

        assertThat(equation, containsString("."));
    }

    @Test
    public void testFilterSquareRootCharacterToCalculableCharacter() throws Exception {
        String equation = "√ 122";

        equation = equationFilterer.filterSquareRootCharacter(equation);

        assertThat(equation, is("sqrt(122)"));
    }

}
