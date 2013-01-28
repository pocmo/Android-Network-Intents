package com.androidzeitgeist.ani.discovery;

import android.content.Intent;

/**
 * This adapter class provides empty implementations of the methods from
 * {@link DiscoveryListener}.
 *
 * Any custom listener that cares only about a subset of the methods of this listener
 * can simply subclass this adapter class instead of implementing the interface
 * directly.
 *
 * @author Sebastian Kaspari <s.kaspari@gmail.com>
 */
public abstract class DiscoveryAdapter implements DiscoveryListener {
    /**
     * Called when the {@link Discovery} has successfully received an {@link Intent}.
     *
     * @param intent The received {@link Intent}.
     */
    public abstract void onIntentDiscovered(Intent intent);

    /**
     * The {@link Discovery} has been started and is now waiting for incoming
     * {@link Intent}s.
     *
     * Empty default implementation.
     */
    public void onDiscoveryStarted() {
        // Empty default implementation
    }

    /**
     * The {@link Discovery} has been stopped.
     *
     * Empty default implementation.
     */
    public void onDiscoveryStopped() {
        // Empty default implementation
    }

    /**
     * An unrecoverable error occured. The {@link Discovery} is going to be stopped.
     *
     * Empty default implementation.
     */
    public void onDiscoveryError() {
        // Empty default implementation
    }
}
