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

package com.androidzeitgeist.ani.transmitter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.net.DatagramPacket;
import java.net.MulticastSocket;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import android.content.Intent;

import com.androidzeitgeist.ani.DummyTestValues;

/**
 * Unit tests for the {@link Transmitter} class.
 */
public class TransmitterTest {
    /**
     * Calling {@link Transmitter#transmit(Intent)} will create a {@link MulticastSocket}
     * and send a {@link DatagramPacket}.
     */
    @Test
    public void testTransmitWillSendAMulticastPacket() throws Exception {
        Transmitter transmitter = spy(new Transmitter());

        MulticastSocket socket = mock(MulticastSocket.class);
        doReturn(socket).when(transmitter).createSocket();

        Intent intent = DummyTestValues.createMockedIntent();

        transmitter.transmit(intent);

        verify(socket).send(any(DatagramPacket.class));
    }

    /**
     * When calling {@link Transmitter#transmit(Intent)} the created {@link MulticastSocket}
     * will be closed again.
     */
    @Test
    public void testCallingTransmitWillCloseTheSocket() throws Exception {
        Transmitter transmitter = spy(new Transmitter());

        MulticastSocket socket = mock(MulticastSocket.class);
        doReturn(socket).when(transmitter).createSocket();

        Intent intent = DummyTestValues.createMockedIntent();

        transmitter.transmit(intent);

        verify(socket).close();
    }

    /**
     * Calling {@link Transmitter#createSocket()} will create a new
     * {@link MulticastSocket} instance.
     */
    @Test
    public void testCreateSocketReturnsNewInstance() throws Exception {
        Transmitter transmitter = new Transmitter();

        MulticastSocket socket1 = transmitter.createSocket();
        MulticastSocket socket2 = transmitter.createSocket();

        Assert.assertNotNull(socket1);
        Assert.assertNotNull(socket2);

        Assert.assertNotSame(socket1, socket2);
    }

    /**
     * When calling {@link Transmitter#transmit(Intent)} the send
     * {@link DatagramPacket} will contain the {@link Intent} as URI.
     */
    @Test
    public void testSendDatagramPacketContainsIntentAsUri() throws Exception {
        Transmitter transmitter = spy(new Transmitter());

        MulticastSocket socket = mock(MulticastSocket.class);
        doReturn(socket).when(transmitter).createSocket();

        Intent intent = DummyTestValues.createMockedIntent();

        transmitter.transmit(intent);

        ArgumentCaptor<DatagramPacket> packetArgument = ArgumentCaptor.forClass(DatagramPacket.class);
        verify(socket).send(packetArgument.capture());

        Assert.assertEquals(
            intent.toUri(0),
            new String(packetArgument.getValue().getData())
        );
    }
}
