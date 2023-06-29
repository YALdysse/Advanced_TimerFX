package org.yaldysse.atfx.action.windows;

import org.yaldysse.atfx.process.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.IOException;
import java.util.ArrayList;

/**Реалізовує операцію переходу ОС до режиму сну для Windows.*/
public class Suspend implements Action
{
    private ArrayList<String> command;

    public Suspend()
    {
        command = new ArrayList<>();
        //rundll32 powrprof.dll,SetSuspendState 0,1,0";
        command.add("rundll32");
        command.add("powrprof.dll,SetSuspendState");
        command.add("0,1,0");
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }
}

