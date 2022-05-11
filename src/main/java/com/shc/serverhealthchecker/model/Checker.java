package com.shc.serverhealthchecker.model;

public abstract class Checker {
    protected SHCController controller;

    public Checker(SHCController controller){
        this.controller = controller;
    }

    public abstract void start();

    public abstract void stop();

    public abstract void resume();

    public abstract int getProgress();
}
