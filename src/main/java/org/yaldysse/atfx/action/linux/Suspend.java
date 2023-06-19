package org.yaldysse.atfx.action.linux;

import org.yaldysse.atfx.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Реалізовує команду переходу в режим сну для ОС Linux,
 * використовуючи команду:
 * <br>
 * <code>systemctl suspend</code>*/
public class Suspend implements Action
{
    private ArrayList<String> command;

    public Suspend()
    {
        command = new ArrayList<>();
        command.add("systemctl");
        command.add("suspend");
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }
}
