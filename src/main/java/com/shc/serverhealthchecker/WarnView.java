package com.shc.serverhealthchecker;

import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCView;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import com.shc.serverhealthchecker.HelloController;

/** this view ONLY takes warning messeage! */
public class WarnView extends SHCView {
    protected TextArea ta1;

    public WarnView(javafx.scene.control.TextArea ta1) {
        this.ta1 = ta1;
    }

    @Override
    public void displayMsg(Msg msg) {
        if (msg.level == Msg.WARN) {
            String line = "[" + msg.submitter + "] " + msg.title + ":\n" + msg.details + "\n";
           // String oldText = this.ta1.getText();
           // String newText = oldText + "\n" + line;
            //synchronized (ta){this.ta.setText(newText);}
           // if (this.ta1.getText().length() < 10000) {
                Platform.runLater(() ->
                        //this.ta.setText(newText)
                        this.ta1.appendText(line + '\n')
                );
           // }
            //this.ta1.setText(this.ta1.getText() + "\n" + line);
        }
    }

}
