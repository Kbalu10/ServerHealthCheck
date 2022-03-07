package com.shc.serverhealthchecker.cfgchecker;

import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.SHCController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;


public class CfgChecker extends Checker {

    protected int progress = 0;
    Process proc = null;

    public CfgChecker(SHCController controller) {
        super(controller);
    }

    @Override
    public void start() {
        Runnable runnable = () ->
        {
            try {
                Process pr = Runtime.getRuntime().exec("echo ConfigCheck");
                this.proc = pr;

                BufferedReader reader = new BufferedReader(new FileReader("/home/u/Downloads/configfilepaths.txt"));
                String line = reader.readLine();
                while (line != null) {
                    Path path = Paths.get(line);
                    FileOwnerAttributeView file = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
                    try {
                        UserPrincipal user = file.getOwner();
                        System.out.println("File Path: " + path + " | Owner: " + user.getName());
                        if (user.getName().equals("root")) {
                            System.out.println("Warning: " + "\"" + path + "\" " + "Has Root Access!");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        };
        Thread t = new Thread(runnable);
        t.start();
    }

    @Override
    public void stop() {

    }

    @Override
    public void resume() {

    }

    @Override
    public int getProgress() {
        return 0;
    }
}

