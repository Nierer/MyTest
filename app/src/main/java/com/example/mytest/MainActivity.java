package com.example.mytest;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity{
    static public String user;
    String str_1;
    String str_2;
    public static String name;
    EditText editText_1;
    EditText editText_2;
    String line=null;

    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_1=findViewById(R.id.button_1);
        button_1.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               editText_1=findViewById(R.id.editText_1);
               editText_2=findViewById(R.id.editText_2);
               str_1=editText_1.getText().toString();
               str_2=editText_2.getText().toString();
               name=str_1;

               if("".equals(str_1)||"".equals(str_2)){
                   Toast.makeText(v.getContext(), "不可为空！请重试！", Toast.LENGTH_SHORT).show();
               }
              else {

                   Thread t=new Thread() {
                       @Override
                       public void run() {
                           try {
                               Socket socket = new Socket(IP.ip, 8884);
                               PrintStream ps = new PrintStream(socket.getOutputStream());
                               ps.println(str_1);
                               ps.flush();

                               ps.println(str_2);
                               ps.flush();

                               BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                               line = br.readLine();
                               user=line;

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
                       Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                       startActivity(intent);
                       Toast.makeText(MainActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                   }
                   else{
                       Toast.makeText(v.getContext(), "账号或密码错误！请重试！", Toast.LENGTH_SHORT).show();
                   }


               }

           }
        });

        Button button_2=findViewById(R.id.button_2);
        button_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,RegisteredActivity.class);
                startActivity(intent);

            }
        });
    }
}
