package com.example.meyki.fistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ibm.mqtt.MqttClient;

import java.io.Serializable;

/**
 * Created by Meyki on 2017/2/21.
 */

public class LoginActivity extends AppCompatActivity {
    private MqttClient mqttClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        SubscribeClient subscribeClient = (SubscribeClient) extras.getSerializable("mqtt");
        if (subscribeClient!=null){
            Log.d("msg", "onCreate: "+subscribeClient);
             mqttClient = subscribeClient.getMqttClient();
        }
    }
}
