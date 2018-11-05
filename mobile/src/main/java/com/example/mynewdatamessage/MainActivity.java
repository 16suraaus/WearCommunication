package com.example.mynewdatamessage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    GoogleApiClient googleApiClient = null;
    public static final String TAG = "MyDataMAP.....";
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(Wearable.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        googleApiClient = builder.build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();

        }
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        sendMessage();
    }

    public void sendMessage() {

        if (googleApiClient.isConnected()) {
            Log.d("connection test","google API client IS connected");
            String message = ((TextView) findViewById(R.id.text)).getText().toString();
            if (message == null || message.equalsIgnoreCase("")) {
                message = "Hello World!";
            }
            new SendMessageToDataLayer(WEARABLE_DATA_PATH, message).start();

        } else {

            Log.d("connection test","google API client not connected");

        }
    }

    public void sendMessageOnClick(View view) {
        sendMessage();
    }

    public class SendMessageToDataLayer extends Thread {
        String path;
        String message;

        public SendMessageToDataLayer(String path, String message) {
            this.path = path;
            this.message = message;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodesList = Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
            for (Node node : nodesList.getNodes()) {
                MessageApi.SendMessageResult messageResult = Wearable.MessageApi.sendMessage(googleApiClient, node.getId(), path, message.getBytes()).await();
                if (messageResult.getStatus().isSuccess()) {

                } else {

                }
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
