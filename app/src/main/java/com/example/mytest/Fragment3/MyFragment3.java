package com.example.mytest.Fragment3;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.example.mytest.Fragment2.Content;
import com.example.mytest.IP;
import com.example.mytest.R;
import com.example.mytest.Sql;

import com.example.mytest.information;
import com.example.mytest.t_desc;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyFragment3 extends Fragment {

    ChartView chartView;
    RadioGroup rg;
    RadioButton rb1, rb2, rb3, rb4,rb5;
    private List<Float> xValue = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Float> yValue = new ArrayList<>();
    //折线对应的数据
    private Map<Float, Float> value = new HashMap<>();

    private String devEUI[] = new String[100];
    private String data[] = new String[100];
    private double x[] = new double[100];
    private double y[] = new double[100];
    private double temperature[] = new double[100];
    private String tem_time[] = new String[100];
    private double voltage[] = new double[100];
    double year[] = new double[100];
    double month[] = new double[13];
    double day[] = new double[32];
    double hour[] = new double[25];
    double temp[] = new double[1441];
    float temp_2;
    LineChart lineChart;
    Timestamp time;
    ArrayList<Entry> entryList = new ArrayList<>();
    ArrayList<Entry> entryList2 = new ArrayList<>();
    int i;
    double max1, min1, max2, min2;
    double line;
    String str;
    String Name;
    String kind = "月";
    Button button;
    Button button_2, button_3;
    private Spinner spinner_1;
    private Spinner spinner_2;
    private TextView textView_1;
    private TextView textView_2;
    private Spinner spinner_3;
    private Spinner spinner_4;
    private TextView textView_7;
    private TextView textView_8;
    private TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6;
    List<String> list=null;
    List<String> name=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter_2;

    String[] quarters_2;
    String[] quarters_1;

    int flag = 0;
    RecyclerView recyclerView;

    static List<information> information = new ArrayList<>();
    List<information> newsList = new ArrayList<>();
    List<t_desc> t_desc =null;

    NewsAdapter adapter_3;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 11) {
                //Toast.makeText(getActivity(),"qq"+String.valueOf(name.get(1)), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "the length:" + String.valueOf(information.size()), Toast.LENGTH_SHORT).show();
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.mfg3_content, container, false);
        TextView paomadeng = (TextView) view.findViewById(R.id.title_12);
        setTextMarquee(paomadeng);

        spinner_1 = (Spinner) view.findViewById(R.id.spinner_1);
        spinner_2 = (Spinner) view.findViewById(R.id.spinner_2);
        textView_1 = (TextView) view.findViewById(R.id.title_10);
        textView_2 = (TextView) view.findViewById(R.id.title_14);

        spinner_3 = (Spinner) view.findViewById(R.id.spinner_3);
        //spinner_4=(Spinner)view.findViewById(R.id.spinner_4);
        textView_7 = (TextView) view.findViewById(R.id.title_7);
        textView_8 = (TextView) view.findViewById(R.id.title_8);

        tv_1 = (TextView) view.findViewById(R.id.title_21);
        tv_2 = (TextView) view.findViewById(R.id.title_22);
        tv_3 = (TextView) view.findViewById(R.id.title_23);
        tv_4 = (TextView) view.findViewById(R.id.title_24);
        tv_5 = (TextView) view.findViewById(R.id.title_25);
        tv_6 = (TextView) view.findViewById(R.id.title_26);

        button = (Button) view.findViewById(R.id.button);
        button_2 = (Button) view.findViewById(R.id.button_2);
        button_3 = (Button) view.findViewById(R.id.button_3);

        lineChart = view.findViewById(R.id.lineChart);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        rg = (RadioGroup) view.findViewById(R.id.radio);
        rb1 = view.findViewById(R.id.radiobutton1);
        rb2 = view.findViewById(R.id.radiobutton2);
        rb3 = view.findViewById(R.id.radiobutton3);
        rb4 = view.findViewById(R.id.radiobutton4);
        rb5 = view.findViewById(R.id.radiobutton5);


        if (information.isEmpty()) {
            init();
        }

        try {
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        ///新建arrayAdapter,android.R.layout.simple_spinner_item是调用android studio中默认的样式
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name);
        //adapter设置一个下拉列表
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner加载适配器
        spinner_2.setAdapter(adapter);
        //实现监听事件
        spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Name = adapter.getItem(position);
                //String cityName=list.get(position);
                textView_2.setText(Name);

