package com.example.meyki.fistapp;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Meyki on 2017/2/22.
 */

public class MainActivity extends AppCompatActivity {
    private ImageView iv_avatar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        Intent intent = getIntent();
        String alias =  intent.getStringExtra("alias");
        String password = intent.getStringExtra("password");
        String avatar = intent.getStringExtra("avatar");
        Log.d("msg", "onCreate: "+avatar);
        if(avatar!=null){
            Glide.with(this)
                    .load(Contants.base_url+Contants.avatar_path+avatar)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(iv_avatar);
        }

    }
}
