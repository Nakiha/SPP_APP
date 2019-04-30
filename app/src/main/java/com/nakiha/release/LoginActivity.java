package com.nakiha.release;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.nakiha.release.ControlModule.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    TextView what_is_this_text;
    EditText account_name;
    EditText account_password;
    Button quickModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUIWidget();
        new AlertView("Demo阶段", getString(R.string.demo_stage), null, new String[]{"确定"}, null, this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
            }
        }).show();
    }

    private void initUIWidget(){
        quickModeButton = findViewById(R.id.quick_mode_button);
        account_name = findViewById(R.id.edit_account_name);
        account_password = findViewById(R.id.edit_account_password);
        what_is_this_text = findViewById(R.id.text_what_is_this);

        quickModeButton.setOnClickListener(this);
        what_is_this_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quick_mode_button:
                ViewDevicesActivity.actionStart(this);
                break;
            case R.id.text_what_is_this:
                new AlertView("这是什么?", getString(R.string.what_is_this_detail), null, new String[]{"确定"}, null, this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                    }
                }).show();
                default:break;
        }
    }
}
