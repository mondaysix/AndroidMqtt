package com.example.meyki.fistapp;

import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import java.io.IOException;



public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn,btn2,btn_code,btn_login;
    private ImageView imageView;
    private int REQUESTCODE = 100;
    //private MediaType JSON = MediaType.parse("application/json");
    private String picstr,s,picname;
    private EditText et_email,et_pwd,et_alias,et_code;
    private String email_code;
    private SubscribeClient subscribeClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        btn_code = (Button) findViewById(R.id.btn_code);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_alias = (EditText) findViewById(R.id.et_alias);
        et_code = (EditText) findViewById(R.id.et_code);
        imageView = (ImageView) findViewById(R.id.image);

        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn_code.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //发送验证码
            case R.id.btn_code:
                final String email = et_email.getText().toString();
                Log.d("msg", "email"+email);
                //验证邮箱格式是否正确
                if(emailValidation(email)) {
                    new RetrofitUtil().init(Contants.base_url)
                            .setListener(new RetrofitUtil.OnGetJsonListener() {
                                @Override
                                public void getJson(String json) {
                                    Log.d("msg", json);
                                    try {
                                        JSONObject jsonObject = new JSONObject(json);
                                        String status = jsonObject.getString("status");
                                        String code = jsonObject.getString("code");
                                        Log.d("msg", "getJson: "+status+"---"+code);
                                        email_code = code;
                                        et_code.setText(email_code+"");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).downData(Contants.user_code+email,null,1);
                }else {
                    Log.d("msg", "email invalid...");
                }
                break;
            //上传图片
            case R.id.btn:
                final Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,100);
                break;
            //提交注册信息
            case R.id.btn2:
                //register
                Log.d("msg", "onClick: ");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        subscribeClient = new SubscribeClient("0",picstr,s);
                    }
                }).start();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userEmail",et_email.getText().toString());
                    jsonObject.put("code",et_code.getText().toString());
                    jsonObject.put("password",et_pwd.getText().toString());
                    jsonObject.put("alias",et_alias.getText().toString());
                    jsonObject.put("picname",picname);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new RetrofitUtil().init(Contants.base_url)
                        .setListener(new RetrofitUtil.OnGetJsonListener() {
                            @Override
                            public void getJson(String json) {
                                Log.d("msg", "------------->>>>> "+json);
                                try {
                                    JSONObject jsonObject1 = new JSONObject(json);
                                    String status = jsonObject1.getString("status");
                                    Log.d("msg", "-------->"+status);
                                    if(status.equals("200")){
                                        //跳转页面------login
                                        userLogin();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"注册失败，重新填写数据",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .downData(null,jsonObject,2);
                break;
            case R.id.btn_login:
                //login 页面
                userLogin();

                break;
        }


    }
    public void userLogin(){

        Intent intent1 = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent1);
    }
    public boolean emailValidation(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get picture
        if (requestCode == REQUESTCODE){

            Uri uri = data.getData();
            String path = uri.getPath();
            String[] pathname = path.split("/Camera/");

            Log.d("msg", "onActivityResult: "+pathname[1]);

            try {
                final Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);
                ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,bytestream);
                s = Base64.encodeToString(bytestream.toByteArray(), Base64.DEFAULT);
                 picname=pathname[1];
                 picstr = "{" +
                         "\"picname\":"+"\""+picname+"\""+
                         "}";
                Log.d("msg", picstr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
