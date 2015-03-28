package com.shinav.mathapp.approach;

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
public class ApproachPartAdapterTest {

    private ApproachPartAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new ApproachPartAdapter();
    }

    @Test
    public void testItemCountIsZeroIfNotSet() throws Exception {
        assertThat(adapter.getItemCount(), is(0));
    }

    @Test
    public void testItemCountIsAmountOfApproachEntries() throws Exception {
        List<ApproachPart> approachEntries = Arrays.asList(
                new ApproachPart(),
                new ApproachPart()
        );

        adapter.setApproachParts(approachEntries);

        assertThat(adapter.getItemCount(), is(2));
    }

    @Test
    public void testGetItemReturnsApproachEntryBasedOnPosition() throws Exception {
        List<ApproachPart> approachEntries = Arrays.asList(
                new ApproachPart(),
                new ApproachPart()
        );

        adapter.setApproachParts(approachEntries);

        assertThat(adapter.getItem(1), is(approachEntries.get(1)));
    }

}
