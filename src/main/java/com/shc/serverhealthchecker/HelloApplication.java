package com.shc.serverhealthchecker;

import com.shc.serverhealthchecker.cfgchecker.CfgChecker;
import com.shc.serverhealthchecker.cfgchecker.CfgCheckerView;
import com.shc.serverhealthchecker.model.SHCController;
import com.shc.serverhealthchecker.pwdchecker.PwdChecker;
import com.shc.serverhealthchecker.pwdchecker.PwdCheckerView;
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

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        stage.setTitle("Hello!");

        TextArea ta1 = new TextArea();
        ta1.setMaxWidth(200.0);
        ta1.setMaxHeight(200.0);

        //. create all model, view, and controller class
        this.controller = new SHCController();
        PwdChecker pwdchecker = new PwdChecker(controller);
        PwdCheckerView pwdview = new PwdCheckerView(ta1);
        CfgChecker cfgchecker = new CfgChecker(controller);
        CfgCheckerView cfgview = new CfgCheckerView(ta1);
        this.controller.addChecker(pwdchecker);
        this.controller.addView(pwdview);
        this.controller.addChecker(cfgchecker);
        this.controller.addView(cfgview);
        ta1.setText("hello");
        Scene scene = new Scene(ta1, 900, 500);
        stage.setScene(scene);
        stage.show();

        //SHOULD BE IMPROVED LATER
        pwdchecker.start();
        cfgchecker.start();

    }

    public static void main(String[] args) {
        launch();
    }
}