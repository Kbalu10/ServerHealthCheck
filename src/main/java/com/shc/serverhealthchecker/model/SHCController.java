package com.shc.serverhealthchecker.model;

import java.util.ArrayList;

public class SHCController {
    protected ArrayList<Checker> arrCheckers = new ArrayList();
    protected ArrayList<SHCView> arrViews = new ArrayList();

    public void reportMsg(Msg msg){
        throw new RuntimeException("report message NOT implemented yet!");
    }
    public void addChecker(Checker checker){
        throw new RuntimeException("report message NOT implemented yet!");
    }

    public void addView(SHCView view){
        throw new RuntimeException("report message NOT implemented yet!");
    }

    public ArrayList<Checker> getCheckers() {return this.arrCheckers;}
    public ArrayList<SHCView> getViews() {return this.arrViews;}
}