//                Thread t = new Thread() {
//                    @Override
//                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByDate(textView_8.getText().toString(), Name);
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                t.start();

                Thread t=new Thread() {
                    @Override
                    public void run() {
                        try {

                            Socket socket = new Socket(IP.ip, 8886);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println(textView_8.getText().toString());
                            ps.flush();
                            ps.println(Name);
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            str = br.readLine();

                            information = JSONArray.parseArray(str, information.class);

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
                if ("月".equals(kind)) {
                    selectRadioButton_2();
                    adapter_3 = new NewsAdapter(information);
                    recyclerView.setAdapter(adapter_3);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                } else {
                    selectRadioButton();
                    adapter_3 = new NewsAdapter(information);
                    recyclerView.setAdapter(adapter_3);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        list = new ArrayList<String>();
        list.add("月");
        list.add("日");
        ///新建arrayAdapter,android.R.layout.simple_spinner_item是调用android studio中默认的样式
        adapter_2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        //adapter设置一个下拉列表
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner加载适配器
        spinner_3.setAdapter(adapter_2);
        //实现监听事件
        spinner_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kind = adapter_2.getItem(position);
                textView_7.setText(kind); //设置日期
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("月".equals(kind)) {
                    Calendar c = Calendar.getInstance(Locale.CHINA);
                    DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        //选择日期点击OK后执行
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Calendar c = Calendar.getInstance();
                            c.set(year, monthOfYear, dayOfMonth);
                            String strFormat = "yyyy-MM";  //格式设定
                            SimpleDateFormat sdf = new SimpleDateFormat(strFormat, Locale.CHINA);
                            textView_8.setText(sdf.format(c.getTime())); //设置日期

//                            Thread t = new Thread() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        Sql sql = new Sql();
//                                        information = sql.SelectByDate(textView_8.getText().toString(), Name);
//                                        Message msg = Message.obtain(); // 实例化消息对象
//                                        msg.what = 11; // 消息标识
//                                        //    msg.obj = line; // 消息内容存放
//                                        handler.sendMessage(msg);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            };
//                            t.start();

                            Thread t=new Thread() {
                                @Override
                                public void run() {
                                    try {

                                        Socket socket = new Socket(IP.ip, 8886);

                                        PrintStream ps = new PrintStream(socket.getOutputStream());
                                        ps.println(textView_8.getText().toString());
                                        ps.flush();
                                        ps.println(Name);
                                        ps.flush();

                                        BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                                        str = br.readLine();

                                        information = JSONArray.parseArray(str, information.class);

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
                                selectRadioButton_2();
                                adapter_3 = new NewsAdapter(information);
                                recyclerView.setAdapter(adapter_3);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        }

                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
// 隐藏
//                    if (datePickerDialog != null) {
//                        int SDKVersion = android.os.Build.VERSION.SDK_INT;
//                        //int SDKVersion = PhoneInfo.getSDKVersionNumber();
//                        if (SDKVersion < 11) {
//                            //隐藏日
//                            ((ViewGroup) datePickerDialog.getDatePicker().getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
//
//                        } else if (SDKVersion > 14) {
//                            //隐藏日
//                            ((ViewGroup) ((ViewGroup) datePickerDialog.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
//
//                        }
//                    }



                } else {
                    Calendar c = Calendar.getInstance(Locale.CHINA);
                    new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        //选择日期点击OK后执行
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Calendar c = Calendar.getInstance();
                            c.set(year, monthOfYear, dayOfMonth);
                            String strFormat = "yyyy-MM-dd";  //格式设定
                            SimpleDateFormat sdf = new SimpleDateFormat(strFormat, Locale.CHINA);
                            textView_8.setText(sdf.format(c.getTime())); //设置日期


//                            Thread t = new Thread() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        Sql sql = new Sql();
//                                        information = sql.SelectByDate(textView_8.getText().toString(), Name);
//                                        Message msg = Message.obtain(); // 实例化消息对象
//                                        msg.what = 11; // 消息标识
//                                        //    msg.obj = line; // 消息内容存放
//                                        handler.sendMessage(msg);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            };
//                            t.start();
                            Thread t=new Thread() {
                                @Override
                                public void run() {
                                    try {

                                        Socket socket = new Socket(IP.ip, 8886);

                                        PrintStream ps = new PrintStream(socket.getOutputStream());
                                        ps.println(textView_8.getText().toString());
                                        ps.flush();
                                        ps.println(Name);
                                        ps.flush();

                                        BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                                        str = br.readLine();

                                        information = JSONArray.parseArray(str, information.class);

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

                                selectRadioButton();
                                adapter_3 = new NewsAdapter(information);
                                recyclerView.setAdapter(adapter_3);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                }

            }

        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Thread t = new Thread() {
//                    @Override
//                    public void run() {
//                        try {
//                            Sql sql = new Sql();
//                            information = sql.SelectByDate(textView_8.getText().toString(), Name);
//                            Message msg = Message.obtain(); // 实例化消息对象
//                            msg.what = 11; // 消息标识
//                            //    msg.obj = line; // 消息内容存放
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                t.start();

                Thread t=new Thread() {
                    @Override
                    public void run() {
                        try {

                            Socket socket = new Socket(IP.ip, 8886);

                            PrintStream ps = new PrintStream(socket.getOutputStream());
                            ps.println(textView_8.getText().toString());
                            ps.flush();
                            ps.println(Name);
                            ps.flush();

                            BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            str = br.readLine();

                            information = JSONArray.parseArray(str, information.class);

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
                if ("月".equals(kind)) {
                    selectRadioButton_2();
                    adapter_3 = new NewsAdapter(information);
                    recyclerView.setAdapter(adapter_3);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                } else {
                    selectRadioButton();
                    adapter_3 = new NewsAdapter(information);
                    recyclerView.setAdapter(adapter_3);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                }

            }
        });

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    flag = 1;
                    lineChart.setVisibility(View.GONE);
                    rg.setVisibility(View.GONE);
                    rb1.setVisibility(View.GONE);
                    rb2.setVisibility(View.GONE);
                    rb3.setVisibility(View.GONE);
                    rb4.setVisibility(View.GONE);
                     rb5.setVisibility(View.GONE);
                    tv_1.setVisibility(View.VISIBLE);
                    tv_2.setVisibility(View.VISIBLE);
                    tv_3.setVisibility(View.VISIBLE);
                    tv_4.setVisibility(View.VISIBLE);
                    tv_5.setVisibility(View.VISIBLE);
                    tv_6.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    flag = 0;
                    lineChart.setVisibility(View.VISIBLE);
                    rg.setVisibility(View.VISIBLE);
                    rb1.setVisibility(View.VISIBLE);
                    rb2.setVisibility(View.VISIBLE);
                    rb3.setVisibility(View.VISIBLE);
                    rb4.setVisibility(View.VISIBLE);
                    rb5.setVisibility(View.VISIBLE);
                    tv_1.setVisibility(View.GONE);
                    tv_2.setVisibility(View.GONE);
                    tv_3.setVisibility(View.GONE);
                    tv_4.setVisibility(View.GONE);
                    tv_5.setVisibility(View.GONE);
                    tv_6.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }

            }
        });


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if ("月".equals(kind)) {
                    selectRadioButton_2();
                } else {
                    selectRadioButton();
                }
            }
        });
        return view;
    }

    public static void setTextMarquee(TextView textView) {
        if (textView != null) {
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setSingleLine(true);
            textView.setSelected(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }

    private void selectRadioButton() {

        if (rb1.isChecked()) {
            entryList.clear();
            for (int i = 0; i < information.size(); i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(information.get(i).getTime());
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                int minute = cal.get(Calendar.MINUTE);//小时
                if(i==0){
                    max1 = information.get(i).getX();
                    min1 = information.get(i).getX();
                }
                else {
                    max1 = max1 > information.get(i).getX() ? max1 : information.get(i).getX();
                    min1 = min1 < information.get(i).getX() ? min1 : information.get(i).getX();
                }
                //  temp[hour*60+minute]=information.get(i).getX();
                entryList.add(new Entry(hour * 60 + minute, (float) information.get(i).getX()));
            }
            double len = (max1 - min1) ;
            float max = (float) (max1 + len);

            float min = (float) (min1 - len);
            if(max==min){
                max=max+1;
                min=min-1;
            }
            lineChart.clear();

            setChart_1(1000f, -1000f, 0);

            lineChart.clear();
            lineChart.clearAllViewportJobs();
            lineChart.removeAllViews();
            setChart_1(max, min, 20f);
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        } else if (rb2.isChecked()) {
            entryList.clear();
            for (int i = 0; i < information.size(); i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(information.get(i).getTime());
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                int minute = cal.get(Calendar.MINUTE);//小时
                if(i==0){
                    max2 = information.get(i).getY();
                    min2 = information.get(i).getY();
                }
                else {
                    max2 = max2 > information.get(i).getY() ? max2 : information.get(i).getY();
                    min2 = min2 < information.get(i).getY() ? min2 : information.get(i).getY();
                }

                //  temp[hour*60+minute]=information.get(i).getX();
                entryList.add(new Entry(hour * 60 + minute, (float) information.get(i).getY()));
            }
            double len = (max2 - min2) ;
            float max = (float) (max2 + len);

            float min = (float) (min2 - len);
            if(max==min){
                max=max+1;
                min=min-1;
            }
            lineChart.clear();

            setChart_1(1000f, -1000f, 0);

            lineChart.clear();
            lineChart.clearAllViewportJobs();
            lineChart.removeAllViews();
            setChart_1(max, min, 20f);
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        } else if (rb3.isChecked()) {
            entryList.clear();
            for (int i = 0; i < information.size(); i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(information.get(i).getTime());
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                int minute = cal.get(Calendar.MINUTE);//小时
                //  temp[hour*60+minute]=information.get(i).getX();
                entryList.add(new Entry(hour * 60 + minute, (float) information.get(i).getTemperature()));
            }
            lineChart.clear();

            setChart_1(1000f, -1000f, 0);
            lineChart.clear();
            lineChart.clearAllViewportJobs();
            lineChart.removeAllViews();
            setChart_1(40f, -10f, 0);
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        } else if (rb4.isChecked()) {
            entryList.clear();
            for (int i = 0; i < information.size(); i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(information.get(i).getTime());
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                int minute = cal.get(Calendar.MINUTE);//小时
                //  temp[hour*60+minute]=information.get(i).getX();
                entryList.add(new Entry(hour * 60 + minute, (float) information.get(i).getVoltage()));
            }
            lineChart.clear();

            setChart_1(1000f, -1000f, 0);
            lineChart.clear();
            lineChart.clearAllViewportJobs();
            lineChart.removeAllViews();
            setChart_1(10f, 0f, 0);
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        } else if (rb5.isChecked()) {
            entryList.clear();
            entryList2.clear();
            for (int i = 0; i < information.size(); i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(information.get(i).getTime());
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                int minute = cal.get(Calendar.MINUTE);//小时
                if(i==0){
                    max1 = information.get(i).getX();
                    min1 = information.get(i).getX();
                    max2 = information.get(i).getY();
                    min2 = information.get(i).getY();
                }
                else {
                    max1 = max1 > information.get(i).getX() ? max1 : information.get(i).getX();
                    min1 = min1 < information.get(i).getX() ? min1 : information.get(i).getX();
                    max2 = max2 > information.get(i).getY() ? max2 : information.get(i).getY();
                    min2 = min2 < information.get(i).getY() ? min2 : information.get(i).getY();
                }

                //  temp[hour*60+minute]=information.get(i).getX();
                entryList.add(new Entry(hour * 60 + minute, (float) information.get(i).getX()));
                entryList2.add(new Entry(hour * 60 + minute, (float) information.get(i).getY()));
            }

            float max=(float)(max1>max2?max1:max2);
            float min=(float)(min1<min2?min1:min2);
            float len = (max - min)/2;

            if(max==min){
                max=max+1;
                min=min-1;
            }

            lineChart.clear();
            setChart_3(1000f, -1000f);

            lineChart.clear();
            setChart_3(max+len,min-len);
            lineChart.invalidate();
        }
    }

    private void selectRadioButton_2() {
        if (rb1.isChecked()) {
            entryList.clear();
           /* if(information.isEmpty()){
               return;
            }*/
            for (int i = 0; i < information.size(); i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(information.get(i).getTime());
                int day = cal.get(Calendar.DATE);//获取日
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                if(i==0){
                    max1 = information.get(i).getX();
                    min1 = information.get(i).getX();
                }
                else {
                    max1 = max1 > information.get(i).getX() ? max1 : information.get(i).getX();
                    min1 = min1 < information.get(i).getX() ? min1 : information.get(i).getX();
                }
                entryList.add(new Entry(day * 24 + hour, (float) information.get(i).getX()));
            }
            lineChart.clear();

            setChart_2(1000f, -1000f, 0);
            lineChart.clear();
            double len = (max1 - min1) ;
            float max = (float) (max1 + len);

            float min = (float) (min1 - len);
            if(max==min){
                max=max+1;
                min=min-1;
            }

            setChart_2(max,min, 20f);
            //lineChart.notifyDataSetChanged();
            // lineChart.invalidate();
        } else if (rb2.isChecked()) {
            entryList.clear();

            for (int i = 0; i < information.size(); i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(information.get(i).getTime());
                int day = cal.get(Calendar.DATE);//获取日
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                if(i==0){
                    max2 = information.get(i).getY();
                    min2 = information.get(i).getY();
                }
                else {
                    max2 = max2 > information.get(i).getY() ? max2 : information.get(i).getY();
                    min2 = min2 < information.get(i).getY() ? min2 : information.get(i).getY();
                }
                entryList.add(new Entry(day * 24 + hour, (float) information.get(i).getY()));
            }
            lineChart.clear();

            setChart_2(1000f, -1000f, 0);
            lineChart.clear();
            double len = (max2 - min2) ;
            float max = (float) (max2 + len);

            float min = (float) (min2 - len);
            if(max==min){
                max=max+1;
                min=min-1;
            }

            setChart_2(max,min, 20f);
            // lineChart.notifyDataSetChanged();
            //  lineChart.invalidate();

        } else if (rb3.isChecked()) {
            entryList.clear();
            for (int i = 0; i < information.size(); i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(information.get(i).getTime());
                int day = cal.get(Calendar.DATE);//获取日
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                entryList.add(new Entry(day * 24 + hour, (float) information.get(i).getTemperature()));
            }
            lineChart.clear();

            setChart_2(1000f, -1000f, 0);
            lineChart.clear();

            setChart_2(40f, -10f, 0);
            //lineChart.notifyDataSetChanged();
            //  lineChart.invalidate();

        } else if (rb4.isChecked()) {
            entryList.clear();
            for (int i = 0; i < information.size(); i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(information.get(i).getTime());
                int day = cal.get(Calendar.DATE);//获取日
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                entryList.add(new Entry(day * 24 + hour, (float) information.get(i).getVoltage()));
            }
            lineChart.clear();

            setChart_2(1000f, -1000f, 0);
            lineChart.clear();

            setChart_2(10f, 0f, 0);
            //lineChart.notifyDataSetChanged();
            //lineChart.invalidate();
        } else if (rb5.isChecked()) {
            entryList.clear();
            entryList2.clear();
            for (int i = 0; i < information.size(); i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(information.get(i).getTime());
                int day = cal.get(Calendar.DATE);//获取日
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                if(i==0){
                    max1 =information.get(i).getX();
                    min1 =  information.get(i).getX();
                    max2 = information.get(i).getY();
                    min2 = information.get(i).getY();
                }
                else {
                    max1 = max1 > information.get(i).getX() ? max1 : information.get(i).getX();
                    min1 = min1 < information.get(i).getX() ? min1 : information.get(i).getX();
                    max2 = max2 > information.get(i).getY() ? max2 : information.get(i).getY();
                    min2 = min2 < information.get(i).getY() ? min2 : information.get(i).getY();
                }
                entryList.add(new Entry(day * 24 + hour, (float) information.get(i).getX()));
                entryList2.add(new Entry(day * 24 + hour, (float) information.get(i).getY()));
            }
            float max=(float)(max1>max2?max1:max2);
            float min=(float)(min1<min2?min1:min2);
            float len = (max - min)/2;
            if(max==min){
                max=max+1;
                min=min-1;
            }

            lineChart.clear();
            setChart_4(1000f, -1000f);

            lineChart.clear();
            setChart_4(max+len,min-len);
            lineChart.invalidate();
        }
    }

    void init() {
        Date aa = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
        textView_8.setText(sf.format(aa));
//        Thread t = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Sql sql = new Sql();
//                    name = sql.selectName();
//                    //  information=sql.Sql();
//                    Message msg = Message.obtain(); // 实例化消息对象
//                    msg.what = 11; // 消息标识
//                    //    msg.obj = line; // 消息内容存放
//                    handler.sendMessage(msg);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        t.start();

        Thread t=new Thread() {
            @Override
            public void run() {
                try {

                    Socket socket = new Socket(IP.ip, 8888);

                    PrintStream ps = new PrintStream(socket.getOutputStream());
                    ps.println("test_3");
                    ps.flush();

                    BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    str = br.readLine();
                    br.readLine();

                    t_desc = JSONArray.parseArray(str, t_desc.class);
                   for(int n=0;n<t_desc.size();n++){
                       name.add(t_desc.get(n).getDevEUI().toString());
                   }

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
    }

    void setChart_1(float length1, float length2, float flag) {
        quarters_1 = new String[1441];
        for (int i = 0; i < 1441; i++) {
            if (i % 60 == 0)
                quarters_1[i] = (i / 60) + "时";
            else
                quarters_1[i] = "";
        }

        lineChart.invalidate();
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int intValue = (int) value;
                if (quarters_1.length > intValue && intValue >= 0)
                    return quarters_1[intValue];
                else
                    return "";
            }
        };
        //设置说明
        lineChart.setDescription(null);
//设置图例关
        lineChart.getLegend().setEnabled(false);
//设置显示范围
        //lineChart.setVisibleXRangeMaximum(4);
        lineChart.setVisibleYRangeMinimum(10f, YAxis.AxisDependency.LEFT);
//设置透明度
        lineChart.setAlpha(1.0f);
//设置背景色
        lineChart.setBackgroundColor(Color.WHITE);
//设置边框
        lineChart.setBorderColor(Color.rgb(0, 0, 0));
        lineChart.setGridBackgroundColor(R.color.colorPrimary);
//设置触摸(关闭影响下面3个属性)
        lineChart.setTouchEnabled(true);
//设置是否可以拖拽
        lineChart.setDragEnabled(true);
//设置是否可以缩放
        lineChart.setScaleEnabled(true);
//设置是否能扩大扩小
        lineChart.setPinchZoom(true);
        lineChart.setNoDataText("你还没有记录数据");
        lineChart.setNoDataTextColor(Color.WHITE);

//获取X轴
        XAxis xl = lineChart.getXAxis();
//启用X轴
        xl.setEnabled(true);
//设置X轴避免图表或屏幕的边缘的第一个和最后一个轴中的标签条目被裁剪
        xl.setAvoidFirstLastClipping(true);
//设置X轴底部显示
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//设置竖网格
        xl.setDrawGridLines(false);
//设置X轴文字大小
        xl.setTextSize(10f);
        xl.setAxisMinimum(0f);//设置x轴的最小值 //`
        xl.setAxisMaximum(1441f);//设置最大值 //
//设置x轴单位间隔
        // xl.setGranularity(1f);
//设置X轴值
        xl.setValueFormatter(formatter);


//获取Y轴(左)
        YAxis yl = lineChart.getAxisLeft();
//设置Y轴文字在外部显示
        yl.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//Y轴字体
        yl.setTextSize(10f);
//设置Y轴最大值
        yl.setAxisMaximum(length1);
//设置Y轴起始值
        yl.setAxisMinimum(length2);
        //  yl.resetAxisMaximum();
        // yl.resetAxisMinimum();

//获取Y轴(右)
        YAxis yl2 = lineChart.getAxisRight();
//禁用右侧Y轴
        yl2.setEnabled(false);

       /* if (flag != 0) {
            LimitLine ll = new LimitLine(flag, "警戒线");
            ll.setLineColor(Color.RED);
            ll.setLineWidth(1f);
            ll.setTextColor(Color.GRAY);
            ll.setTextSize(12f);
            // .. and more styling options
            yl.addLimitLine(ll);

            LimitLine lll = new LimitLine(-flag, "警戒线");
            lll.setLineColor(Color.RED);
            lll.setLineWidth(1f);
            lll.setTextColor(Color.GRAY);
            lll.setTextSize(12f);
            // .. and more styling options
            yl.addLimitLine(lll);
        }*/

//数据
        if (entryList.isEmpty()) {
            entryList.add(new Entry(0, (float) 0));
        }
        LineDataSet l1 = new LineDataSet(entryList, "红色");
        l1.setAxisDependency(YAxis.AxisDependency.LEFT);
//设置包括的范围区域填充颜色
        l1.setDrawFilled(false);

//设置线的宽度
        l1.setLineWidth(2f);
//设置曲线的颜色
        l1.setColor(Color.rgb(142, 245, 255));
//设置曲率,0.05f-1f  1为折线
        // l1.setCubicIntensity(1f);
        l1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

//设置有圆点
        l1.setDrawCircles(true);
//设置小圆点的大小
        l1.setCircleRadius(3.5f);
//设置圆圈颜色
        l1.setCircleColor(Color.rgb(142, 245, 255));
//填充圆圈内颜色
        l1.setCircleColorHole(Color.rgb(142, 245, 255));

//设置显示数值
        l1.setDrawValues(false);

        List<ILineDataSet> lineDataSetArrayList = new ArrayList<>();
        lineDataSetArrayList.add(l1);

        LineData lineData = new LineData(lineDataSetArrayList);
        lineChart.setData(lineData);

//设置XY轴进入动画
        // lineChart.animateXY(800, 800);
//设置最小的缩放
        lineChart.setScaleMinima(1f, 1f);
        //设置悬浮
        XYMarkerView mv = new XYMarkerView(getActivity(), formatter, 1);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);

    }

    void setChart_2(float length1, float length2, float flag) {
        quarters_2 = new String[744];
        for (int i = 0; i < 744; i++) {
            if (i % 24 == 0)
                quarters_2[i] = (i / 24) + "日";
            else
                quarters_2[i] = "";
        }

        //lineChart.invalidate();
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int intValue = (int) value;
                if (quarters_2.length > intValue && intValue >= 0) {
                    if (!"0日".equals(quarters_2[intValue]))
                        return quarters_2[intValue];
                    else
                        return "";
                } else
                    return "";
            }
        };
        //设置说明
        lineChart.setDescription(null);
//设置图例关
        lineChart.getLegend().setEnabled(false);
//设置显示范围
        //lineChart.setVisibleXRangeMaximum(4);
        //lineChart.setVisibleYRangeMinimum(10f, YAxis.AxisDependency.LEFT);
//设置透明度
        lineChart.setAlpha(1.0f);
//设置背景色
        lineChart.setBackgroundColor(Color.WHITE);
//设置边框
        lineChart.setBorderColor(Color.rgb(0, 0, 0));
        lineChart.setGridBackgroundColor(R.color.colorPrimary);
//设置触摸(关闭影响下面3个属性)
        lineChart.setTouchEnabled(true);
//设置是否可以拖拽
        lineChart.setDragEnabled(true);
//设置是否可以缩放
        lineChart.setScaleEnabled(true);
//设置是否能扩大扩小
        lineChart.setPinchZoom(true);
        lineChart.setNoDataText("你还没有记录数据");
        lineChart.setNoDataTextColor(Color.WHITE);

//获取X轴
        XAxis xl = lineChart.getXAxis();

        // xl.setSpaceBetweenLabels(4);
//设置x轴单位间隔
//启用X轴
        xl.setEnabled(true);
//设置X轴避免图表或屏幕的边缘的第一个和最后一个轴中的标签条目被裁剪
        xl.setAvoidFirstLastClipping(true);
//设置X轴底部显示
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//设置竖网格
        xl.setDrawGridLines(false);
//设置X轴文字大小
        xl.setTextSize(12f);
        xl.setDrawLabels(true);//绘制标签

        xl.setAxisMinimum(0f);//设置x轴的最小值 //`
        xl.setAxisMaximum(744f);//设置最大值 //


        xl.setGranularityEnabled(true);
        xl.setGranularity(1f);
        xl.setLabelCount(quarters_2.length);
//设置X轴值
        xl.setValueFormatter(formatter);


//获取Y轴(左)
        YAxis yl = lineChart.getAxisLeft();
//设置Y轴文字在外部显示
        yl.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//Y轴字体
        yl.setTextSize(10f);
//设置Y轴最大值
        yl.setAxisMaximum(length1);
//设置Y轴起始值
        yl.setAxisMinimum(length2);
        // yl.resetAxisMaximum();
        //yl.resetAxisMinimum();

//获取Y轴(右)
        YAxis yl2 = lineChart.getAxisRight();
//禁用右侧Y轴
        yl2.setEnabled(false);

        /*if (flag != 0) {
            LimitLine ll = new LimitLine(flag, "警戒线");
            ll.setLineColor(Color.RED);
            ll.setLineWidth(1f);
            ll.setTextColor(Color.GRAY);
            ll.setTextSize(12f);
            // .. and more styling options
            yl.addLimitLine(ll);

            LimitLine lll = new LimitLine(-flag, "警戒线");
            lll.setLineColor(Color.RED);
            lll.setLineWidth(1f);
            lll.setTextColor(Color.GRAY);
            lll.setTextSize(12f);
            // .. and more styling options
            yl.addLimitLine(lll);
        }*/

//数据
        if (entryList.isEmpty()) {
            entryList.add(new Entry(0, (float) 0));
        }
        LineDataSet l1 = new LineDataSet(entryList, "红色");
        l1.setAxisDependency(YAxis.AxisDependency.LEFT);
//设置包括的范围区域填充颜色
        l1.setDrawFilled(false);

//设置线的宽度
        l1.setLineWidth(2f);
//设置曲线的颜色
        l1.setColor(Color.rgb(142, 245, 255));
//设置曲率,0.05f-1f  1为折线
        // l1.setCubicIntensity(1f);
        l1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

//设置有圆点
        l1.setDrawCircles(true);
//设置小圆点的大小
        l1.setCircleRadius(3.5f);
//设置圆圈颜色
        l1.setCircleColor(Color.rgb(142, 245, 255));
//填充圆圈内颜色
        l1.setCircleColorHole(Color.rgb(142, 245, 255));

//设置显示数值
        l1.setDrawValues(false);

        List<ILineDataSet> lineDataSetArrayList = new ArrayList<>();
        lineDataSetArrayList.add(l1);

        LineData lineData = new LineData(lineDataSetArrayList);
        lineChart.setData(lineData);

//设置XY轴进入动画
        // lineChart.animateXY(800, 800);
//设置最小的缩放
        lineChart.setScaleMinima(1f, 1f);
        //设置悬浮
        XYMarkerView mv = new XYMarkerView(getActivity(), formatter, 2);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);

    }

    void setChart_3(float length1, float length2) {
        quarters_1 = new String[1441];
        for (int i = 0; i < 1441; i++) {
            if (i % 60 == 0)
                quarters_1[i] = (i / 60) + "时";
            else
                quarters_1[i] = "";
        }

        lineChart.invalidate();
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int intValue = (int) value;
                if (quarters_1.length > intValue && intValue >= 0)
                    return quarters_1[intValue];
                else
                    return "";
            }
        };
        //设置说明
        lineChart.setDescription(null);
