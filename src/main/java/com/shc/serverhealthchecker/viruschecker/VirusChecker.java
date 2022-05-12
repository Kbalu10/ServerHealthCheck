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
    protected VirusChecker myself = this; //
    private boolean stop;
    Process proc = null;
    public static String scanPath = "/";

    public VirusChecker(SHCController controller){
        super(controller);
    }

    @Override
    public void start() {
        this.controller.reportMsg(new Msg(Msg.WARN, "Starting", "Starting Virus Checker" , "VirusChecker"));
        Runnable runnable = () ->
        {
            try {
                stop = false;
                File tempFile = new File("/usr/local/bin/clamscan");
                if(!tempFile.exists()){
                    try {
                        System.out.println("ClamAV Doesn't exist");
                        //proc = Runtime.getRuntime().exec("sudo ./external/clamStart.sh");
                        ProcessBuilder inst = new ProcessBuilder();
                        inst.command("sudo", "./external/clamStart.sh", "-s");
                        inst.redirectErrorStream(true);
                        Process process = inst.start();
                        this.proc = process;
                        InputStream inS = process.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inS));

                        String line1 = null;
                        while ((line1 = reader.readLine()) != null && stop != true){
                            System.out.println(line1);
                        }

                        process.waitFor();

                        System.out.println("ok!");

                        //in.close();
                        reader.close();
                    }catch(Exception e) {e.printStackTrace();}
                }
/*
                System.out.println("Starting VirusCheck");
                String clam = "sudo clamscan -r ";
                String command = clam.concat(scanPath);
                //Process fresh = Runtime.getRuntime().exec("sudo freshclam");
                //this.proc = fresh;
                Process pr = Runtime.getRuntime().exec(command);
                this.proc = pr;
                BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    this.progress++;
                    this.controller.reportMsg(new Msg(Msg.WARN, "state", line, "VirusChecker"));
                }
*/
                List<String> clam = new ArrayList<String>();
                clam.add("sudo"); clam.add("clamscan"); clam.add("-r"); clam.add(scanPath);
                System.out.println("Scanning "+scanPath);
                ProcessBuilder cmd = new ProcessBuilder(clam);
                cmd.redirectErrorStream(true);
                Process process = cmd.start();
                this.proc = process;
                InputStream is = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line = null;
                while ((line = reader.readLine()) != null && stop != true){
                    System.out.println(line);
                    //this.controller.reportMsg(new Msg(Msg.WARN, "state", line, "VirusChecker"));
                    myself.progress++; //
                    if(line.indexOf("ERROR")>=0){ //
                        this.controller.reportMsg(new Msg(Msg.ERROR, "state", line, "VirusChecker"));
                    }else{
                        this.controller.reportMsg(new Msg(Msg.WARN, "state", line, "VirusChecker"));
                    }
                }


                //process.waitFor();
                proc.waitFor();
                myself.progress = 100; //
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
