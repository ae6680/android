package com.shinav.mathapp.tabs;

import android.view.ViewGroup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, manifest = Config.NONE)
public class TabsPagerAdapterTest {

    @Test
    public void testAddsTabIncreasesCount() throws Exception {
        TabsPagerAdapter adapter = new TabsPagerAdapter();
        int count = adapter.getCount();

        adapter.addTab(Mockito.mock(ViewGroup.class));

        assertEquals(count + 1, adapter.getCount());
    }

}
