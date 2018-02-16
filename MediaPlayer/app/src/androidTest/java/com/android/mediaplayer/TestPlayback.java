package com.android.mediaplayer;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static junit.framework.Assert.assertEquals;

/**
 * Created by tosshaan on 2/16/2018.
 */

public class TestPlayback {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testGetCurrentResource() {
        LocalDateTime dummyTime = LocalDateTime.of(2017, 2, 7, 12, 00);
        TimeMachine.useFixedClockAt(dummyTime);
        int currentResource = mainActivity.getActivity().getCurrentResource();
        assertEquals(currentResource, mainActivity.getActivity().MEDIA_RES_ID);
        mainActivity.getActivity().loadMedia(currentResource);
    }
}
