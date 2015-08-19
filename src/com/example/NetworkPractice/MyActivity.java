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
        //HandleMessage������Message���д������հѽ�����õ�TextView��
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    //���������UI�������������ʾ��������
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
                        //������ݷ��ص����ݾ�����߼�
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        //�����������صĽ����ŵ�Message��
                        message.obj = response.toString();
                        //Handler�������ͳ�ȥ
                        handler.sendMessage(message);
                        Gson gson = new Gson();
                        App Person = gson.fromJson(response.toString(),App.class);
                        Log.d("MainActivity", "id is " + Person.getId());
                        Log.d("MainActivity", "name is " + Person.getName());
                        Log.d("MainActivity", "version is " + Person.getage());

                    }
                    @Override
                    public void onError(Exception e) {
                        //��������쳣���д���
                        Log.d("MainActvity", "id is Error");
                    }
                });
            }
        }).start();
    }
}
