package com.example.calenderapp.model;

public class Module {
    long id,time,userid;
    String name,type;

    public Module(long time, long userid, String name, String type) {
        this.time = time;
        this.userid = userid;
        this.name = name;
        this.type = type;
    }
    public Module(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
