package com.androidzeitgeist.ani.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import android.content.Intent;

/**
 * Helper class to create (unique) test values and objects for test cases.
 */
public class DummyTestValues {
    /**
     * Create a mocked intent for transmitting/receiving.
     */
    public static Intent createMockedIntent() {
        Intent intent = mock(Intent.class);

        when(intent.toUri(0)).thenReturn("/");

        return intent;
    }
}
