package com.shinav.mathapp.calculator;

import com.shinav.mathapp.R;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, manifest = Config.NONE)
public class CalculatorResultsAdapterTest {

    private CalculatorResultsAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new CalculatorResultsAdapter(
                Mockito.mock(Bus.class)
        );
    }

    @Test
    public void testCorrectLayout() throws Exception {
        assertEquals(R.layout.calculator_results_item, CalculatorResultsAdapter.ITEM_LAYOUT);
    }

    @Test
    public void testIncreaseItemsWhenAdded() throws Exception {
        int count = adapter.getItemCount();

        adapter.addItem(new CalculatorResultItem());

        assertThat(adapter.getItemCount(), is(count + 1));
    }

    @Test
    public void testPopulatesWithTwoItemsWhenConstructed() throws Exception {
        assertThat(adapter.getItemCount(), is(2));
    }


}
