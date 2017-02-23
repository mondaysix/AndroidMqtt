package com.example.meyki.fistapp;

/**
 * Created by Meyki on 2017/2/22.
 */

public interface Contants {
     String base_url = "http://10.0.0.40:1883/";
     String user_code = "user/regcode/";//get
     String user_reg = "user/register/";//post
    String user_login = "/user/login/";//post
    String avatar_path = "uploads/";
    int[] QOS_VALUES = {0,1};
    String[] TOPICS = {
            "user/getPic",
            "userpic"
    };
}
