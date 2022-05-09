package com.shc.serverhealthchecker.cfgchecker;

import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCController;
import javafx.scene.control.TextArea;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.sql.*;


public class CfgChecker extends Checker {

    protected int progress = 0;
    private boolean stop;
    Process proc = null;

    public CfgChecker(SHCController controller) {
        super(controller);
    }

    @Override
    public void start() {
        System.out.println("CFG");
        Runnable runnable = () ->
        {
            try {
                stop = false;
                Process pr = Runtime.getRuntime().exec("echo ConfigCheck");
                this.proc = pr;
                BufferedReader reader = new BufferedReader(new FileReader("external/CFGPaths"));
                String line = reader.readLine();

                //Reads hashed passwords from mysql user database and sends message if default hashing method is used or no password
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "root");
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select * from user");
                    //String hashingMSG = ""
                    //Msg sqlPass = new Msg(1, "MySQL Hashing", hashingMSG, "CFG Checker");
                    while (resultSet.next()) {
                        String password = resultSet.getString("authentication_string");
                        String user = resultSet.getString("user");
                        //System.out.println(user + ": " + password);

                        if (password.isEmpty()) {
                            //System.out.println("Blank");
                            String emptyPassStr = user + ": has no password set!";
                            Msg emptyPass = new Msg(3, "Empty MySQL Password", emptyPassStr, "CFG Checker");
                            this.controller.reportMsg(emptyPass);
                        } else if (password.charAt(0) == '*') {
                            //System.out.println("Password is default dumbo");
                            String hashPass = user + ": is using the default(weak) hashing method on their password!";
                            Msg defaultHash = new Msg(1, "Weak MySQL Hashing", hashPass, "CFG Checker");
                            this.controller.reportMsg(defaultHash);
                        }


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                //parses CFGPaths file which contains important CFG file paths
                while (line != null) {
                    Path path = Paths.get(line);

                    //checks if the file path exists
                    if(Files.exists(path)) {
                        FileOwnerAttributeView file = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
                        try {
                            //Checks the owner from the CFGPaths list of CFG files and gives a warning if the owner is root
                            UserPrincipal user = file.getOwner();
                            String ownerMsg = "File Path: " + path + " | Owner: " + user.getName();
                            Msg ownerName = new Msg(1, "File Owner", ownerMsg, "CFG Checker");
                            this.controller.reportMsg(ownerName);
                            //System.out.println("File Path: " + path + " | Owner: " + user.getName());
                            if (user.getName().equals("root")) {
                                //System.out.println("Warning: " + "\"" + path + "\" " + "Has Root Access!");
                                String hasRoot = "Warning: " + "\"" + path + "\" " + "Has Root Access!";
                                Msg rootMsg = new Msg(1, "Root Access", hasRoot, "CFG Checker");
                                this.controller.reportMsg(rootMsg);
                            }

                            //Determines when the current file was last modified and warns if it is within 24 hours
                            Path detailedfilet = Paths.get(String.valueOf(path));
                            BasicFileAttributes attr = Files.readAttributes(detailedfilet, BasicFileAttributes.class);
                            String filename = String.valueOf(path);
                            File filet = new File(filename);
                            long lastmodded = filet.lastModified();
                            String modString = path + " has been modified within the last 24 hours.\nLast Modified: " + attr.lastModifiedTime();
                            if (lastmodded > System.currentTimeMillis() - 86400000) {
                                Msg modMsg = new Msg(1, "File Modified", modString, "CFG Checker");
                                this.controller.reportMsg(modMsg);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    //Reports message that file path is invalid
                    else{
                        String invalidPath = "'" + path + "' Does not exist";
                        Msg noPath = new Msg(3, "Invalid File Path", invalidPath, "CFG Checker");
                        this.controller.reportMsg(noPath);
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
            stop = true;
            System.out.println("Thread Stopped");
    }

    @Override
    public void resume() {

    }

    @Override
    public int getProgress() {
        return 0;
    }
}

