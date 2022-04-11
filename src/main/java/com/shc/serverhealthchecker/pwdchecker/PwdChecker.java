package com.shc.serverhealthchecker.pwdchecker;

import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PwdChecker extends Checker{

    protected int progress = 0;
    private boolean stop;
    Process proc = null;

    public PwdChecker(SHCController controller){
        super(controller);
    }

    @Override
    public void start() {
        Runnable runnable = () ->
        {
            try {
                Process pr = Runtime.getRuntime().exec("ls");
                this.proc = pr;
                Msg test1 = new Msg(1, "PWD CHECKER TEST PRINT", "PWD MSG TEST", "PWD Checker");
                this.controller.reportMsg(test1);

                BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String line;
                while ((line = in.readLine()) != null && !stop) {
                    System.out.println(line);
                    this.progress++;
                    this.controller.reportMsg(new Msg(Msg.DEBUG, "state", line, "PwdChecker"));
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
        stop = true;
        System.out.println("Thread Stopped");
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
