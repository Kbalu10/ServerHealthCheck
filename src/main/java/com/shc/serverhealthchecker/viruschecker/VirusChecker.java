package com.shc.serverhealthchecker.viruschecker;

import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class VirusChecker extends Checker{

    protected int progress = 0;
    Process proc = null;

    public VirusChecker(SHCController controller){
        super(controller);
    }

    @Override
    public void start() {
        Runnable runnable = () ->
        {
            try {
                Process pr = Runtime.getRuntime().exec("echo VirusCheck");
                this.proc = pr;

                BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    this.progress++;
                    this.controller.reportMsg(new Msg(Msg.DEBUG, "state", line, "VirusChecker"));
                }
                pr.waitFor();
                this.progress = 100;
                System.out.println("ok!");

                in.close();
            } catch (Exception exc) {
                System.out.println("exception");
            }
        };
        Thread t = new Thread(runnable);
        t.start();
    }

    @Override
    public void stop() {
        //proc.wait();
    }

    @Override
    public void resume() {
        //find out how to resume the process
    }

    @Override
    public int getProgress() {
        return this.progress;
    }
}
