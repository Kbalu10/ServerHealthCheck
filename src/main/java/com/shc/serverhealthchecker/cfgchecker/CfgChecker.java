package com.shc.serverhealthchecker.cfgchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;

//Temporary Code that demonstrates grabbing the username for file paths listed in on outside file, /etc/passwd prints User: root
public class CfgChecker {
        public void start() {
            BufferedReader reader;
            try{
                reader = new BufferedReader(new FileReader("/home/u/Downloads/configfilepaths.txt"));
                String line = reader.readLine();
                while(line != null){
                    Path path = Paths.get(line.toString());
                    FileOwnerAttributeView file = Files.getFileAttributeView(path,
                            FileOwnerAttributeView.class);

                    try {
                        UserPrincipal user = file.getOwner();
                        System.out.println("Owner: " + user.getName());
                        if(user.getName().toString().equals("root")) {
                            System.out.println("Warning: This File Has Root Access!");
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

        }
    }

