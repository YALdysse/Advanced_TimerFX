package org.yaldysse.atfx.action.windows;


import org.yaldysse.atfx.process.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.IOException;
import java.util.ArrayList;

/**Реалізовує операцію негайного завершення роботи ОС Windows за допомогою
 * виконання команди:
 * <br>
 * <code>shutdown /p /f
 * </code>*/
public class Shutdown implements Action
{
    private ArrayList<String> command;

    public Shutdown()
    {
        command = new ArrayList<>();
        command.add("shutdown");
        command.add("/p");
        command.add("/f");
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }
}
