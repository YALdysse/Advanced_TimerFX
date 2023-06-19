package org.yaldysse.atfx.action.windows;

import org.yaldysse.atfx.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Реалізовує операцію негайного завершення роботи ОС Windows за допомогою
 * виконання команди:
 * <br>
 * <code>shutdown /r /t 0 /f
 * </code>
 */
public class Reboot implements Action
{
    private ArrayList<String> command;

    public Reboot()
    {
        command = new ArrayList<>();
        command.add("shutdown");
        command.add("/r");
        command.add("/t");
        command.add("0");
        command.add("/f");
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }
}
