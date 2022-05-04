package com.shc.serverhealthchecker;

import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCController;
import com.shc.serverhealthchecker.model.SHCView;
import com.shc.serverhealthchecker.pwdchecker.PwdChecker;
import com.shc.serverhealthchecker.pwdchecker.PwdCheckerView;
import com.shc.serverhealthchecker.viruschecker.VirusChecker;
import com.shc.serverhealthchecker.viruschecker.VirusCheckerView;
import com.shc.serverhealthchecker.cfgchecker.CfgChecker;
import com.shc.serverhealthchecker.cfgchecker.CfgCheckerView;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public TextArea txtArea1;
    @FXML
    public TextArea txtArea2;
    @FXML
    private Stage stage;
    private Scene scene;
    @FXML
    private TextArea textArea = new TextArea(); //
    private TextArea textArea1 = new TextArea(); //
    @FXML
    private Task task; //
    @FXML
    protected ArrayList<Checker> arrCheckers;//
    protected ArrayList<SHCView> arrViews;  //
    @FXML
    private TextField filePath;
    @FXML
    private Button virusCheckBtn;
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
        CfgChecker cfgchecker = new CfgChecker(controller);
        CfgCheckerView cfgview = new CfgCheckerView(ta1);
        this.controller.addChecker(pwdchecker);
        this.controller.addView(pwdview);
        this.controller.addChecker(cfgchecker);
        this.controller.addView(cfgview);
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
    protected void switchScene3(ActionEvent event) throws IOException { //third user interface
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scene3.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());

        Node node = (Node) event.getSource();
        String id = node.getId();

        stage.setTitle("Results");
        stage.setScene(scene);
        stage.show();

        //1. add the SELECTED checker
        //this.controller.clearAll();

        /*
        for (int i = 0; i < bSelected.length; i++) {
            if (bSelected[i]) {
                this.controller.addChecker(arrCheckers[i]);
            }
        }
*/
        //Warning, Error view

        textArea = (TextArea) scene.lookup("#txtArea1");
        WarnView warnview = new WarnView(textArea);
        this.controller.addView(warnview);

        textArea1 = (TextArea) scene.lookup("#txtArea2");
        ErrorView errorview = new ErrorView(textArea1);
        this.controller.addView(errorview);

        /*
        WarnView warnview = new WarnView(txtArea1);
        this.controller.addView(warnview);

        ErrorView errorview = new ErrorView(txtArea2);
        this.controller.addView(errorview);
 */
        //progressbar view
        ProgressBar progressbar1 = (ProgressBar) scene.lookup("#progressbar1");
        ProgressBar progressbar2 = (ProgressBar) scene.lookup("#progressbar2");
        ProgressBar progressbar3 = (ProgressBar) scene.lookup("#progressbar3");
        //ProgressBar progressbar4 = (ProgressBar) scene.lookup("#progressbar4");


        if (Objects.equals(id, "pwdCheckBtn")) {
            System.out.println("PASSWORD");
            startSingleCheck(0);
            progress(progressbar1,this.controller.getCheckers().get(0));
        }
        if (Objects.equals(id, "cfgCheckBtn")) {
            startSingleCheck(1);
            progress(progressbar2,this.controller.getCheckers().get(1));
        }
        if (Objects.equals(id, "virusCheckBtn")) {
            startSingleCheck(2);
            progress(progressbar3,this.controller.getCheckers().get(2));
        }
        if (Objects.equals(id, "fullCheckBtn")) {
            try {
                startAllCheck();
                progress(progressbar1,this.controller.getCheckers().get(0));
                progress(progressbar2,this.controller.getCheckers().get(1));
                progress(progressbar3,this.controller.getCheckers().get(2));
                //progress(progressbar4, null); //TO FIX LATER
                //stopAllCheck();// Remove Later
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void progress(ProgressBar bar, Checker check){   //
        bar.setProgress(0);
        task = new MyTask(bar,check);
        bar.progressProperty().unbind();
        bar.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                System.out.println("Task completed!");
            }
        });
        new Thread(task).start();
    }

    class MyTask extends Task{ //
       protected ProgressBar pbar;
       protected Checker checker;
       public MyTask(ProgressBar bar, Checker checkerIn){
           this.pbar = bar;
           this.checker = checkerIn;
       }
        @Override
        protected Object call() throws Exception {
            for(int i = 0; i< 10; i++) {
                Thread.sleep(100);
                //if(bar!=null){
                //updateMessage("Task completed: " + ((i*10)+10) + "%");
              /*
                if(i>=8) {
                    if(checker.getProgress()!=100) {
                        updateProgress(9, 10);
                    }else{
                        updateProgress(10, 10);
                    }
                }
               */
                updateProgress(i+1, 10);
                //bar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
                System.out.println(this.pbar.getProgress());
                //}
            }
            return true;
        }
    }

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
            virusCheckBtn.setVisible(true);
            pathErrorLbl.setText("");
            VirusChecker.scanPath = filePath.getText();
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
        //Thread.sleep(1000); //For Debugging Remove Later
    }

    public void stopAllCheck() {
        ArrayList<Checker> checkArr = controller.getCheckers();
        for(int i=0; i<checkArr.size(); i++) {
            checkArr.get(i).stop();
        }
    }

    public void startSingleCheck(int checkType) /*throws Exception*/{
        ArrayList<Checker> checkArr = controller.getCheckers();
        checkArr.get(checkType).start();
        //Thread.sleep(1000); //For Debugging Remove Later
    }
    public void stopSingleCheck(int checkType) /*throws Exception*/{
        ArrayList<Checker> checkArr = controller.getCheckers();
        checkArr.get(checkType).stop();
        //Thread.sleep(1000); //For Debugging Remove Later
    }


}