package com.example.NetworkPractice;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

public class MyActivity extends Activity implements View.OnClickListener {


    /**
     * Called when the activity is first created.
     */
    String address = "http://172.19.0.23/get_data.json";
    private Button sendRequest;
    private TextView cc;
    public static final int SHOW_RESPONSE = 0;

    private Handler handler = new Handler() {
        //HandleMessage方法对Message进行处理，最终把结果设置到TextView上
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    //在这里进行UI操作，将结果显示到界面上
                    cc.setText(response);
            }
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sendRequest = (Button) findViewById(R.id.send_request);
        cc = (TextView) findViewById(R.id.response);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sendReuestWithHttpClient();
    }

    private void sendReuestWithHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        //这里根据返回的内容具体的逻辑
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        //将服务器返回的结果存放到Message中
                        message.obj = response.toString();
                        //Handler将它发送出去
                        handler.sendMessage(message);
                        Gson gson = new Gson();
                        App Person = gson.fromJson(response.toString(),App.class);
                        Log.d("MainActivity", "id is " + Person.getId());
                        Log.d("MainActivity", "name is " + Person.getName());
                        Log.d("MainActivity", "version is " + Person.getage());

                    }
                    @Override
                    public void onError(Exception e) {
                        //在这里对异常进行处理
                        Log.d("MainActvity", "id is Error");
                    }
                });
            }
        }).start();
    }
}
