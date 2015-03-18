package com.shinav.mathapp.calculator;

import com.shinav.mathapp.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, manifest = Config.NONE)
public class CalculatorResultsAdapterTest {

    private CalculatorResultsAdapter calculatorResultsAdapter;

    @Before
    public void setUp() throws Exception {
        calculatorResultsAdapter = new CalculatorResultsAdapter();
    }

    @Test
    public void testCorrectLayout() throws Exception {
        assertEquals(R.layout.calculator_results_item, CalculatorResultsAdapter.ITEM_LAYOUT);
    }

    @Test
    public void testIncreaseItemsWhenAdded() throws Exception {
        int count = calculatorResultsAdapter.getItemCount();

        calculatorResultsAdapter.addItem(new CalculatorEntry());

        assertThat(calculatorResultsAdapter.getItemCount(), is(count+1));
    }

    @Test
    public void testPopulatesWithTwoItemsWhenConstructed() throws Exception {
        assertThat(calculatorResultsAdapter.getItemCount(), is(2));
    }


}
