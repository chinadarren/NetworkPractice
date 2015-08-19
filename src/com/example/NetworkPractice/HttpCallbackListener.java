package com.example.NetworkPractice;

/**
 * Created by cl on 2015/8/19.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
