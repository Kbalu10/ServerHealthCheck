package com.shc.serverhealthchecker.pwdchecker;

import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCController;

import java.io.*;

public class PwdChecker extends Checker{

    protected int progress = 0;
    protected PwdChecker myself = this;
    private boolean stop;
    Process proc = null;

    public PwdChecker(SHCController controller){
        super(controller);
    }

    @Override
    public void start() {
        this.controller.reportMsg(new Msg(Msg.WARN, "Starting", "Starting Password Checker" , "PDWChecker"));
        Runnable runnable = () ->
        {
            try {
                //Process pr = Runtime.getRuntime().exec("john --wordlist=/home/u/Downloads/rockyou.txt /home/u/Downloads/mypasswd");
                Process pr = Runtime.getRuntime().exec("unshadow /etc/passwd /etc/shadow");
                this.proc = pr;


                BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String line;
                File file = new File("unshadowed.txt");
                FileWriter fw = new FileWriter(file);
                PrintWriter pw = new PrintWriter(fw);

                while ((line = in.readLine()) != null && !stop) {
                    System.out.println(line);
                    pw.println(line);
                    myself.progress++; //
                }
                pw.close();
                //proc.waitFor();


                Process pr2 = Runtime.getRuntime().exec("john --wordlist=/home/u/Downloads/rockyou.txt unshadowed.txt");
                //Process pr2 = Runtime.getRuntime().exec("john --single unshadowed.txt");
                this.proc = pr2;
                BufferedReader in2 = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
                while ((line = in2.readLine()) != null && !stop) {
                    System.out.println(line);
                    if(line.indexOf("ERROR")>=0){ //
                        this.controller.reportMsg(new Msg(Msg.ERROR, "Cracking", line, "PDWChecker"));
                    }else{
                        this.controller.reportMsg(new Msg(Msg.WARN, "Cracking", line, "PWDChecker"));
                    }
                }
                proc.waitFor();

                Process pr3 = Runtime.getRuntime().exec("john --show unshadowed.txt");
                this.proc = pr3;
                BufferedReader in3 = new BufferedReader(new InputStreamReader(pr3.getInputStream()));
                while ((line = in3.readLine()) != null && !stop) {
                    System.out.println(line);
                    if(!line.isEmpty()){
                        if(line.indexOf("ERROR")>=0){ //
                            this.controller.reportMsg(new Msg(Msg.ERROR, "Cracked Password", line, "PWDChecker"));
                        }else{
                            this.controller.reportMsg(new Msg(Msg.WARN, "Cracked Password", line, "PWDChecker"));
                        }
                    }


                }
                proc.waitFor();


                myself.progress = 100; //
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
