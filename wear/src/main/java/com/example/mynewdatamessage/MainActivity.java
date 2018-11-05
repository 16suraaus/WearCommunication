package com.example.mynewdatamessage;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mTextView= (TextView)findViewById(R.id.textHELLO);

        String message = getIntent().getStringExtra("message");
        if (message == null || message.equalsIgnoreCase("")) {
            message = "Hello World!!!!";
        }

        if(message==null)
        Log.d("hello   ","message is null");
        else
        {
            Log.d("hello","message is not null");
            Log.d("hello", message);

        }
        if(mTextView == null)
            Log.d("hello","mtextView is null");
        else
            Log.d("hello","mTextView is not Null");
        mTextView.setText(message);


    }
}


