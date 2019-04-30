package com.nakiha.release.ControlModule;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ForceOffLineReceiver extends BroadcastReceiver {
    public final static String BroadcastTAG = "C:\\Users\\Zhu\\Desktop\\bank\\ReLease2\\app\\src\\main\\java\\com\\nakiha\\release\\ControlModule\\ForceOffLineReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = "警告";
        String message = "您被强制下线";
        builtAlertDialog(context, title, message);
    }

    private void builtAlertDialog(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok",(dialog, which)->{
            ActivityCollector.finishAll();

        } );

    }
}
