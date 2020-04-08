package com.example.mytest.Fragment2;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.mytest.R;
import com.example.mytest.information;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    int position;
    String text;
    String title;
    List<information> list ;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mfg2_message);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        TextView newsTitleText = (TextView) findViewById(R.id.news_title);
        newsTitleText.setText("监测点详细信息");
        list=MyFragment2.newsList;

        tv1=(TextView)findViewById(R.id.mfg2_textview_2);
        tv2=(TextView)findViewById(R.id.mfg2_textview_4);
        tv3=(TextView)findViewById(R.id.mfg2_textview_6);
        tv4=(TextView)findViewById(R.id.mfg2_textview_8);
        tv5=(TextView)findViewById(R.id.mfg2_textview_10);
        tv6=(TextView)findViewById(R.id.mfg2_textview_12);
        tv7=(TextView)findViewById(R.id.mfg2_textview_14);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        tv1.setText(list.get(position).getDevEUI());
        tv2.setText(list.get(position).getState());
        //tv3.setText(sf.format(list.get(position).getTem_time());
        tv3.setText(String.valueOf(list.get(position).getTem_time()));
        tv4.setText(String.valueOf(list.get(position).getX()));
        tv5.setText(String.valueOf(list.get(position).getY()));
        tv6.setText(String.valueOf(list.get(position).getTemperature()));
        tv7.setText(String.valueOf(list.get(position).getVoltage()));


    }
}
