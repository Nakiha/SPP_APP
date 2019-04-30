package com.nakiha.release;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.nakiha.release.ControlModule.BaseActivity;
import com.nakiha.release.UtilsClasss.BluetoothUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.UUID;

public class ControlRemoteCarActivity extends BaseActivity implements View.OnClickListener{
    private AlertView mAlertView;

    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    private InputStream is;    //输入流，用来接收蓝牙数据

    private BluetoothDevice _device = null;     //蓝牙设备
    private BluetoothSocket _socket = null;      //蓝牙通信socket

    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    //用于控制蓝牙输入流
    boolean bRun = true;
    boolean bThread = false;

    private String smsg = "";    //显示用数据缓存
    private String fmsg = "";    //保存用数据缓存

    private TextView testText;
    private ImageButton upButton;
    private ImageButton downButton;
    private ImageButton leftButton;
    private ImageButton rightButton;

    private String MAC;

    private Bundle buttonsBindData;
    private Bundle buttonsDeepBindData;

    private static final String[] KEYS = {"up", "down", "left", "right", "up_left", "up_right", "down_left", "down_right"};

    public static void actionStart(Context from,@Nullable Bundle dataNeedDeliver){
        Intent intent = new Intent(from, ControlRemoteCarActivity.class);
        if (dataNeedDeliver !=null)
            intent.putExtras(dataNeedDeliver);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_remote_car);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        resolveBundle();
        initUIWidgets();
    }


    private void resolveBundle(){
        Bundle passedData = getIntent().getExtras();
        if (passedData == null){
            Toast.makeText(this,"传递了空Bundle",Toast.LENGTH_SHORT).show();
            finish();
        }else
            MAC = passedData.getString("MAC");
    }
    private void connectToDevice(){
        _device = mBluetoothAdapter.getRemoteDevice(MAC);
        // 用服务号得到socket
        try{
            _socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
        }catch(IOException e){
            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
        }
        //连接socket
        try{
            _socket.connect();
            Toast.makeText(this, "连接"+_device.getName()+"成功！", Toast.LENGTH_SHORT).show();
        }catch(IOException e){
            try{
                Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                _socket.close();
                _socket = null;
            }catch(IOException ee){
                Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        //打开接收线程
        try{
            is = _socket.getInputStream();   //得到蓝牙数据输入流
        }catch(IOException e){
            Toast.makeText(this, "接收数据失败！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(bThread==false){
            ReadThread.start();
            bThread=true;
        }else{
            bRun = true;
        }
    }
    Thread ReadThread= new Thread(){

        public void run(){
            int num = 0;
            byte[] buffer = new byte[1024];
            byte[] buffer_new = new byte[1024];
            int i = 0;
            int n = 0;
            bRun = true;
            //接收线程
            while(true){
                try{
                    while(is.available()==0){
                        while(bRun == false);
                    }
                    while(true){
                        num = is.read(buffer);         //读入数据
                        n=0;

                        String s0 = new String(buffer,0,num);
                        fmsg+=s0;    //保存收到数据
                        for(i=0;i<num;i++){
                            if((buffer[i] == 0x0d)&&(buffer[i+1]==0x0a)){
                                buffer_new[n] = 0x0a;
                                i++;
                            }else{
                                buffer_new[n] = buffer[i];
                            }
                            n++;
                        }
                        String s = new String(buffer_new,0,n);
                        smsg+=s;   //写入接收缓存
                        if(is.available()==0)break;  //短时间没有数据才跳出进行显示
                    }
                    //发送显示消息，进行显示刷新
                    handler.sendMessage(handler.obtainMessage());
                }catch(IOException e){
                    finish();
                }
            }
        }
    };
    //消息处理队列
    @SuppressLint("HandlerLeak")
    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
        }
    };

    private void initUIWidgets(){
        testText = findViewById(R.id.test_text);

        upButton = findViewById(R.id.go_up_button);
        downButton = findViewById(R.id.go_down_button);
        leftButton = findViewById(R.id.go_left_button);
        rightButton = findViewById(R.id.go_right_button);

        testText.setOnClickListener((v)->{connectToDevice();});
        upButton.setOnClickListener(this);
        downButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (buttonsBindData!=null)
            switch (v.getId()){
                case R.id.go_up_button:
                    BluetoothUtils.sendMessage(buttonsBindData.getString(KEYS[0]), _socket);
                    break;
                case R.id.go_down_button:
                    BluetoothUtils.sendMessage(buttonsBindData.getString(KEYS[1]), _socket);
                    break;
                case R.id.go_left_button:
                    BluetoothUtils.sendMessage(buttonsBindData.getString(KEYS[2]), _socket);
                    break;
                case R.id.go_right_button:
                    BluetoothUtils.sendMessage(buttonsBindData.getString(KEYS[3]), _socket);
                    break;

            }
        else {
            Toast.makeText(this,"对按钮进行设置",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_control_car_page, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_setting:
                buttonsBindData = getButtonsSendData();
                break;
            case R.id.menu_item_deep_setting:
                buttonsDeepBindData = getDeepButtonsSendData();
                break;
            default:break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(_socket!=null)  //关闭连接socket
            try{
                _socket.close();
            }catch(IOException e){}
    }

    //下面两个方法整合起来太麻烦
    private Bundle getButtonsSendData() {
        ViewGroup extendView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alert_car_button_setting, null);
        Bundle buttonsBindData = new Bundle();
        OnItemClickListener listener = (itemObject, position) -> {
            EditText[] editTextList = new EditText[4];
            editTextList[0] = findViewById(R.id.go_up_button_setting);
            editTextList[1] = findViewById(R.id.go_down_button_setting);
            editTextList[2] = findViewById(R.id.go_left_button_setting);
            editTextList[3] = findViewById(R.id.go_right_button_setting);
            boolean hasInputEmpty = false;
            for (int i = 0; i < 4; i++) {
                EditText editText_Temp = editTextList[i];
                String temp = editText_Temp.getText().toString();
                if (TextUtils.isEmpty(temp)) {
                    hasInputEmpty = true;
                    buttonsBindData.putString(KEYS[i], "0");
                } else
                    buttonsBindData.putString(KEYS[i], temp);
            }
            if (position == 1 && hasInputEmpty) {
                Toast.makeText(this, "存在空内容, 其对应信息将被设为默认值0", Toast.LENGTH_SHORT).show();
            } else {
                mAlertView.dismiss();
            }
        };
        mAlertView = new AlertView("按钮设置", "请输入对应按钮要发送的信息内容", null
                ,  new String[]{"取消","完成"}, null, this, AlertView.Style.Alert,listener);
        mAlertView.addExtView(extendView);
        mAlertView.show();
        return buttonsBindData;
    }
    private Bundle getDeepButtonsSendData() {
        ViewGroup extendView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alert_car_deep_button_setting, null);
        Bundle buttonsBindData = new Bundle();
        OnItemClickListener listener = (itemObject, position) -> {
            EditText[] editTextList = new EditText[4];
            editTextList[0] = findViewById(R.id.go_up_left_button_setting);
            editTextList[1] = findViewById(R.id.go_down_left_button_setting);
            editTextList[2] = findViewById(R.id.go_up_left_button_setting);
            editTextList[3] = findViewById(R.id.go_down_right_button_setting);
            if (position == 1) {
                boolean hasInputEmpty = false;
                for (int i = 0; i < 4; i++) {
                    EditText editText_Temp = editTextList[i];
                    String temp = editText_Temp.getText().toString();
                    if (TextUtils.isEmpty(temp)) {
                        hasInputEmpty = true;
                        buttonsBindData.putString(KEYS[i], "0");
                    } else
                        buttonsBindData.putString(KEYS[i], temp);
                }
                if (hasInputEmpty)
                    Toast.makeText(this, "存在空内容, 其对应信息将被设为默认值0", Toast.LENGTH_SHORT).show();
            } else {
                mAlertView.dismiss();
            }
        };
        mAlertView = new AlertView("按钮设置", "请输入对应按钮要发送的信息内容", null
                ,  new String[]{"取消","完成"}, null, this, AlertView.Style.Alert,listener);
        mAlertView.addExtView(extendView);
        mAlertView.show();
        return buttonsBindData;
    }
}
