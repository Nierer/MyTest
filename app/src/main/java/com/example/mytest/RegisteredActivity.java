package com.example.mytest;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class RegisteredActivity extends AppCompatActivity {
    String str_1;
    String str_2;
    String str_3;
    String result;
    String line;

    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           // Toast.makeText(RegisteredActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            }

        };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText_1 = findViewById(R.id.editText);
                EditText editText_2 = findViewById(R.id.editText_1);
                EditText editText_3 = findViewById(R.id.editText_2);
                str_1 = editText_1.getText().toString();
                str_2 = editText_2.getText().toString();
                str_3 = editText_3.getText().toString();
                RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton_1);
                RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton_2);

                if (rb1.isChecked()) {
                    result = "选项1";
                } else if (rb2.isChecked()) {
                    result = "选项2";
                }

                if ("".equals(str_1) || "".equals(str_2) || "".equals(result)) {
                    Toast.makeText(v.getContext(), "不可为空！请重试！", Toast.LENGTH_SHORT).show();
                    editText_1.setText("");
                    editText_2.setText("");
                    editText_3.setText("");
                } else if (str_2.equals(str_3)) {


                    Thread t=new Thread() {
                        @Override
                        public void run() {
                            try {

                                Socket socket = new Socket(IP.ip, 8885);

                                PrintStream ps = new PrintStream(socket.getOutputStream());
                                ps.println(str_1);
                                ps.flush();

                                ps.println(str_2);
                                ps.flush();

                                BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                                line = br.readLine();


                                Message msg = Message.obtain(); // 实例化消息对象
                                msg.what = 0x12; // 消息标识
                                handler.sendMessage(msg);

                                ps.close();
                                socket.close();

                            } catch (UnknownHostException e) {
                                e.printStackTrace();
                            }catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    t.start();

                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if("1".equals(line)) {
                        Toast.makeText(v.getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisteredActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(v.getContext(), "账号已存在！", Toast.LENGTH_SHORT).show();
                    }
                } else if (!str_2.equals(str_3)) {
                    Toast.makeText(v.getContext(), "密码不一致！请重试！", Toast.LENGTH_SHORT).show();
                    editText_1.setText("");
                    editText_2.setText("");
                    editText_3.setText("");
                }
            }
        });
    }
}
