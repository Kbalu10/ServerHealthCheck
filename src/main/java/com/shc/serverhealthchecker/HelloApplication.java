package com.shc.serverhealthchecker;

import com.shc.serverhealthchecker.model.SHCController;
import com.shc.serverhealthchecker.pwdchecker.PwdChecker;
import com.shc.serverhealthchecker.pwdchecker.PwdCheckerView;
import com.shc.serverhealthchecker.viruschecker.VirusChecker;
import com.shc.serverhealthchecker.viruschecker.VirusCheckerView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    protected SHCController controller;

    @Override
    public void start(Stage stage) throws IOException {

        //Branch Test Commit Stephen Kurtis, This is from the branch "StephenBranch"
        //Test Commit Alexander Sutter
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        TextArea ta1 = new TextArea();
        ta1.setMaxWidth(200.0);
        ta1.setMaxHeight(200.0);


        //. create all model, view, and controller class
        this.controller = new SHCController();
        PwdChecker pwdchecker = new PwdChecker(controller);
        PwdCheckerView pwdview = new PwdCheckerView(ta1);
        VirusChecker viruschecker = new VirusChecker(controller);
        VirusCheckerView virusview = new VirusCheckerView(ta1);
        this.controller.addChecker(pwdchecker);
        this.controller.addView(pwdview);
        stage.show();

        //SHOULD BE IMPROVED LATER
        pwdchecker.start();
        /*
        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("Starting");
                pwdchecker.start();
                System.out.println("Ending");
            }
        });

        t.start();
        */
    }

    public static void main(String[] args) {
        launch();
    }
}