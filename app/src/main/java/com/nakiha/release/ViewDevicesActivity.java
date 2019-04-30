package com.nakiha.release;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.nakiha.release.ControlModule.BaseActivity;
import com.nakiha.release.DatabaseModule.DeviceData;
import com.nakiha.release.RecyclerViewAdapter.DeviceAdapter;
import com.nakiha.release.UIWidgets.TitleLayout;
import com.nakiha.release.UtilsClasss.BluetoothUtils;
import com.nakiha.release.UtilsClasss.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ViewDevicesActivity extends BaseActivity implements View.OnClickListener {
    private final static String DEFAULT_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    Button deleteDevicesButton;
    Button addDevicesButton;

    BluetoothAdapter mAdapter;

    private final static int REQUEST_ENABLE_BT = 0XABCD0000;

    private List<DeviceData> mDeviceDataList = new ArrayList<>();
    TitleLayout mTitleLayout;

    public static void actionStart(Context from){
        from.startActivity(new Intent(from, ViewDevicesActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_devices);
        initBluetoothService();
        initUIWidgets();
    }

    //初始化方法
    private void initUIWidgets(){
        addDevicesButton = findViewById(R.id.add_devices_button);
        deleteDevicesButton = findViewById(R.id.delete_devices_button);

        deleteDevicesButton.setVisibility(View.GONE);
        addDevicesButton.setOnClickListener(this);
        initTitleLayout();
        initRecyclerList();
    }

    private void initBluetoothService(){
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter==null) {
            Toast.makeText(this,"设备未载有蓝牙设备", Toast.LENGTH_SHORT).show();
            finish();
        }else if (!mAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        BluetoothUtils.requireDiscoverableTime(this, 300);
    }

    private void initTitleLayout(){
        mTitleLayout = findViewById(R.id.titleLayout);
        mTitleLayout.setHeadImage_TEMP(R.mipmap.demo_head);
        mTitleLayout.setAccountNameText("测试员");
        try {
            mTitleLayout.setModeText(TitleLayout.QUICK_MODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerList(){
        initDeviceDataList();
        RecyclerView recyclerView = findViewById(R.id.control_devices_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(24));
        DeviceAdapter adapter = new DeviceAdapter(mDeviceDataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemViewCacheSize(15);
    }

    private void initDeviceDataList(){
        DeviceData test1 = new DeviceData(R.mipmap.test_p1,"迈凯伦 P1", "列表1号", "mac大头鬼");
        DeviceData test2 = new DeviceData(R.mipmap.test_egoista, "兰博基尼 egoista", "列表2号", "???");
        mDeviceDataList.add(test1);
        mDeviceDataList.add(test2);

        //加入小车
        DeviceData remoteCar = new DeviceData(R.mipmap.remote_car, "HC-06", "遥控小车", "20:18:09:28:51:60");
        Bundle data = new Bundle();
        data.putString("MAC", "20:18:09:28:51:60");
        remoteCar.setLeftButton((v)->{ControlRemoteCarActivity.actionStart(this, data);}, "控制页面");
        mDeviceDataList.add(remoteCar);

        mDeviceDataList.add(test1);
        mDeviceDataList.add(test2);
        mDeviceDataList.add(test1);
        mDeviceDataList.add(test2);
    }

    //回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(!mAdapter.isEnabled()){
                    Toast.makeText(this,"开启蓝牙请求被拒绝", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_devices_button:
                new AlertView("无效的按钮", "功能善待开发\n您可以通过暴打田田田来加速开发", null, new String[]{"确定"}, null, this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                    }
                }).show();
                break;
                default:break;
        }

    }

    /*private void EditAlertView() {
        //拓展窗口
        //ViewGroup extView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form, null);

        AlertView mAlertViewExt = new AlertView("提示", "！", "取消", null, new String[]{"完成"}, this, AlertView.Style.Alert,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {

                    }
                });
        mAlertViewExt.show();
        mAlertViewExt.addExtView(extView);
    }*/
}
