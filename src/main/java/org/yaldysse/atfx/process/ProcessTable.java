package org.yaldysse.atfx.process;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProcessTable
{
    private SimpleIntegerProperty pid;
    private SimpleStringProperty shortCommand;
    private SimpleStringProperty userName;

    public ProcessTable(final int aPid, final String aUser, final String aShortCommand)
    {
        pid = new SimpleIntegerProperty(aPid);
        shortCommand = new SimpleStringProperty(aShortCommand);
        userName = new SimpleStringProperty(aUser);
    }

    public Integer getPid()
    {
        return pid.getValue();
    }

    public String getShortCommand()
    {
        return shortCommand.get();
    }

    public String getUserName()
    {
        return userName.get();
    }

    public SimpleIntegerProperty pid_Property()
    {
        return pid;
    }

    public SimpleStringProperty userNameProperty()
    {
        return userName;
    }

    public SimpleStringProperty shortCommandProperty()
    {
        return shortCommand;
    }
}
