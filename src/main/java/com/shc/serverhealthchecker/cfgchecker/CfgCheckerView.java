package com.shc.serverhealthchecker.cfgchecker;

import com.shc.serverhealthchecker.model.Msg;
import com.shc.serverhealthchecker.model.SHCView;
import javafx.scene.control.TextArea;

public class CfgCheckerView extends SHCView{
        protected TextArea ta;

        public CfgCheckerView(TextArea ta){
            this.ta = ta;
        }
        @Override
        public void displayMsg(Msg msg) {
            String line = msg.submitter + ": " + msg.title + ": " + msg.details;
            this.ta.setText(this.ta.getText() + "\n" + line);
        }

    }


