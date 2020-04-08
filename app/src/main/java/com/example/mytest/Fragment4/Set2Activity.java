package com.example.mytest.Fragment4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

//import com.tencent.android.tpush.XGIOperateCallback;
//import com.tencent.android.tpush.XGPushConfig;
//import com.tencent.android.tpush.XGPushManager;


import com.example.mytest.R;

public class Set2Activity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{


    private Switch swh_1;
    private Switch swh_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set2);

        swh_1 = (Switch) findViewById(R.id.swh_1);
        swh_2 = (Switch) findViewById(R.id.swh_2);
        swh_1.setOnCheckedChangeListener(this);
        swh_2.setOnCheckedChangeListener(this);
    }


//    private void initXGPush(){
//        XGPushConfig.enableDebug(this,true);
//        XGPushManager.registerPush(this, new XGIOperateCallback(){
//            @Override
//            public void onSuccess(Object data, int flag){
//                //token在设备卸载重装的时候有可能会变
//                Log.e("TPush","注册成功，设备token为："+ data);
//                setToken(data.toString());
//            }
//            @Override
//            public void onFail(Object data, int errCode,String msg) {
//                Log.e("TPush","注册失败，错误码："+ errCode +",错误信息："+ msg);
//            }
//        });
//
////设置账号
//        XGPushManager.registerPush(getApplicationContext(),"XINGE");
//// 设置标签
//        XGPushManager.setTag(this,"XINGE");
//    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.swh_1:
                if(compoundButton.isChecked()) Toast.makeText(this,"短信推送开启",Toast.LENGTH_SHORT).show();
                else Toast.makeText(this,"短信推送关闭",Toast.LENGTH_SHORT).show();
                break;
            case R.id.swh_2:
                if(compoundButton.isChecked()) Toast.makeText(this,"邮件推送开启",Toast.LENGTH_SHORT).show();
                else Toast.makeText(this,"邮件推送关闭", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}

