package com.nakiha.release;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nakiha.release.ControlModule.BaseActivity;

public class ChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
}
