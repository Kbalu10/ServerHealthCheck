package com.shc.serverhealthchecker.pwdchecker;

import com.shc.serverhealthchecker.model.Checker;
import com.shc.serverhealthchecker.model.SHCController;

public class PwdChecker extends Checker {
    public PwdChecker(SHCController controller){
        super(controller);
    }
    @Override
    public void start() {
        throw new RuntimeException("report message NOT implemented yet!");
    }

    @Override
    public void stop() {
        throw new RuntimeException("report message NOT implemented yet!");
    }

    @Override
    public void resume() {
        throw new RuntimeException("report message NOT implemented yet!");
    }

    @Override
    public int getProgress() {
        return 0;
    }
}
