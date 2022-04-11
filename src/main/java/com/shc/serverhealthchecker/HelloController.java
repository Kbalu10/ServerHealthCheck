package com.shc.serverhealthchecker;


import com.shc.serverhealthchecker.cfgchecker.CfgChecker;
import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCController;
import com.shc.serverhealthchecker.model.SHCView;
import com.shc.serverhealthchecker.pwdchecker.PwdChecker;
import com.shc.serverhealthchecker.pwdchecker.PwdCheckerView;
import com.shc.serverhealthchecker.viruschecker.VirusChecker;
import com.shc.serverhealthchecker.viruschecker.VirusCheckerView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    @FXML
    private Stage stage;
    private Scene scene;
    @FXML
    private ProgressBar progressbar; //
    @FXML
    protected ArrayList<Checker> arrCheckers;//
    protected ArrayList<SHCView> arrViews;  //
    @FXML
    private TextField filePath;
    @FXML
    private Button VirusStartBtn;
    @FXML
    private Label pathErrorLbl;

    public HelloApplication helloApp;
    protected SHCController controller;

   void setHelloApp(HelloApplication app){
        this.helloApp = app;
   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        this.controller.addChecker(viruschecker);
        this.controller.addView(virusview);
    }

    @FXML
    protected void switchScene1(ActionEvent event) throws IOException {
        stopAllCheck();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scene1.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void switchScene2(ActionEvent event) throws IOException { //second user interface
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scene2.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Select tests");
        stage.setScene(scene);


        stage.show();
    }
    @FXML
    protected void switchScene3(ActionEvent event/*boolean [] bSelected*/) throws IOException{ //third user interface
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scene3.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Results");
        stage.setScene(scene);
        stage.show();
        //1. add the SELECTED checker
        this.controller.clearAll();
        /*
        Checker [] arrCheckers = new Checker [] {
                new PwdChecker(this.controller),
                new CfgChecker(this.controller),
                new VirusChecker(this.controller),

        };
        for(int i=0; i<bSelected.length; i++) {
            if(bSelected[i]) {
                this.controller.addChecker(arrCheckers[i]);
            }
        }
        */

        //2. add the views
        TextArea t1 = (TextArea) scene.lookup("#txtArea1");
        WarnView warnview = new WarnView(t1);
        this.controller.addView(warnview);

        //progressbar view


        try {
            startAllCheck();
            stopAllCheck();// Remove Later
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    @FXML
    protected void switchScene3_1(ActionEvent event, boolean [] bSelected) throws IOException { //pwdchecker
       switchScene_worker(event, new boolean [] {true, false, false});
    }

    @FXML
    protected void switchScene3_2(ActionEvent event, boolean [] bSelected) throws IOException { //configchecker
        switchScene_worker(event, new boolean [] {false, true, false});
    }

    @FXML
    protected void switchScene3_3(ActionEvent event, boolean [] bSelected) throws IOException { //virusScan
        switchScene_worker(event, new boolean [] {false, false, true});
    }

    @FXML
    protected void switchScene3_4(ActionEvent event, boolean [] bSelected) throws IOException { //Full system sacn
        switchScene_worker(event, new boolean [] {true, true, true});
    }

    //GUI receive message from checkers
    //send messages from checkers to views
    @FXML
    protected void addCheckers(Checker pwdChecker, Checker virusChecker, Checker configChecker) throws IOException{ //register checkers with controller?
        arrCheckers.add(pwdChecker);
        arrCheckers.add(virusChecker);
        arrCheckers.add(configChecker);

        //implement controller.report
    }
*/
    @FXML
    protected ArrayList<SHCView> getViews(SHCView view) throws IOException{  //dispatch WARNING and ERROR to dialog box1(area1) and dialog box2(area2)
       return this.arrViews;
    }

    @FXML
    protected void switchVirusPath(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("virusPath.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Results");
        stage.setScene(scene);
        stage.show();
    }

    boolean pathExists(String filePath){
        File tempPath = new File(filePath);
        return tempPath.exists();
    }

    @FXML
    void pathCheck(ActionEvent event){
        if(!(filePath.getText()).isEmpty() && pathExists(filePath.getText())){
            VirusStartBtn.setVisible(true);
            pathErrorLbl.setText("");
        }
        else if(!pathExists(filePath.getText())){
            pathErrorLbl.setText("Error: File Path doesn't exist");
        }
        else if(filePath.getText().isEmpty()){
            pathErrorLbl.setText("Error: Invalid file path");
        }
    }



    public void startAllCheck() throws Exception{
       ArrayList<Checker> checkArr = controller.getCheckers();
        for(int i=0; i<checkArr.size(); i++) {
            checkArr.get(i).start();
        }
        Thread.sleep(1000); //For Debugging Remove Later
    }

    public void stopAllCheck() {
        ArrayList<Checker> checkArr = controller.getCheckers();
        for(int i=0; i<checkArr.size(); i++) {
            checkArr.get(i).stop();
        }
    }
}