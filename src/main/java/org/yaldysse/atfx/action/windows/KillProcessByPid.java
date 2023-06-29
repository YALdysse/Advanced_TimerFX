package org.yaldysse.atfx.action.windows;

import org.yaldysse.atfx.process.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Реалізовує операцію примусового завершення процесу з його дочірніми процесами
 * за унікальним номером (PID) для ОС Windows.
 * <br>
 * <code>taskkill /PID &lt;value&gt; /F /T
 * </code>
 */
public class KillProcessByPid implements Action
{
    private ArrayList<String> command;

    /**
     * @param aProcessID           Ідентифікатор процесу.
     * @param aKillChildrenProcess Визначає, чи варто примусова закривати
     *                             дочірні процеси.
     */
    public KillProcessByPid(final int aProcessID, final boolean aKillChildrenProcess)
    {
        if (aProcessID < 0)
        {
            throw new IllegalArgumentException("PID не може бути меншим за нуль.");
        }

        command = new ArrayList<>();
        command.add("taskkill");
        command.add("/PID");
        command.add("" + aProcessID);
        command.add("/F");

        if (aKillChildrenProcess)
        {
            command.add("/T");
        }
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }
}