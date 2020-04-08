package com.example.mytest.Fragment4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mytest.Fragment2.MyFragment2;
import com.example.mytest.IP;
import com.example.mytest.MainActivity;
import com.example.mytest.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyFragment4 extends Fragment {

    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mfg4_content, container, false);
        TextView newsContent_1 = (TextView) view.findViewById(R.id.news_content_1);
        newsContent_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Set1Activity.class);
                startActivity(intent);
            }
        });
        TextView newsContent_2 = (TextView) view.findViewById(R.id.news_content_2);
        newsContent_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Set2Activity.class);
                startActivity(intent);
            }
        });
        TextView newsContent_3 = (TextView) view.findViewById(R.id.news_content_3);
        newsContent_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Set3Activity.class);
                startActivity(intent);
            }
        });
        TextView newsContent_6 = (TextView) view.findViewById(R.id.news_content_4);
        newsContent_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Intent intent=new Intent(getActivity(),Set4Activity.class);
//               startActivity(intent);

                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(getActivity());
                normalDialog.setIcon(R.drawable.tab_menu_better);
                normalDialog.setTitle("我是一个普通Dialog");
                normalDialog.setMessage("确认退出吗？");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finishAffinity();
                                System.exit(0);
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                            }
                        });
                // 显示
                normalDialog.show();

            }
        });
        TextView newsContent_7 = (TextView) view.findViewById(R.id.news_content_5);
        newsContent_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Intent intent=new Intent(getActivity(),Set4Activity.class);
//               startActivity(intent);

                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(getActivity());
                normalDialog.setIcon(R.drawable.tab_menu_better);
                normalDialog.setTitle("我是一个普通Dialog");
                normalDialog.setMessage("确认注销吗？");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Thread t=new Thread() {
                                    @Override
                                    public void run() {
                                        try {

                                            Socket socket = new Socket(IP.ip, 8883);

                                            PrintStream ps = new PrintStream(socket.getOutputStream());
                                            ps.println(MainActivity.user);
                                            ps.flush();

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

                                getActivity().finishAffinity();
                                System.exit(0);
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                            }
                        });
                // 显示
                normalDialog.show();

            }
        });
        TextView newsContent_4 = (TextView) view.findViewById(R.id.news_title_3);
        newsContent_4.setText("安全云");
        TextView newsContent_5 = (TextView) view.findViewById(R.id.news_title_4);
        newsContent_5.setText("欢迎使用！");

        return view;
    }


}