//设置图例关
        lineChart.getLegend().setEnabled(false);
//设置显示范围
        //lineChart.setVisibleXRangeMaximum(4);
        lineChart.setVisibleYRangeMinimum(10f, YAxis.AxisDependency.LEFT);
//设置透明度
        lineChart.setAlpha(1.0f);
//设置背景色
        lineChart.setBackgroundColor(Color.WHITE);
//设置边框
        lineChart.setBorderColor(Color.rgb(0, 0, 0));
        lineChart.setGridBackgroundColor(R.color.colorPrimary);
//设置触摸(关闭影响下面3个属性)
        lineChart.setTouchEnabled(true);
//设置是否可以拖拽
        lineChart.setDragEnabled(true);
//设置是否可以缩放
        lineChart.setScaleEnabled(true);
//设置是否能扩大扩小
        lineChart.setPinchZoom(true);
        lineChart.setNoDataText("你还没有记录数据");
        lineChart.setNoDataTextColor(Color.WHITE);

//获取X轴
        XAxis xl = lineChart.getXAxis();
//启用X轴
        xl.setEnabled(true);
//设置X轴避免图表或屏幕的边缘的第一个和最后一个轴中的标签条目被裁剪
        xl.setAvoidFirstLastClipping(true);
//设置X轴底部显示
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//设置竖网格
        xl.setDrawGridLines(false);
