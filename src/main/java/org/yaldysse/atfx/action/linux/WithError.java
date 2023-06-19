package org.yaldysse.atfx.action.linux;

import org.yaldysse.atfx.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.IOException;
import java.util.ArrayList;

public class WithError implements Action
{
    private ArrayList<String> command;

    public WithError()
    {
        command = new ArrayList<>();
//        command.add("bash");
//        command.add("-c");
        command.add("i_want_to_reboot");
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }
}
