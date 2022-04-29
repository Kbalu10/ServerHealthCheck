package com.shc.serverhealthchecker;

import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCController;
import com.shc.serverhealthchecker.model.SHCView;
import com.shc.serverhealthchecker.pwdchecker.PwdChecker;
import com.shc.serverhealthchecker.pwdchecker.PwdCheckerView;
import com.shc.serverhealthchecker.viruschecker.VirusChecker;
import com.shc.serverhealthchecker.viruschecker.VirusCheckerView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
   protected SHCController controller;

    @Override
    public void start(Stage stage) throws IOException {

        //Branch Test Commit Stephen Kurtis, This is from the branch "StephenBranch"
        //Test Commit Alexander Sutter
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scene1.fxml"));
        Parent root = fxmlLoader.load();
        HelloController cont = fxmlLoader.getController();
        cont.setHelloApp(this);
        Scene scene = new Scene(root, 485, 268);
        stage.setTitle("Main Menu!");
        stage.setScene(scene);


/*        TextArea ta1 = new TextArea();
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
        this.controller.addChecker(viruschecker);
        this.controller.addView(virusview);*/
        stage.show();

        //SHOULD BE IMPROVED LATER
        //pwdchecker.start();
        //viruschecker.start();


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

/*    public void startAllCheck() throws Exception{
        ArrayList<Checker> checkArr = controller.getCheckers();
        for(int i=0; i<checkArr.size(); i++) {
            checkArr.get(i).start();
        }
        Thread.sleep(1000);
    }

    public void stopAllCheck() throws Exception{
        ArrayList<Checker> checkArr = controller.getCheckers();
        for(int i=0; i<checkArr.size(); i++) {
            checkArr.get(i).stop();
        }
    }*/

    public static void main(String[] args) {
        launch();
    }
}
