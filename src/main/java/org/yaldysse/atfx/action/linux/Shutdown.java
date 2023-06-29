package org.yaldysse.atfx.action.linux;

import org.yaldysse.atfx.process.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.IOException;
import java.util.ArrayList;;

/**Реалізовує команду завершення роботи для ОС Linux,
 * використовуючи команду:
 * <br>
 * <code>systemctl poweroff</code>*/
public class Shutdown implements Action
{
    private ArrayList<String> command;

    public Shutdown()
    {
        command = new ArrayList<>();
        command.add("systemctl");
        command.add("poweroff");
    }
    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }
}
