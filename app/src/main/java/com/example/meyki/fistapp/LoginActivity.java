package com.example.meyki.fistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ibm.mqtt.MqttClient;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Meyki on 2017/2/21.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //T data;
    private SubscribeClient subscribeClient;
    private MqttClient mqttClient;
    private EditText et_user,et_pass;
    private Button bt_login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //eventbus注册
       //EventBus.getDefault().register(this);
        et_user = (EditText) findViewById(R.id.et_user);
        et_pass = (EditText) findViewById(R.id.et_pass);
        bt_login = (Button) findViewById(R.id.bt_login);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                subscribeClient = new SubscribeClient("1","","");
//                mqttClient = subscribeClient.getMqttClient();
//                try {
//                    if(mqttClient!=null){
//                        Log.d("msg", "onClick: "+et_user.getText().toString());
//                        mqttClient.publish("user/getPic",et_user.getText().toString().getBytes(),Contants.QOS_VALUES[0],true);
//                        mqttClient.subscribe(Contants.TOPICS, Contants.QOS_VALUES);
//
//                    }
//
//                } catch (MqttException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        bt_login.setOnClickListener(this);
    }
//    @Subscribe(threadMode = ThreadMode.MAIN,priority = 100)
//    public void onEventMainThread(SubscribeClient data){
//        Log.d("msg", "onEventMainThread: "+data);
//
//
//    }
    @Override
    public void onClick(View v) {

        JSONObject jsonObject = new JSONObject();
        String useralias = et_user.getText().toString();
        String password = et_pass.getText().toString();



        if(useralias.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(),"用户名或者密码不能为空",Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                jsonObject.put("alias", et_user.getText().toString());
                jsonObject.put("password", et_pass.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new RetrofitUtil().init(Contants.base_url)
                    .setListener(new RetrofitUtil.OnGetJsonListener() {

                        @Override
                        public void getJson(String json) {
                            Log.d("msg", "login----json----" + json);
                            try {
                                JSONObject jsonobj = new JSONObject(json);
                                String status = jsonobj.getString("status");
                                String msg = jsonobj.getString("msg");
                                String avatar = jsonobj.getString("avatar");
                                if (status.equals("200")) {
                                    //跳转成功页面

                                    Log.d("msg", "login page ---->: "+avatar);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("alias", et_user.getText().toString());
                                    intent.putExtra("password", et_pass.getText().toString());
                                    intent.putExtra("avatar",avatar);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }).downData(null, jsonObject, 3);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册
        //EventBus.getDefault().unregister(this);
    }

}