//设置X轴文字大小
        xl.setTextSize(10f);
        xl.setAxisMinimum(0f);//设置x轴的最小值 //`
        xl.setAxisMaximum(1441f);//设置最大值 //
//设置x轴单位间隔
        // xl.setGranularity(1f);
//设置X轴值
        xl.setValueFormatter(formatter);


//获取Y轴(左)
        YAxis yl = lineChart.getAxisLeft();
//设置Y轴文字在外部显示
        yl.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//Y轴字体
        yl.setTextSize(10f);
//设置Y轴最大值
        yl.setAxisMaximum(length1);
//设置Y轴起始值
        yl.setAxisMinimum(length2);
        //  yl.resetAxisMaximum();
        // yl.resetAxisMinimum();

//获取Y轴(右)
        YAxis yl2 = lineChart.getAxisRight();
//禁用右侧Y轴
        yl2.setEnabled(false);

//数据
        if (entryList.isEmpty()) {
            entryList.add(new Entry(0, (float) 0));
        }
        LineDataSet l1 = new LineDataSet(entryList, "蓝色");
        l1.setAxisDependency(YAxis.AxisDependency.LEFT);
//设置包括的范围区域填充颜色
        l1.setDrawFilled(false);

//设置线的宽度
        l1.setLineWidth(2f);
