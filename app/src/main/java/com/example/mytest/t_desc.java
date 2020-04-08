package com.example.mytest;

public class t_desc {
    int _id;
    String devEUI;
    String remark;

    public t_desc(){

    }

    public t_desc(int _id,String devEUI,String remark) {
        this._id = _id;
        this.devEUI = devEUI;
        this.remark = remark;
    }

    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDevEUI() {
        return devEUI;
    }
    public void setDevEUI(String devEUI) {
        this.devEUI = devEUI;
    }

    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
