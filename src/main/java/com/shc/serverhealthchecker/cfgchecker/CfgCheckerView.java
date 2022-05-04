package com.shc.serverhealthchecker.cfgchecker;

import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCView;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class CfgCheckerView extends SHCView {
    protected TextArea ta;

    public CfgCheckerView(TextArea ta) {
        this.ta = ta;
    }

    @Override
    public void displayMsg(Msg msg) {
        String line = msg.submitter + ": " + msg.title;
     //   String oldText = ta.getText();
      //  String newText = oldText + "\n" + line;
        //synchronized (ta){this.ta.setText(newText);}
        if (this.ta.getText().length() < 10000) {
            Platform.runLater(() ->
                    //this.ta.setText(newText)
                    this.ta.appendText(line + '\n')
            );
            //redraw screen or control

        }

    }
}
