package com.shc.serverhealthchecker.model;

public class Msg {
    public static final int SEVERE = 0;
    public static final int WARN = 1;
    public static final int DEBUG = 2;
    public static final int ERROR = 3; //

    public int level;
    public String title;
    public String details;
    public String submitter;

    public Msg(int level, String title, String details, String submitter){
        this.level = level;
        this.title = title;
        this.details = details;
        this.submitter = submitter;
    }
}
