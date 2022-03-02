package com.shc.serverhealthchecker.viruschecker;


import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCView;
import javafx.scene.control.TextArea;

public class VirusCheckerView extends SHCView {
    protected TextArea ta;

    public VirusCheckerView(TextArea ta){
        this.ta = ta;
    }
    @Override
    public void displayMsg(Msg msg) {
        String line = msg.submitter + ": " + msg.title;
        this.ta.setText(this.ta.getText() + "\n" + line);
    }

}
