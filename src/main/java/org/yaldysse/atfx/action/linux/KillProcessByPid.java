package org.yaldysse.atfx.action.linux;

import org.yaldysse.atfx.action.Action;
import org.yaldysse.atfx.process.ProcessUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Реалізовує дію примусового вбивства процесу з вказаним PID для ОС
 * GNU/Linux.
 * <br>
 * <code>kill -9 &lt;pid&gt;</code>
 */
public class KillProcessByPid implements Action
{
    private ArrayList<String> command;

    /**
     * Ініціює команду, що примусово завершить процес з вказаним PID.
     * <br>
     * <code>
     * kill -9 &lt;pid&gt;
     * </code>
     */
    public KillProcessByPid(final int pid)
    {
        if (pid < 1)
        {
            throw new IllegalArgumentException("1 < N < ?");
        }

        command = new ArrayList<>();
        command.add("kill");
        command.add("-9");
        command.add("" + pid);
    }

    /**
     * Ініціює команду, що примусово завершить процеси, що співпадають
     * по назві.
     * <br>
     * <code>
     * pkill -9 &lt;process_name&gt;
     * </code>
     */
    public KillProcessByPid(final String aProcessName, final boolean aKillProcessChildren)
    {
        command = new ArrayList<>();
        command.add("pkill");
        command.add("-9");
        command.add(aProcessName);
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }
}
