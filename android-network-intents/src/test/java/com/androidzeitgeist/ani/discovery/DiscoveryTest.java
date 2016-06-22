/*
 * Copyright (C) 2013 Sebastian Kaspari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidzeitgeist.ani.discovery;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@link Discovery} class.
 */
public class DiscoveryTest {
    /**
     * Calling {@link Discovery#enable(DiscoveryListener)} is a shortcut for calling
     * {@link Discovery#setDisoveryListener(DiscoveryListener)} and
     * {@link Discovery#enable()}.
     */
    @Test
    public void testEnableWithListenerIsShortcut() throws Exception {
        DiscoveryListener listener = mock(DiscoveryListener.class);

        Discovery discovery = spy(new Discovery());
        doNothing().when(discovery).enable();

        discovery.enable(listener);

        verify(discovery).setDisoveryListener(listener);
        verify(discovery).enable();
    }

    /**
     * Calling {@link Discovery#enable()} without setting a listener will throw an
     * {@link IllegalStateException}.
     */
    @Test(expected=IllegalStateException.class)
    public void testEnableThrowsExceptionIfNoListenerIsSet() throws Exception {
        Discovery discovery = new Discovery();
        discovery.enable();
    }

    /**
     * Calling {@link Discovery#disable()} without a matching call to
     * {@link Discovery#enable()} will throw an {@link IllegalAccessError}.
     */
    @Test(expected=IllegalAccessError.class)
    public void testDisableWithoutEnablingWillThrowException() throws Exception {
        Discovery discovery = new Discovery();
        discovery.disable();
    }

    /**
     * Calling {@link Discovery#enable()} will create and start a {@link DiscoveryThread}.
     */
    @Test
    public void testEnableWillCreateAndStartDiscoveryThread() throws Exception {
        DiscoveryThread thread = mock(DiscoveryThread.class);
        DiscoveryListener listener = mock(DiscoveryListener.class);

        Discovery discovery = spy(new Discovery());
        discovery.setDisoveryListener(listener);

        doReturn(thread).when(discovery).createDiscoveryThread();

        discovery.enable();

        verify(discovery).createDiscoveryThread();
        verify(thread).start();
    }

    /**
     * Calling {@link Discovery#enable()} twice without calling {@link Discovery#disable()}
     * in between throws an {@link IllegalAccessError}.
     */
    @Test(expected=IllegalAccessError.class)
    public void testEnablingDiscoveryTwiceThrowsException() throws Exception {
        DiscoveryThread thread = mock(DiscoveryThread.class);
        DiscoveryListener listener = mock(DiscoveryListener.class);

        Discovery discovery = spy(new Discovery());
        discovery.setDisoveryListener(listener);

        doReturn(thread).when(discovery).createDiscoveryThread();

        discovery.enable();
        discovery.enable();
    }

    /**
     * Calling {@link Discovery#disable()} will call {@link DiscoveryThread#stopDiscovery()}
     */
    @Test
    public void testCallingDisableWillCallDisableDiscoveryOnThread() throws Exception {
        DiscoveryThread thread = mock(DiscoveryThread.class);
        DiscoveryListener listener = mock(DiscoveryListener.class);

        Discovery discovery = spy(new Discovery());
        discovery.setDisoveryListener(listener);

        doReturn(thread).when(discovery).createDiscoveryThread();

        discovery.enable();
        discovery.disable();

        verify(thread).start();
        verify(thread).stopDiscovery();
    }

    /**
     * Calling {@link Discovery#createDiscoveryThread()} returns new {@link DiscoveryThread}
     * instance.
     */
    @Test
    public void testCreateDiscoveryThreadReturnsNewInstance() {
        Discovery discovery = new Discovery();

        DiscoveryThread thread1 = discovery.createDiscoveryThread();
        DiscoveryThread thread2 = discovery.createDiscoveryThread();

        Assert.assertNotNull(thread1);
        Assert.assertNotNull(thread2);

        Assert.assertNotSame(thread1, thread2);
    }
}
