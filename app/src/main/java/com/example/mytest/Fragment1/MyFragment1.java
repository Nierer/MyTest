package com.example.mytest.Fragment1;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.mytest.IP;
import com.example.mytest.R;
import com.example.mytest.Sql;
import com.example.mytest.information;
import com.example.mytest.t_desc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MyFragment1 extends Fragment {

 public Button rb_1,rb_2,rb_3,rb_4,rb_5,rb_6,rb_7,rb_8,rb_9,rb_10,rb_11;
 String line=null,line_2=null;
    Context mContext;
    TextView time;
    AlertDialog alert = null;
    AlertDialog.Builder builder = null;
    static List<com.example.mytest.information> information = new ArrayList<>();
    static List<t_desc> list;

    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();//获取系统时间
                    CharSequence sysTimeStr = DateFormat.format("yyyy-MM-dd HH:mm:ss", sysTime);//时间显示格式
                    time.setText(sysTimeStr); //更新时间
                    break;
                default:
                    break;

            }

        }

    };


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mfg1_content, container, false);

        rb_1=(Button) view.findViewById(R.id.button_1);
        rb_2=(Button) view.findViewById(R.id.button_2);
        rb_3=(Button) view.findViewById(R.id.button_3);
        rb_4=(Button) view.findViewById(R.id.button_4);
        rb_5=(Button) view.findViewById(R.id.button_5);
        rb_6=(Button) view.findViewById(R.id.button_6);
        rb_7=(Button) view.findViewById(R.id.button_7);
        rb_8=(Button) view.findViewById(R.id.button_8);
        rb_9=(Button) view.findViewById(R.id.button_9);
        rb_10=(Button) view.findViewById(R.id.button_10);
        rb_11=(Button) view.findViewById(R.id.button_11);

        time=(TextView)view.findViewById(R.id.time_2);

        mContext = getActivity();

        Thread tt=new Thread(){
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    handler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
        };
        tt.start(); //启动显示时间线程

        rb_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t=new Thread() {
                    @Override
                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByName("ff00030005000001");
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                                try {

                                    Socket socket = new Socket(IP.ip, 8888);

                                    PrintStream ps = new PrintStream(socket.getOutputStream());
                                    ps.println("test_1");
                                    ps.flush();

                                    BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                                    line = br.readLine();
                                    line_2=br.readLine();

                                    list = JSONArray.parseArray(line, t_desc.class);
                                    information = JSONArray.parseArray(line_2, information.class);

                                    Message msg = Message.obtain(); // 实例化消息对象
                                    msg.what = 0x12; // 消息标识
                                    msg.obj=line; //消息内容
                                    handler.sendMessage(msg);

                                    ps.close();
                                    br.close();
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
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                //
                alert = builder.setTitle("监测点信息：")
                        .setMessage("名称："+information.get(0).getDevEUI()+"\nx 值："+information.get(0).getX()+"\ny 值:"+information.get(0).getY()+"\n温度:"+information.get(0).getTemperature()+"\n电压:"+information.get(0).getVoltage()+"\n时间:"+information.get(0).getTem_time())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();             //创建AlertDialog对象

                alert.show();                    //显示对话框

            }
        });

        rb_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t=new Thread() {
                    @Override
                    public void run() {
                        try {

                            Socket socket = new Socket(IP.ip, 8888);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println("test_1");
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            line = br.readLine();
                            line_2=br.readLine();

                            list = JSONArray.parseArray(line, t_desc.class);
                            information = JSONArray.parseArray(line_2, information.class);

                            Message msg = Message.obtain(); // 实例化消息对象
                            msg.what = 0x12; // 消息标识
                            msg.obj=line; //消息内容
                            handler.sendMessage(msg);

                            ps.close();
                            br.close();
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
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                //
                alert = builder.setTitle("监测点信息：")
                        .setMessage("名称："+information.get(1).getDevEUI()+"\nx 值："+information.get(1).getX()+"\ny 值:"+information.get(1).getY()+"\n温度:"+information.get(1).getTemperature()+"\n电压:"+information.get(1).getVoltage()+"\n时间:"+information.get(1).getTem_time())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();             //创建AlertDialog对象

                alert.show();                    //显示对话框

            }
        });

        rb_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t=new Thread() {
                    @Override
                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByName("ff00030005000001");
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        try {

                            Socket socket = new Socket(IP.ip, 8888);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println("test_1");
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            line = br.readLine();
                            line_2=br.readLine();

                            list = JSONArray.parseArray(line, t_desc.class);
                            information = JSONArray.parseArray(line_2, information.class);

                            Message msg = Message.obtain(); // 实例化消息对象
                            msg.what = 0x12; // 消息标识
                            msg.obj=line; //消息内容
                            handler.sendMessage(msg);

                            ps.close();
                            br.close();
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
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                //
                alert = builder.setTitle("监测点信息：")
                        .setMessage("名称："+information.get(2).getDevEUI()+"\nx 值："+information.get(2).getX()+"\ny 值:"+information.get(2).getY()+"\n温度:"+information.get(2).getTemperature()+"\n电压:"+information.get(2).getVoltage()+"\n时间:"+information.get(2).getTem_time())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();             //创建AlertDialog对象

                alert.show();                    //显示对话框

            }
        });


        rb_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t=new Thread() {
                    @Override
                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByName("ff00030005000001");
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        try {

                            Socket socket = new Socket(IP.ip, 8888);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println("test_1");
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            line = br.readLine();
                            line_2=br.readLine();

                            list = JSONArray.parseArray(line, t_desc.class);
                            information = JSONArray.parseArray(line_2, information.class);

                            Message msg = Message.obtain(); // 实例化消息对象
                            msg.what = 0x12; // 消息标识
                            msg.obj=line; //消息内容
                            handler.sendMessage(msg);

                            ps.close();
                            br.close();
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
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                //
                alert = builder.setTitle("监测点信息：")
                        .setMessage("名称："+information.get(3).getDevEUI()+"\nx 值："+information.get(3).getX()+"\ny 值:"+information.get(3).getY()+"\n温度:"+information.get(3).getTemperature()+"\n电压:"+information.get(3).getVoltage()+"\n时间:"+information.get(3).getTem_time())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();             //创建AlertDialog对象

                alert.show();                    //显示对话框

            }
        });


        rb_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t=new Thread() {
                    @Override
                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByName("ff00030005000001");
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        try {

                            Socket socket = new Socket(IP.ip, 8888);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println("test_1");
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            line = br.readLine();
                            line_2=br.readLine();

                            list = JSONArray.parseArray(line, t_desc.class);
                            information = JSONArray.parseArray(line_2, information.class);

                            Message msg = Message.obtain(); // 实例化消息对象
                            msg.what = 0x12; // 消息标识
                            msg.obj=line; //消息内容
                            handler.sendMessage(msg);

                            ps.close();
                            br.close();
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
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                //
                alert = builder.setTitle("监测点信息：")
                        .setMessage("名称："+information.get(4).getDevEUI()+"\nx 值："+information.get(4).getX()+"\ny 值:"+information.get(4).getY()+"\n温度:"+information.get(4).getTemperature()+"\n电压:"+information.get(4).getVoltage()+"\n时间:"+information.get(4).getTem_time())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();             //创建AlertDialog对象

                alert.show();                    //显示对话框

            }
        });


        rb_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t=new Thread() {
                    @Override
                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByName("ff00030005000001");
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        try {

                            Socket socket = new Socket(IP.ip, 8888);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println("test_1");
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            line = br.readLine();
                            line_2=br.readLine();

                            list = JSONArray.parseArray(line, t_desc.class);
                            information = JSONArray.parseArray(line_2, information.class);

                            Message msg = Message.obtain(); // 实例化消息对象
                            msg.what = 0x12; // 消息标识
                            msg.obj=line; //消息内容
                            handler.sendMessage(msg);

                            ps.close();
                            br.close();
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
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                //
                alert = builder.setTitle("监测点信息：")
                        .setMessage("名称："+information.get(5).getDevEUI()+"\nx 值："+information.get(5).getX()+"\ny 值:"+information.get(5).getY()+"\n温度:"+information.get(5).getTemperature()+"\n电压:"+information.get(5).getVoltage()+"\n时间:"+information.get(5).getTem_time())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();             //创建AlertDialog对象

                alert.show();                    //显示对话框

            }
        });

        rb_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t=new Thread() {
                    @Override
                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByName("ff00030005000001");
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        try {

                            Socket socket = new Socket(IP.ip, 8888);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println("test_1");
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            line = br.readLine();
                            line_2=br.readLine();

                            list = JSONArray.parseArray(line, t_desc.class);
                            information = JSONArray.parseArray(line_2, information.class);

                            Message msg = Message.obtain(); // 实例化消息对象
                            msg.what = 0x12; // 消息标识
                            msg.obj=line; //消息内容
                            handler.sendMessage(msg);

                            ps.close();
                            br.close();
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
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                //
                alert = builder.setTitle("监测点信息：")
                        .setMessage("名称："+information.get(6).getDevEUI()+"\nx 值："+information.get(6).getX()+"\ny 值:"+information.get(6).getY()+"\n温度:"+information.get(6).getTemperature()+"\n电压:"+information.get(6).getVoltage()+"\n时间:"+information.get(6).getTem_time())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();             //创建AlertDialog对象

                alert.show();                    //显示对话框

            }
        });

        rb_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t=new Thread() {
                    @Override
                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByName("ff00030005000001");
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        try {

                            Socket socket = new Socket(IP.ip, 8888);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println("test_1");
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            line = br.readLine();
                            line_2=br.readLine();

                            list = JSONArray.parseArray(line, t_desc.class);
                            information = JSONArray.parseArray(line_2, information.class);

                            Message msg = Message.obtain(); // 实例化消息对象
                            msg.what = 0x12; // 消息标识
                            msg.obj=line; //消息内容
                            handler.sendMessage(msg);

                            ps.close();
                            br.close();
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
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                //
                alert = builder.setTitle("监测点信息：")
                        .setMessage("名称："+information.get(7).getDevEUI()+"\nx 值："+information.get(7).getX()+"\ny 值:"+information.get(7).getY()+"\n温度:"+information.get(7).getTemperature()+"\n电压:"+information.get(7).getVoltage()+"\n时间:"+information.get(7).getTem_time())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();             //创建AlertDialog对象

                alert.show();                    //显示对话框

            }
        });

        rb_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t=new Thread() {
                    @Override
                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByName("ff00030005000001");
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        try {

                            Socket socket = new Socket(IP.ip, 8888);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println("test_1");
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            line = br.readLine();
                            line_2=br.readLine();

                            list = JSONArray.parseArray(line, t_desc.class);
                            information = JSONArray.parseArray(line_2, information.class);

                            Message msg = Message.obtain(); // 实例化消息对象
                            msg.what = 0x12; // 消息标识
                            msg.obj=line; //消息内容
                            handler.sendMessage(msg);

                            ps.close();
                            br.close();
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
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                //
                alert = builder.setTitle("监测点信息：")
                        .setMessage("名称："+information.get(8).getDevEUI()+"\nx 值："+information.get(8).getX()+"\ny 值:"+information.get(8).getY()+"\n温度:"+information.get(8).getTemperature()+"\n电压:"+information.get(8).getVoltage()+"\n时间:"+information.get(8).getTem_time())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();             //创建AlertDialog对象

                alert.show();                    //显示对话框

            }
        });

        rb_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t=new Thread() {
                    @Override
                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByName("ff00030005000001");
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        try {

                            Socket socket = new Socket(IP.ip, 8888);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println("test_1");
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            line = br.readLine();
                            line_2=br.readLine();

                            list = JSONArray.parseArray(line, t_desc.class);
                            information = JSONArray.parseArray(line_2, information.class);

                            Message msg = Message.obtain(); // 实例化消息对象
                            msg.what = 0x12; // 消息标识
                            msg.obj=line; //消息内容
                            handler.sendMessage(msg);

                            ps.close();
                            br.close();
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
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                //
                alert = builder.setTitle("监测点信息：")
                        .setMessage("名称："+information.get(9).getDevEUI()+"\nx 值："+information.get(9).getX()+"\ny 值:"+information.get(9).getY()+"\n温度:"+information.get(9).getTemperature()+"\n电压:"+information.get(9).getVoltage()+"\n时间:"+information.get(9).getTem_time())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();             //创建AlertDialog对象

                alert.show();                    //显示对话框

            }
        });

        rb_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t=new Thread() {
                    @Override
                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByName("ff00030005000001");
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        try {

                            Socket socket = new Socket(IP.ip, 8888);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println("test_1");
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            line = br.readLine();
                            line_2=br.readLine();

                            list = JSONArray.parseArray(line, t_desc.class);
                            information = JSONArray.parseArray(line_2, information.class);

                            Message msg = Message.obtain(); // 实例化消息对象
                            msg.what = 0x12; // 消息标识
                            msg.obj=line; //消息内容
                            handler.sendMessage(msg);

                            ps.close();
                            br.close();
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
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                //
                alert = builder.setTitle("监测点信息：")
                        .setMessage("名称："+information.get(10).getDevEUI()+"\nx 值："+information.get(10).getX()+"\ny 值:"+information.get(10).getY()+"\n温度:"+information.get(10).getTemperature()+"\n电压:"+information.get(10).getVoltage()+"\n时间:"+information.get(10).getTem_time())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();             //创建AlertDialog对象

                alert.show();                    //显示对话框

            }
        });



        return view;
    }
}


