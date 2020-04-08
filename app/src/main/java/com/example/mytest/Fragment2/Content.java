package com.example.mytest.Fragment2;

import java.sql.Timestamp;

public class Content {
    private String name;
    private String state;
    private String time;
    private int imageId;
    Timestamp time_2;
    private double x;
    private double y;
    private double temperature;
    private double voltage;

    public Timestamp getTime_2() {
        return time_2;
    }

    public void setTime_2(Timestamp time_2) {
        this.time_2 = time_2;
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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Content(String name,int imageId,String state,String time ){
        this.name=name;
        this.imageId=imageId;
        this.state=state;
        this.time=time;
    }
    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setTime(String time){
        this.time=time;
    }
    public void setState(String state){
        this.state=state;
    }
    public String getName(){
        return name;
    }
    public String getState(){
        return state;
    }
    public String getTime(){
        return time;
    }
    public int getImageId(){
        return imageId;
    }
}
