package com.example.me.notificationsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Notification2Activity extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);
        textView=findViewById(R.id.textView);
        int number=getIntent().getIntExtra(MyReceiver.KEY_NUMBER,99);
        textView.setText(String.valueOf(number));
    }

}
