package com.example.mytest.Fragment3;

import android.content.Context;
import android.widget.TextView;

import com.example.mytest.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

public class XYMarkerView extends MarkerView {

    private TextView tvContent;
    private IAxisValueFormatter xAxisValueFormatter;
    int flag;

    private DecimalFormat format;

    public XYMarkerView(Context context, IAxisValueFormatter xAxisValueFormatter,int flag) {
        super(context, R.layout.custom_marker_view);

        this.xAxisValueFormatter = xAxisValueFormatter;
        this.flag=flag;
        tvContent = (TextView) findViewById(R.id.tvContent);
       // format = new DecimalFormat("###.0");
        // tvContent.setText("x: " + (int)(e.getX()/60)+"时"+(int)e.getX()%60+"分" + "\ny: " + format.format(e.getY()));
    }

    //回调函数每次MarkerView重绘,可以用来更新内容(用户界面)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if(flag==1)
        tvContent.setText("x: " + (int)(e.getX()/60)+"时"+(int)e.getX()%60+"分" + "\ny: " + e.getY());
        else
            tvContent.setText("x: " +(int)(e.getX()/24)+"日"+(int)e.getX()%24+"时" + "\ny: " + e.getY());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
