package org.yaldysse.atfx.process;

import java.io.Serializable;

/**
 * Інкапсулює дані про процес в операційній системі.
 */
public class Process implements Serializable
{
    private int pid;
    private String shortCommand;
    private String userName;

    public Process(final int aPid, final String aUsername, final String aShortCommand)
    {
        pid = aPid;
        userName = aUsername;
        shortCommand = aShortCommand;
    }

    public int getPid()
    {
        return pid;
    }

    public String getShortCommand()
    {
        return shortCommand;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setPid(int pid)
    {
        this.pid = pid;
    }

    public void setCommand(String command)
    {
        shortCommand= command;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}
