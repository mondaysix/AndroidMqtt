package com.example.meyki.fistapp;


import android.util.Log;

import com.ibm.mqtt.MqttSimpleCallback;



/**
 * Created by Meyki on 2017/2/23.
 * 简单回调函数，处理client接收到的主题消息
 */


public class SimpleCallbackHandler implements MqttSimpleCallback {

    /**
     * 当客户机和broker意外断开时触发
     * 可以再此处理重新订阅
     */
    public String msg;

    @Override
    public void connectionLost() throws Exception {
        // TODO Auto-generated method stub
        System.out.println("客户机和broker已经断开");
    }

    /**
     * 客户端订阅消息后，该方法负责回调接收处理消息
     */
    @Override
    public void publishArrived(String topicName, byte[] payload, int Qos, boolean retained) throws Exception {
        // TODO Auto-generated method stub
        Log.d("msg", "订阅主题: " + topicName);
        Log.d("msg", "消息数据: " + new String(payload));
        String data = new String(payload);
        Log.d("msg", "消息级别(0,1,2): " + Qos);
        Log.d("msg", "是否是实时发送的消息(false=实时，true=服务器上保留的最后消息): " + retained);

    }
}
