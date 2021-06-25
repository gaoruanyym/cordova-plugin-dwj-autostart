package com.dwj.autostart.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import com.dwj.autostart.util.LogToFile;

/**
 * 开机广播
 */
public class BootReceiver extends BroadcastReceiver {

    private final static String TAG = "BootReceiver";

    private Timer timer;

    private PackageManager pm;

    @Override
    public void onReceive(Context context, Intent intent) {
        LogToFile.init(context);
        LogToFile.i(TAG,"接收广播" + intent.getAction());
        if(timer == null) {
            Toast.makeText(context,"已开机，正在启动指挥调度平台",Toast.LENGTH_LONG).show();
        }
        pm = context.getPackageManager();

        if(timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try{
                        Intent zhddintent = pm.getLaunchIntentForPackage("com.zhdd.screen");
                        context.startActivity(zhddintent);
                    }catch (Exception e) {
                        LogToFile.e(TAG,"错误：" + e.getMessage());
                    }
                }
            },5000);
        }
    }
}
