package org.yaldysse.atfx.action.windows;

import org.yaldysse.atfx.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.IOException;
import java.util.ArrayList;

/**Реалізовує операцію виходу з поточного обліковго запису для
 * ОС Windows.*/
public class Logout implements Action
{
    private ArrayList<String> command;

    public Logout()
    {
        command = new ArrayList<>();
        command.add("logoff");
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }
}