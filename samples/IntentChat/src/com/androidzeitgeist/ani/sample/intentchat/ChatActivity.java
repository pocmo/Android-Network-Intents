package com.androidzeitgeist.ani.sample.intentchat;

import java.net.InetAddress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.androidzeitgeist.ani.discovery.Discovery;
import com.androidzeitgeist.ani.discovery.DiscoveryException;
import com.androidzeitgeist.ani.discovery.DiscoveryListener;
import com.androidzeitgeist.ani.transmitter.Transmitter;
import com.androidzeitgeist.ani.transmitter.TransmitterException;

public class ChatActivity extends Activity implements DiscoveryListener, OnEditorActionListener, OnClickListener  {
    private static final String EXTRA_MESSAGE = "message";

    private TextView chatView;
    private EditText inputView;
    private ImageButton sendButton;

    private Discovery discovery;
    private Transmitter transmitter;

    private boolean discoveryStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        discovery = new Discovery();
        discovery.setDisoveryListener(this);
        transmitter = new Transmitter();

        chatView = (TextView) findViewById(R.id.chat);

        inputView = (EditText) findViewById(R.id.input);
        inputView.setOnEditorActionListener(this);

        sendButton = (ImageButton) findViewById(R.id.send);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            discovery.enable();
            discoveryStarted = true;
        } catch (DiscoveryException exception) {
            appendChatMessage("* (!) Could not start discovery: " + exception.getMessage());
            discoveryStarted = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (discoveryStarted) {
            discovery.disable();
        }
    }

    private void appendChatMessage(final String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                chatView.append(message + "\n");
            }
        });
    }

    private void appendChatMessageFromSender(String sender, String message) {
        appendChatMessage("<" + sender + "> " + message);
    }

    @Override
    public void onDiscoveryError(Exception exception) {
        appendChatMessage("* (!) Discovery error: " + exception.getMessage());
    }

    @Override
    public void onDiscoveryStarted() {
        appendChatMessage("* (>) Discovery started");
    }

    @Override
    public void onDiscoveryStopped() {
        appendChatMessage("* (<) Discovery stopped");
    }

    @Override
    public void onIntentDiscovered(InetAddress address, Intent intent) {
        if (!intent.hasExtra(EXTRA_MESSAGE)) {
            appendChatMessage("* (!) Received Intent without message");
            return;
        }

        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String sender  = address.getHostName();

        appendChatMessageFromSender(sender, message);
    }

    @Override
    public void onClick(View v) {
        sendChatMessage();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendChatMessage();
            return true;
        }

        return false;
    }

    public void sendChatMessage() {
        String message = inputView.getText().toString();

        if (message.length() == 0) {
            return; // No message to send
        }

        inputView.setText("");

        Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE, message);

        transmitIntentOnBackgroundThread(intent);
    }

    private void transmitIntentOnBackgroundThread(final Intent intent) {
        new Thread() {
            public void run() {
                transmitIntent(intent); 
            }
        }.start();
    }

    private void transmitIntent(final Intent intent) {
        try {
            transmitter.transmit(intent);
        } catch (TransmitterException exception) {
            appendChatMessage("Could not transmit intent: " + exception.getMessage());
        }
    }
}
