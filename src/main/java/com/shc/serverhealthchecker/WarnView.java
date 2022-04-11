package com.shc.serverhealthchecker;

import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCView;
import javafx.scene.control.TextArea;

/** this view ONLY takes warning messeage! */
public class WarnView extends SHCView {
    protected TextArea ta;

    public WarnView(javafx.scene.control.TextArea ta){
        this.ta = ta;
    }
    @Override
    public void displayMsg(Msg msg) {
        if (msg.level<=Msg.WARN) {
            String line = msg.submitter + ": " + msg.title + msg.details;
            this.ta.setText(this.ta.getText() + "\n" + line);
        }
    }
}
