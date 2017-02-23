package com.example.meyki.fistapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Meyki on 2017/2/23.
 */

public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.app.userinfo.pic")){
            String datas = intent.getStringExtra("data");
            Log.d("msg", "onReceive: ---->login"+intent.getStringExtra("data"));
            context.startActivity(intent);
            //avatar = intent.getStringExtra("data");
        }
    }
}