//设置曲线的颜色
        l1.setColor(Color.rgb(142, 245, 255));
//设置曲率,0.05f-1f  1为折线
        // l1.setCubicIntensity(1f);
        l1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

//设置有圆点
        l1.setDrawCircles(true);
//设置小圆点的大小
        l1.setCircleRadius(3.5f);
//设置圆圈颜色
        l1.setCircleColor(Color.rgb(142, 245, 255));
//填充圆圈内颜色
        l1.setCircleColorHole(Color.rgb(142, 245, 255));

//设置显示数值
        l1.setDrawValues(false);

        List<ILineDataSet> lineDataSetArrayList = new ArrayList<>();
        lineDataSetArrayList.add(l1);


        if (entryList2.isEmpty()) {
            entryList2.add(new Entry(0, (float) 0));
        }
        LineDataSet l2 = new LineDataSet(entryList2, "红色");
        l2.setAxisDependency(YAxis.AxisDependency.LEFT);
//设置包括的范围区域填充颜色
        l2.setDrawFilled(false);

//设置线的宽度
        l2.setLineWidth(2f);
//设置曲线的颜色
        l2.setColor(Color.rgb(238 ,99 ,99));
//设置曲率,0.05f-1f  1为折线
        // l1.setCubicIntensity(1f);
        l2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

