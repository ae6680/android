package com.shinav.mathapp.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.shinav.mathapp.time.TimeUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.shinav.mathapp.MyApplication.PREF_TUTORIAL_COMPLETED;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, manifest = Config.NONE)
public class UpdateCheckerTest {

    private UpdateChecker updateChecker;
    private SharedPreferences prefs;
    private Context context;
    private ConnectivityManager cm;
    private NetworkInfo networkInfo;
    private TimeUtils timeUtils;
    private Editor editor;

    @Before
    public void setUp() throws Exception {
        prefs = Mockito.mock(SharedPreferences.class);
        context = Mockito.mock(Context.class);
        cm = Mockito.mock(ConnectivityManager.class);
        networkInfo = Mockito.mock(NetworkInfo.class);
        timeUtils = Mockito.mock(TimeUtils.class);
        editor = Mockito.mock(Editor.class);

        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(cm);
        Mockito.when(cm.getActiveNetworkInfo()).thenReturn(networkInfo);
        Mockito.when(prefs.edit()).thenReturn(editor);
        Mockito.when(editor.putLong(anyString(), anyLong())).thenReturn(editor);

        updateChecker = new UpdateChecker(context,prefs, timeUtils);
    }

    @Test
    public void testCheckTrueWhenOnlineAndDayLaterThanUpdated() throws Exception {
        long updated = 0;
        long today = System.currentTimeMillis();
        boolean network = true;
        boolean tutorialCompleted = true;

        Mockito.when(prefs.getLong(anyString(), anyLong())).thenReturn(updated);
        Mockito.when(prefs.getBoolean(eq(PREF_TUTORIAL_COMPLETED), eq(false))).thenReturn(tutorialCompleted);
        Mockito.when(timeUtils.getCurrentTimeMillis()).thenReturn(today);
        Mockito.when(networkInfo.isConnectedOrConnecting()).thenReturn(network);

        assertThat(updateChecker.check(), is(true));
    }

    @Test
    public void testCheckFalseWhenOnlineAndSameDayThanUpdated() throws Exception {
        long updated = System.currentTimeMillis();
        long today = System.currentTimeMillis();
        boolean network = true;
        boolean tutorialCompleted = true;

        Mockito.when(prefs.getLong(anyString(), anyLong())).thenReturn(updated);
        Mockito.when(prefs.getBoolean(eq(PREF_TUTORIAL_COMPLETED), eq(false))).thenReturn(tutorialCompleted);
        Mockito.when(timeUtils.getCurrentTimeMillis()).thenReturn(today);
        Mockito.when(networkInfo.isConnectedOrConnecting()).thenReturn(network);

        assertThat(updateChecker.check(), is(false));
    }

    @Test
    public void testCheckTrueWhenTutorialNotCompletedButUpdatedForTheDay() throws Exception {
        long updated = 1;
        long today = System.currentTimeMillis();
        boolean network = true;
        boolean tutorialCompleted = false;

        Mockito.when(prefs.getLong(anyString(), anyLong())).thenReturn(updated);
        Mockito.when(prefs.getBoolean(eq(PREF_TUTORIAL_COMPLETED), eq(false))).thenReturn(tutorialCompleted);
        Mockito.when(timeUtils.getCurrentTimeMillis()).thenReturn(today);
        Mockito.when(networkInfo.isConnectedOrConnecting()).thenReturn(network);

        assertThat(updateChecker.check(), is(true));

    }

    @Test
    public void testCheckFalseWhenNotOnline() throws Exception {
        long updated = 1;
        long today = System.currentTimeMillis();
        boolean network = false;
        boolean tutorialCompleted = false;

        Mockito.when(prefs.getLong(anyString(), anyLong())).thenReturn(updated);
        Mockito.when(prefs.getBoolean(eq(PREF_TUTORIAL_COMPLETED), eq(false))).thenReturn(tutorialCompleted);
        Mockito.when(timeUtils.getCurrentTimeMillis()).thenReturn(today);
        Mockito.when(networkInfo.isConnectedOrConnecting()).thenReturn(network);

        assertThat(updateChecker.check(), is(false));

    }

}
