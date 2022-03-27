package com.shc.serverhealthchecker.viruschecker;

import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCController;

import java.io.BufferedReader;
import java.io.File;
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
                File tempFile = new File("/usr/local/bin/clamscan");
                if(!tempFile.exists()){
                    System.out.println("ClamAV Doesn't exist");
                    Process initial = Runtime.getRuntime().exec("sudo clamStart.sh");
                }
                String filePath = "/UnknownFile/";
                File fileExists = new File(filePath);
                while(!fileExists.exists()){
                    System.out.println("Path doesnt exist");
                    filePath = "/home/";
                    fileExists = new File(filePath);
                }
                System.out.println("File Exists");
                String clam = "sudo clamscan -r ";
                String command = clam.concat(filePath);
                //Process fresh = Runtime.getRuntime().exec("sudo freshclam");
                //this.proc = fresh;
                Process pr = Runtime.getRuntime().exec(command);
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
                exc.printStackTrace();
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