//设置有圆点
        l2.setDrawCircles(true);
//设置小圆点的大小
        l2.setCircleRadius(3.5f);
//设置圆圈颜色
        l2.setCircleColor(Color.rgb(238 ,99 ,99));
//填充圆圈内颜色
        l2.setCircleColorHole(Color.rgb(238 ,99 ,99));

//设置显示数值
        l2.setDrawValues(false);

        lineDataSetArrayList.add(l2);

        LineData lineData = new LineData(lineDataSetArrayList);
        lineChart.setData(lineData);

//设置XY轴进入动画
        // lineChart.animateXY(800, 800);
//设置最小的缩放
        lineChart.setScaleMinima(1f, 1f);
        //设置悬浮
        XYMarkerView mv = new XYMarkerView(getActivity(), formatter, 1);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);

    }

    void setChart_4(float length1, float length2) {
        quarters_2 = new String[744];
        for (int i = 0; i < 744; i++) {
            if (i % 24 == 0)
                quarters_2[i] = (i / 24) + "日";
            else
                quarters_2[i] = "";
        }

        //lineChart.invalidate();
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int intValue = (int) value;
                if (quarters_2.length > intValue && intValue >= 0) {
                    if (!"0日".equals(quarters_2[intValue]))
                        return quarters_2[intValue];
                    else
                        return "";
                } else
                    return "";
            }
        };
        //设置说明
        lineChart.setDescription(null);
