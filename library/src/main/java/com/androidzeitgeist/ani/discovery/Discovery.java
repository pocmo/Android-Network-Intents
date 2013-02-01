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

import android.content.Intent;

import com.androidzeitgeist.ani.internal.AndroidNetworkIntents;

/**
 * Discovery class for receiving {@link Intent}s from the network.
 *
 * @author Sebastian Kaspari <s.kaspari@gmail.com>
 */
public class Discovery {
    private String multicastAddress;
    private int port;

    private DiscoveryListener listener;
    private DiscoveryThread thread;

    /**
     * Create a new {@link Discovery} instance that will listen to the default
     * multicast address and port for incoming {@link Intent}s.
     */
    public Discovery() {
        this(
            AndroidNetworkIntents.DEFAULT_MULTICAST_ADDRESS,
            AndroidNetworkIntents.DEFAULT_PORT
        );
    }

    /**
     * Create a new {@link Discovery} instance that will listen to the default
     * multicast address and the given port for incoming {@link Intent}s.
     *
     * @param port The network port to listen to.
     */
    public Discovery(int port) {
        this(
            AndroidNetworkIntents.DEFAULT_MULTICAST_ADDRESS,
            port
        );
    }

    /**
     * Create a new {@link Discovery} instance that will listen to the given
     * multicast address and port for incoming {@link Intent}s.
     *
     * @param multicastAddress The multicast address to listen to, e.g. 225.4.5.6.
     * @param port The port to listen to.
     */
    public Discovery(String multicastAddress, int port) {
        this.multicastAddress = multicastAddress;
        this.port = port;
    }

    /**
     * Set the {@link DiscoveryListener} instance that will be notified about
     * incoming {@link Intent}s.
     *
     * @param listener The {@link DiscoveryListener} that will be notified about
     *                 incoming {@link Intent}s.
     */
    public void setDisoveryListener(DiscoveryListener listener) {
        this.listener = listener;
    }

    /**
     * Enables the {@link Discovery} so that it will monitor the network for
     * {@link Intent}s and notify the given {@link DiscoveryListener} instance.
     *
     * This is a shortcut for:
     * <code>
     * discovery.setDiscoveryListener(listener);
     * discovery.enable();
     * </code>
     *
     * @param listener The {@link DiscoveryListener} that will be notified about
     *                 incoming {@link Intent}s.
     * @throws DiscoveryException
     */
    public void enable(DiscoveryListener listener) throws DiscoveryException {
        setDisoveryListener(listener);
        enable();
    }

    /**
     * Enables the {@link Discovery} so that it will monitor the network for
     * {@link Intent}s and notify the set {@link DiscoveryListener} instance.
     *
     * @throws DiscoveryException
     * @throws {@link IllegalStateException} if no listener has been set
     * @throws IllegalAccessError if this {@link Discovery} is already enabled
     */
    public void enable() throws DiscoveryException {
        if (listener == null) {
            throw new IllegalStateException("No listener set");
        }

        if (thread == null) {
            thread = createDiscoveryThread();
            thread.start();
        } else {
            throw new IllegalAccessError("Discovery already started");
        }
    }

    protected DiscoveryThread createDiscoveryThread() {
        return new DiscoveryThread(multicastAddress, port, listener);
    }

    /**
     * Disables the {@link Discovery}.
     *
     * @throws IllegalAccessError if this {@link Discovery} is not running
     */
    public void disable() {
        if (thread != null) {
            thread.stopDiscovery();
            thread = null;
        } else {
            throw new IllegalAccessError("Discovery not running");
        }
    }
}
