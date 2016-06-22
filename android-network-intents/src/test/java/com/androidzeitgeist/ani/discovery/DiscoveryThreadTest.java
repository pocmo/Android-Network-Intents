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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.MulticastSocket;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.androidzeitgeist.ani.internal.AndroidNetworkIntents;

/**
 * Unit tests for the {@link DiscoveryThread} class.
 */
public class DiscoveryThreadTest {
    /**
     * Running the {@link DiscoveryThread} will call {@link DiscoveryListener#onDiscoveryStarted()}
     * and {@link DiscoveryListener#onDiscoveryStopped()}.
     */
    @Test
    public void testListenerWillBeNotifiedAboutStartAndEnd() throws Exception{
        DiscoveryListener listener = mock(DiscoveryListener.class);

        DiscoveryThread thread = spy(new DiscoveryThread(
            AndroidNetworkIntents.DEFAULT_MULTICAST_ADDRESS,
            AndroidNetworkIntents.DEFAULT_PORT,
            listener
        ));

        doNothing().when(thread).receiveIntents();

        thread.run();

        verify(listener).onDiscoveryStarted();
        verify(listener).onDiscoveryStopped();
    }

    /**
     * Verify that close() will be called on the created socket.
     */
    @Test
    public void testSocketWillBeClosed() throws Exception {
        DiscoveryListener listener = mock(DiscoveryListener.class);

        DiscoveryThread thread = spy(new DiscoveryThread(
            AndroidNetworkIntents.DEFAULT_MULTICAST_ADDRESS,
            AndroidNetworkIntents.DEFAULT_PORT,
            listener
        ));

        MulticastSocket socket = mock(MulticastSocket.class);
        doReturn(socket).when(thread).createSocket();

        doNothing().when(thread).receiveIntents();

        thread.run();

        verify(socket).close();
    }

    /**
     * On exception being thrown the listener will be notified about an error by
     * calling {@link DiscoveryListener#onDiscoveryError(Exception)}.
     */
    @Test
    public void testOnErrorListenerWillBeNotified() throws Exception {
        DiscoveryListener listener = mock(DiscoveryListener.class);

        DiscoveryThread thread = spy(new DiscoveryThread(
            AndroidNetworkIntents.DEFAULT_MULTICAST_ADDRESS,
            AndroidNetworkIntents.DEFAULT_PORT,
            listener
        ));

        IOException exception = mock(IOException.class);
        doThrow(exception).when(thread).createSocket();

        thread.run();

        verify(listener).onDiscoveryStarted();
        verify(listener).onDiscoveryError(exception);
        verify(listener).onDiscoveryStopped();
    }

    /**
     * Calling {@link DiscoveryThread#createSocket()} will create a new
     * {@link MulticastSocket} instance.
     */
    @Test
    @Ignore // Might not be able to assign address on local machine
    public void testCreateSocketReturnsNewInstance() throws Exception {
        DiscoveryListener listener = mock(DiscoveryListener.class);

        DiscoveryThread thread = new DiscoveryThread(
            AndroidNetworkIntents.DEFAULT_MULTICAST_ADDRESS,
            AndroidNetworkIntents.DEFAULT_PORT,
            listener
        );

        MulticastSocket socket1 = thread.createSocket();
        MulticastSocket socket2 = thread.createSocket();

        Assert.assertNotNull(socket1);
        Assert.assertNotNull(socket2);

        Assert.assertNotSame(socket1, socket2);
    }
}