//设置图例关
        lineChart.getLegend().setEnabled(false);
//设置显示范围
        //lineChart.setVisibleXRangeMaximum(4);
        //lineChart.setVisibleYRangeMinimum(10f, YAxis.AxisDependency.LEFT);
//设置透明度
        lineChart.setAlpha(1.0f);
//设置背景色
        lineChart.setBackgroundColor(Color.WHITE);
//设置边框
        lineChart.setBorderColor(Color.rgb(0, 0, 0));
        lineChart.setGridBackgroundColor(R.color.colorPrimary);
//设置触摸(关闭影响下面3个属性)
        lineChart.setTouchEnabled(true);
//设置是否可以拖拽
        lineChart.setDragEnabled(true);
//设置是否可以缩放
        lineChart.setScaleEnabled(true);
//设置是否能扩大扩小
        lineChart.setPinchZoom(true);
        lineChart.setNoDataText("你还没有记录数据");
        lineChart.setNoDataTextColor(Color.WHITE);

//获取X轴
        XAxis xl = lineChart.getXAxis();

        // xl.setSpaceBetweenLabels(4);
//设置x轴单位间隔
//启用X轴
        xl.setEnabled(true);
//设置X轴避免图表或屏幕的边缘的第一个和最后一个轴中的标签条目被裁剪
        xl.setAvoidFirstLastClipping(true);
//设置X轴底部显示
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//设置竖网格
        xl.setDrawGridLines(false);
