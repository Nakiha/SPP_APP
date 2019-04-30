package com.nakiha.release.UtilsClasss;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BluetoothUtils {

    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    public static ArrayList<String> getPairedDevicesInfo(BluetoothAdapter adapter) {
        ArrayList<String> nameAndMACList = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        if (pairedDevices.size() > 0){
            for (BluetoothDevice device : pairedDevices){
                nameAndMACList.add(device.getName()+"\n"+device.getAddress());
            }
            return nameAndMACList;
        }else {
            return null;
        }
    }

    public static void sendMessage(String message, BluetoothSocket _socket){
        int i=0;
        int n=0;
        try{
            OutputStream os = _socket.getOutputStream();   //蓝牙连接输出流
            byte[] bos = message.getBytes();
            for(i=0;i<bos.length;i++){
                if(bos[i]==0x0a)n++;
            }
            byte[] bos_new = new byte[bos.length+n];
            n=0;
            for(i=0;i<bos.length;i++){ //手机中换行为0a,将其改为0d 0a后再发送
                if(bos[i]==0x0a){
                    bos_new[n]=0x0d;
                    n++;
                    bos_new[n]=0x0a;
                }else{
                    bos_new[n]=bos[i];
                }
                n++;
            }

            os.write(bos_new);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void requireDiscoverableTime(Context from, int time){
        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, time);
        from.startActivity(discoverableIntent);
    }



}
