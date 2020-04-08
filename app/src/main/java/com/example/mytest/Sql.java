package com.example.mytest;

import com.example.mytest.Fragment2.Content;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Sql {

    static List<information> info = new ArrayList<>();
    static List<Content> con = new ArrayList<>();
    static List<String> name;
    static String temp;

    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://39.107.123.59:3306/DB_sensor_f8l";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "user_client";
    static final String PASS = "123456";



    public static List Sql(String name) {
        Connection conn = null;
        Statement stmt = null;
        int j=1;
        info.clear();
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT x,y,temperature,voltage FROM t_sensor where devEUI= '"+name+"'";
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
             //   String devEUI = rs.getString(5);
              //  String data = rs.getString(6);
               // double x = rs.getDouble(10);
               // double y = rs.getDouble(11);
             //   String tem_time = rs.getString(14);
               // double temperature = rs.getDouble(12);
              //  double voltage = rs.getDouble(13);

               /* tem_time = tem_time.replace("Z", " UTC");//UTC是本地时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                Date d = null;
                try {
                    d = format.parse(tem_time);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = sf.format(d);
               // System.out.println(date);

                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                int year = cal.get(Calendar.YEAR);//获取年份
                int month = cal.get(Calendar.MONTH) + 1;//获取月份
                int day = cal.get(Calendar.DATE);//获取日
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                int minute = cal.get(Calendar.MINUTE);//分
                int second = cal.get(Calendar.SECOND);//秒
                int WeekOfYear = cal.get(Calendar.DAY_OF_WEEK);//一周的第几天
*/

                double x = rs.getDouble(1);
                double y = rs.getDouble(2);
                double temperature = rs.getDouble(3);
                double voltage = rs.getDouble(4);
                information in=new information(x,y,temperature,voltage);
              //  in.setHour();
                info.add(in);
                j++;

            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return info;
    }

    public static List SelectByDate(String date,String name) {
        Connection conn = null;
        Statement stmt = null;
        int j=1;
        info.clear();
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT x,y,temperature,voltage,tem_time FROM t_sensor where tem_time like '"+date+"%' and devEUI= '"+name+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                double x = rs.getDouble(1);
                double y = rs.getDouble(2);
                double temperature = rs.getDouble(3);
                double voltage = rs.getDouble(4);
                Timestamp time=rs.getTimestamp(5);

                Calendar cal = Calendar.getInstance();
                cal.setTime(time);
                int year = cal.get(Calendar.YEAR);//获取年份
                int month = cal.get(Calendar.MONTH) + 1;//获取月份
                int day = cal.get(Calendar.DATE);//获取日
                int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
                int minute = cal.get(Calendar.MINUTE);//小时

                information in=new information(x,y,temperature,voltage);
                in.setYear(year);
                in.setMonth(month);
                in.setDay(day);
                in.setHour(hour);
                in.setTime(time);
                info.add(in);
                j++;
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return info;
    }

    public static List SelectByName(String name) {
        Connection conn = null;
        Statement stmt = null;
        int j=1;
        info.clear();
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql;
            double x=0;
            double y=0;
            double temperature=0;
            double voltage=0;
            Timestamp time=null;

            sql = "SELECT x,y,temperature,voltage,tem_time FROM t_sensor where devEUI= '"+name+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                x = rs.getDouble(1);
                y = rs.getDouble(2);
                temperature = rs.getDouble(3);
                voltage = rs.getDouble(4);
                time=rs.getTimestamp(5);

            }
            information in=new information(x,y,temperature,voltage);
            in.setTem_time(String.valueOf(time));
            info.add(in);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return info;
    }

    public static List SelectState() {
        Connection conn = null;
        Statement stmt = null;
        name=new ArrayList<String>();
        int j=1;
        con.clear();
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            name=selectName();
            String sql;
            ResultSet rs=null;
            for(int i=0;i<name.size();i++) {
                sql = "SELECT x,y,tem_time,temperature,voltage FROM t_sensor where devEUI= '" + name.get(i)+"'";
                rs = stmt.executeQuery(sql);
                Content news;
                double x=0;
                double y=0;
                double tem=0,vol=0;
                String state=null;
                Timestamp time=new Timestamp(new Date().getTime());
                while (rs.next()) {
                     x = rs.getDouble(1);
                     y = rs.getDouble(2);
                     time = rs.getTimestamp(3);
                     tem= rs.getDouble(4);
                     vol = rs.getDouble(5);
                }
                if(Math.abs(x)>10||Math.abs(y)>10) {
                    state="警告";
                }
                else{
                   state="正常";
                }
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                if("警告".equals(state)) {
                    news = new Content(name.get(i), R.mipmap.tab_better_pressed, state, sf.format(time));
                }
                else{
                    news = new Content(name.get(i), R.mipmap.tab_better_normal, state, sf.format(time));
                }
                news.setX(x);
                news.setY(y);
                news.setTime_2(time);
                news.setTemperature(tem);
                news.setVoltage(vol);
                con.add(news);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return con;
    }

    public static List selectName(){
        Connection conn = null;
        Statement stmt = null;
        name=new ArrayList<String>();
        int j=1;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT distinct devEUI FROM t_sensor";
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                String devEUI = rs.getString(1);
                name.add(devEUI);
                j++;

            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return name;
    }
}