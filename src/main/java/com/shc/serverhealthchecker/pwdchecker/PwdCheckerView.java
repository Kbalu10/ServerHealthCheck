package com.shc.serverhealthchecker.pwdchecker;

import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCView;
import javafx.scene.control.TextArea;

public class PwdCheckerView extends SHCView {
    protected TextArea ta;

    public PwdCheckerView(TextArea ta){
        this.ta = ta;
    }
    @Override
    public void displayMsg(Msg msg) {
        if (msg.submitter == "PWD Checker"){
            String line = msg.submitter + ": " + msg.title + ": " + msg.details;
            this.ta.setText(this.ta.getText() + "\n" + line);
        }
    }

}
