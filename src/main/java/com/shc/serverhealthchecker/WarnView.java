package com.shc.serverhealthchecker;

import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCView;
import javafx.scene.control.TextArea;

/** this view ONLY takes warning messeage! */
public class WarnView extends SHCView {
    protected TextArea ta1;

    public WarnView(javafx.scene.control.TextArea ta1){
        this.ta1 = ta1;
    }
    @Override
    public void displayMsg(Msg msg) {
        if (msg.level>=Msg.WARN) {
            String line = "[" + msg.submitter + "] " + msg.title + ":\n" + msg.details + "\n";
            this.ta1.setText(this.ta1.getText() + "\n" + line);
        }
    }
}
