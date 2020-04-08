package com.example.mytest.Fragment4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytest.R;

public class Set1Activity extends AppCompatActivity {

    String str1;
    String str2;
    String str3;
    String str4;
    String str5;
    String str6;

    TextView newsContent_1;
    EditText newsContent_2;
    EditText newsContent_3;
    EditText newsContent_4;
    EditText newsContent_5;
    EditText newsContent_6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set1);

        Button button=findViewById(R.id.button14);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                str2=newsContent_2.getText().toString();
                str3=newsContent_3.getText().toString();
                str4=newsContent_4.getText().toString();
                str5=newsContent_5.getText().toString();
                str6=newsContent_6.getText().toString();

                Toast.makeText(v.getContext(), "修改成功", Toast.LENGTH_SHORT).show();

//                new Thread() {
//                    @Override
//                    public void run() {
//                        try {
//                            Socket socket = new Socket(IP.ip, 8807);
//                            // Socket socket = new Socket("172.31.130.158", 8807);
//                            PrintStream ps = new PrintStream(socket.getOutputStream());
//                            ps.println(str1);
//                            ps.flush();
//                            ps.println(str2);
//                            ps.flush();
//                            ps.println(str3);
//                            ps.flush();
//                            ps.println(str4);
//                            ps.flush();
//                            ps.println(str5);
//                            ps.flush();
//                            ps.println(str6);
//                            ps.flush();
//
//
//                            ps.close();
//                            socket.close();
//                        } catch (UnknownHostException e) {
//                            e.printStackTrace();
//                        }catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }.start();

            }
        });
    }
}
