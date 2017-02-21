package com.example.meyki.fistapp;

import android.util.Log;

import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttSimpleCallback;

import java.io.Serializable;

/**
 * Created by Meyki on 2017/2/18.
 */

public class SubscribeClient implements Serializable {
    public   String CONNECTION_STRING = "tcp://10.0.0.40:1884";
    private final static boolean CLEAN_START = true;
    private final static short KEEP_ALIVE = 10;//低耗网络，但是又需要及时获取数据，心跳30s
    private final static String CLIENT_ID = "client";
    private final static String[] TOPICS = {
            "user/getPic",
            "other"
    };
    private final static int[] QOS_VALUES = {0,1};
    private MqttClient mqttClient = null;
    public SubscribeClient(String i,String picstr,String bs){

        try {
            //connect servers
            mqttClient = new MqttClient(CONNECTION_STRING);

            mqttClient.connect(CLIENT_ID+i, CLEAN_START, KEEP_ALIVE);
            SimpleCallbackHandler simpleCallbackHandler = new SimpleCallbackHandler();
            mqttClient.registerSimpleHandler(simpleCallbackHandler);//注册接收消息方法
            mqttClient.subscribe(TOPICS, QOS_VALUES);//订阅接主题
           // mqttClient.subscribe(,QOS_VALUES);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 完成订阅后，可以增加心跳，保持网络通畅，也可以发布自己的消息
         */

        try {
            if (picstr!=null){
                mqttClient.publish("publishmsg", picstr.getBytes(), QOS_VALUES[0], true);
                mqttClient.publish("publishmsg2",bs.getBytes(),QOS_VALUES[0], true);
               mqttClient.publish("user/getPic","test".getBytes(),QOS_VALUES[0],true);
            }


        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public MqttClient getMqttClient(){
        if(mqttClient!=null){
            return mqttClient;
        }
        else
            return null;

    }
    /**
     * 简单回调函数，处理client接收到的主题消息
     * @author pig
     *
     */
    class SimpleCallbackHandler implements MqttSimpleCallback {

        /**
         * 当客户机和broker意外断开时触发
         * 可以再此处理重新订阅
         */
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
            Log.d("msg", "消息级别(0,1,2): " + Qos);
            Log.d("msg", "是否是实时发送的消息(false=实时，true=服务器上保留的最后消息): " + retained);
        }

    }
}
