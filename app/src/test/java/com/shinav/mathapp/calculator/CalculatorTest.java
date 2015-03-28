package com.shinav.mathapp.calculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, manifest = Config.NONE)
public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator(
                new EquationFilterer(),
                new AnswerCleaner()
        );
    }

    @Test
    public void testCalculatesAddition() throws Exception {
        String equation = "1 + 1";
        assertThat(calculator.calculate(equation), is("2"));
    }

    @Test
    public void testCalculatesSubtraction() throws Exception {
        String equation = "1 - 1";
        assertThat(calculator.calculate(equation), is("0"));
    }

    @Test
    public void testCalculatesMultiplication() throws Exception {
        String equation = "2 × 2";
        assertThat(calculator.calculate(equation), is("4"));
    }

    @Test
    public void testCalculatesDivision() throws Exception {
        String equation = "4 ÷ 2";
        assertThat(calculator.calculate(equation), is("2"));
    }

    @Test
    public void testCalculatesSquareRoot() throws Exception {
        String equation = "√ 16";
        assertThat(calculator.calculate(equation), is("4"));
    }

    @Test
    public void testCalculatesPowerOf() throws Exception {
        String equation = "2 ^ 5";
        assertThat(calculator.calculate(equation), is("32"));
    }

    @Test
    public void testCalculatesBasedOnOrderOfOperations() throws Exception {
        String equation = "6 / 5 * (2 + 3) - 3";
        assertThat(calculator.calculate(equation), is("3"));
    }

}
