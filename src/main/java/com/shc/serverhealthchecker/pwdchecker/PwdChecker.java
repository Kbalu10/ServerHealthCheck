package com.shc.serverhealthchecker.pwdchecker;

import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PwdChecker extends Checker {

    protected int progress = 0;
    Process proc = null;

    public PwdChecker(SHCController controller){
        super(controller);
    }
    @Override
    public void start() {
        try {
            Process pr = Runtime.getRuntime().exec("ping 8.8.8.8");
            this.proc = pr;

            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                this.progress++;
                this.controller.reportMsg(new Msg(Msg.DEBUG, "state", line, "PwdChecker"));
            }
            pr.waitFor();
            this.progress = 100;
            System.out.println("ok!");

            in.close();
        }catch(Exception exc){
            System.out.println("exception");
        }
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
