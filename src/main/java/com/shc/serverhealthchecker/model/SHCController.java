package com.shc.serverhealthchecker.model;

import java.util.ArrayList;

public class SHCController {
    protected ArrayList<Checker> arrCheckers = new ArrayList();
    protected ArrayList<SHCView> arrViews = new ArrayList();

    public void reportMsg(Msg msg){

      for(int i=0; i<this.arrViews.size(); i++){
          SHCView view = this.arrViews.get(i);
          view.displayMsg(msg);
      }
    }

    public void clearAll(){
        this.arrCheckers.clear();
        this.arrViews.clear();
    }
    public void addChecker(Checker checker){
       this.arrCheckers.add(checker);
    }

    public void addView(SHCView view){
        this.arrViews.add(view);
    }

    public ArrayList<Checker> getCheckers() {return this.arrCheckers;}
    public ArrayList<SHCView> getViews() {return this.arrViews;}
}
