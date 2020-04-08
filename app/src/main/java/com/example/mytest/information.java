package com.example.mytest;

import java.sql.Timestamp;

public class information {
    private String devEUI;
    private String data;
    private double x;
    private double y;
    private double temperature;
    private String tem_time;
    private double voltage;
    int year;
    int month;
    int day;
    int hour;
    int imageId;
    String state;


    Timestamp time;

    public information(){

    }

    public information(double x,double y,double temperature,double voltage){
        this.x=x;
        this.y=y;
        this.temperature=temperature;
        this.voltage=voltage;
    }

    public information(String devEUI,String data,double x,double y,double temperature,String tem_time,double voltage){
        this.devEUI=devEUI;
        this.data=data;
        this.x=x;
        this.y=y;
        this.temperature=temperature;
        this.tem_time=tem_time;
        this.voltage=voltage;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }


    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }



    public String getTem_time() {
        return tem_time;
    }

    public void setTem_time(String tem_time) {
        this.tem_time = tem_time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public String getDevEUI() {
        return devEUI;
    }

    public void setDevEUI(String devEUI) {
        this.devEUI = devEUI;
    }

}