//设置X轴文字大小
        xl.setTextSize(12f);
        xl.setDrawLabels(true);//绘制标签

        xl.setAxisMinimum(0f);//设置x轴的最小值 //`
        xl.setAxisMaximum(744f);//设置最大值 //


        xl.setGranularityEnabled(true);
        xl.setGranularity(1f);
        xl.setLabelCount(quarters_2.length);
//设置X轴值
        xl.setValueFormatter(formatter);


//获取Y轴(左)
        YAxis yl = lineChart.getAxisLeft();
//设置Y轴文字在外部显示
        yl.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//Y轴字体
        yl.setTextSize(10f);
//设置Y轴最大值
        yl.setAxisMaximum(length1);
//设置Y轴起始值
        yl.setAxisMinimum(length2);
        // yl.resetAxisMaximum();
        //yl.resetAxisMinimum();

//获取Y轴(右)
        YAxis yl2 = lineChart.getAxisRight();
//禁用右侧Y轴
        yl2.setEnabled(false);

        /*if (flag != 0) {
            LimitLine ll = new LimitLine(flag, "警戒线");
            ll.setLineColor(Color.RED);
            ll.setLineWidth(1f);
            ll.setTextColor(Color.GRAY);
            ll.setTextSize(12f);
            // .. and more styling options
            yl.addLimitLine(ll);

            LimitLine lll = new LimitLine(-flag, "警戒线");
            lll.setLineColor(Color.RED);
            lll.setLineWidth(1f);
            lll.setTextColor(Color.GRAY);
            lll.setTextSize(12f);
            // .. and more styling options
            yl.addLimitLine(lll);
        }*/

//数据
        if (entryList.isEmpty()) {
            entryList.add(new Entry(0, (float) 0));
        }
        LineDataSet l1 = new LineDataSet(entryList, "红色");
        l1.setAxisDependency(YAxis.AxisDependency.LEFT);
//设置包括的范围区域填充颜色
        l1.setDrawFilled(false);

//设置线的宽度
        l1.setLineWidth(2f);
//设置曲线的颜色
        l1.setColor(Color.rgb(142, 245, 255));
//设置曲率,0.05f-1f  1为折线
        // l1.setCubicIntensity(1f);
        l1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

//设置有圆点
        l1.setDrawCircles(true);
//设置小圆点的大小
        l1.setCircleRadius(3.5f);
//设置圆圈颜色
        l1.setCircleColor(Color.rgb(142, 245, 255));
//填充圆圈内颜色
        l1.setCircleColorHole(Color.rgb(142, 245, 255));

//设置显示数值
        l1.setDrawValues(false);

        List<ILineDataSet> lineDataSetArrayList = new ArrayList<>();
        lineDataSetArrayList.add(l1);



        if (entryList2.isEmpty()) {
            entryList2.add(new Entry(0, (float) 0));
        }
        LineDataSet l2 = new LineDataSet(entryList2, "红色");
        l2.setAxisDependency(YAxis.AxisDependency.LEFT);
//设置包括的范围区域填充颜色
        l2.setDrawFilled(false);

//设置线的宽度
        l2.setLineWidth(2f);
//设置曲线的颜色
        l2.setColor(Color.rgb(238 ,99 ,99));
//设置曲率,0.05f-1f  1为折线
        // l1.setCubicIntensity(1f);
        l2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

//设置有圆点
        l2.setDrawCircles(true);
//设置小圆点的大小
        l2.setCircleRadius(3.5f);
//设置圆圈颜色
        l2.setCircleColor(Color.rgb(238 ,99 ,99));
//填充圆圈内颜色
        l2.setCircleColorHole(Color.rgb(238 ,99 ,99));

//设置显示数值
        l2.setDrawValues(false);

        lineDataSetArrayList.add(l2);

        LineData lineData = new LineData(lineDataSetArrayList);
        lineChart.setData(lineData);

//设置XY轴进入动画
        // lineChart.animateXY(800, 800);
//设置最小的缩放
        lineChart.setScaleMinima(1f, 1f);
        //设置悬浮
        XYMarkerView mv = new XYMarkerView(getActivity(), formatter, 2);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);

    }


    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }


    public static int getSDKVersionNumber() {
               int sdkVersion;
               try {
                       sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
                  } catch (NumberFormatException e) {
                       sdkVersion = 0;
                    }
               return sdkVersion;
             }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<information> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder {
            View newsView;
            TextView item1;
            TextView item2;
            TextView item3;
            TextView item4;
            TextView item5;
            TextView item6;

            public ViewHolder(View view) {
                super(view);
                newsView = view;
                item1 = (TextView) view.findViewById(R.id.item_1);
                item2 = (TextView) view.findViewById(R.id.item_2);
                item3 = (TextView) view.findViewById(R.id.item_3);
                item4 = (TextView) view.findViewById(R.id.item_4);
                item5 = (TextView) view.findViewById(R.id.item_5);
                item6 = (TextView) view.findViewById(R.id.item_6);
            }
        }

        public NewsAdapter(List<information> newsList) {
            mNewsList = newsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mfg3_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    information news = mNewsList.get(position);
                    Toast.makeText(v.getContext(), "you clicked monitoring points: " + Name, Toast.LENGTH_SHORT).show();

                }
            });
            return holder;
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            information news = mNewsList.get(position);
            holder.item1.setText(Name);
            holder.item2.setText(String.valueOf(news.getX()));
            holder.item3.setText(String.valueOf(news.getY()));
            holder.item4.setText(String.valueOf(news.getTemperature()));
            holder.item5.setText(String.valueOf(news.getVoltage()));
            holder.item6.setText(String.valueOf(news.getTime()));
        }

        public int getItemCount() {
            return mNewsList.size();
        }
    }
}






