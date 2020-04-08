package com.example.mytest.Fragment2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.example.mytest.IP;
import com.example.mytest.MainActivity;
import com.example.mytest.R;
import com.example.mytest.RegisteredActivity;
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

public class MyFragment2 extends Fragment {

    String line;

    static List<information> newsList ;
    NewsAdapter adapter;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 11) {
               // adapter = new NewsAdapter(newsList);
                //Toast.makeText(getActivity(),"qq"+String.valueOf(name.get(1)), Toast.LENGTH_SHORT).show();
               // newsList_2=newsList;
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mfg2_content, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
       //if(newsList_2.isEmpty())
        initNews();

        try {
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*for (int j = 0; j < name.size()-1; j ++) {
                Content news = new Content( name.get(j), R.mipmap.tab_better_pressed, "正常", "2020-12-25");
                newsList_2.add(news);
        }*/
        Toast.makeText(getActivity(), "the length:" + String.valueOf(newsList.size()), Toast.LENGTH_SHORT).show();

        adapter = new NewsAdapter(newsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    private void initNews() {
//     Thread t=new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Sql sql = new Sql();
//                   // name = sql.selectName();
//                    newsList=sql.SelectState();
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
//     t.start();

        Thread t=new Thread() {
            @Override
            public void run() {
                try {

                    Socket socket = new Socket(IP.ip, 8887);

                    PrintStream ps = new PrintStream(socket.getOutputStream());
                    ps.println("test_2");
                    ps.flush();

                    BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    line = br.readLine();

                    newsList = JSONArray.parseArray(line, information.class);

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
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<information> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder {
            View newsView;
            ImageView item1;
            TextView item2;
            TextView item3;
            TextView item4;

            public ViewHolder(View view) {
                super(view);
                newsView = view;
                item1 = (ImageView) view.findViewById(R.id.item_1);
                item2 = (TextView) view.findViewById(R.id.item_2);
                item3 = (TextView) view.findViewById(R.id.item_3);
                item4 = (TextView) view.findViewById(R.id.item_4);
            }
        }

        public NewsAdapter(List<information> newsList) {
            mNewsList = newsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mfg2_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
//            holder.newsView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = holder.getAdapterPosition();
//                    Content news = mNewsList.get(position);
//                    Intent intent = new Intent(getActivity(), MessageActivity.class);
//                    intent.putExtra("position", position);
//                    getActivity().startActivity(intent);
////                    Intent intent=new Intent(getActivity(), MessageActivity.class);
////                    startActivity(intent);
//                }
//            });
            holder.item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    information news = mNewsList.get(position);
                    Toast.makeText(v.getContext(), "you clicked newsImage " + news.getDevEUI(), Toast.LENGTH_SHORT).show();

                }
            });
            holder.item2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    information news = mNewsList.get(position);
                    Intent intent = new Intent(getActivity(), MessageActivity.class);
                    intent.putExtra("position", position);
                    getActivity().startActivity(intent);
//                    Intent intent=new Intent(getActivity(), MessageActivity.class);
//                    startActivity(intent);
                }
            });
            holder.item3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    information news = mNewsList.get(position);
                    Intent intent = new Intent(getActivity(), MessageActivity.class);
                    intent.putExtra("position", position);
                    getActivity().startActivity(intent);
                }
            });
            holder.item4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    information news = mNewsList.get(position);
                    Intent intent = new Intent(getActivity(), MessageActivity.class);
                    intent.putExtra("position", position);
                    getActivity().startActivity(intent);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            information news = mNewsList.get(position);
            if("0".equals(news.getState())) {
                news.setImageId(R.mipmap.tab_better_pressed);
                news.setState("警告");
            }
            else{
                news.setImageId(R.mipmap.tab_better_normal);
                news.setState("正常");
            }
            holder.item1.setImageResource(news.getImageId());
            holder.item2.setText(news.getDevEUI());
            holder.item3.setText(news.getState());
            holder.item4.setText(news.getTem_time());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }
}