package org.yaldysse.atfx.action.linux;

import org.yaldysse.atfx.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Реалізовує команду перезавантаження ОС для GNU/Linux,
 * використовуючи команду:
 * <br>
 * <code>systemctl reboot</code>*/
public class Reboot implements Action
{
    private ArrayList<String> command;

    public Reboot()
    {
        command = new ArrayList<>();
        command.add("systemctl");
        command.add("reboot");
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }
}
