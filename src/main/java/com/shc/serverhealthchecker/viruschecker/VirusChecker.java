package com.shc.serverhealthchecker.viruschecker;

import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCController;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VirusChecker extends Checker{

    protected int progress = 0;
    private boolean stop;
    Process proc = null;


    public VirusChecker(SHCController controller){
        super(controller);
    }

    @Override
    public void start() {
        Runnable runnable = () ->
        {
            try {
                stop = false;
                File tempFile = new File("/usr/local/bin/clamscan");
                if(!tempFile.exists()){
                    System.out.println("ClamAV Doesn't exist");
                    Process initial = Runtime.getRuntime().exec("sudo clamStart.sh");
                }
                /*
                System.out.println("Starting VirusCheck");
                String filePath = "/UnknownFile/";
                File fileExists = new File(filePath);
                while(!fileExists.exists()){
                    System.out.println(filePath+": Path doesnt exist");
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

                 */
                List<String> clam = new ArrayList<String>();
                clam.add("sudo"); clam.add("clamscan"); clam.add("-r"); clam.add("/");
                ProcessBuilder cmd = new ProcessBuilder(clam);
                cmd.redirectErrorStream(true);
                Process process = cmd.start();
                this.proc = process;
                InputStream is = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line = null;
                while ((line = reader.readLine()) != null && stop != true){
                    System.out.println(line);
                    this.controller.reportMsg(new Msg(1, "state", line, "VirusChecker"));
                    this.progress++;
                }

                process.waitFor();
                this.progress = 100;
                System.out.println("ok!");

                //in.close();
                reader.close();
            } catch (Exception exc) {
                System.out.println("Virus exception");
                exc.printStackTrace();
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
