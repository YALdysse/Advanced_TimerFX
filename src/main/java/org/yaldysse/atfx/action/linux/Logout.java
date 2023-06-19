package org.yaldysse.atfx.action.linux;

import org.yaldysse.atfx.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.IOException;
import java.util.ArrayList;

/***/
public class Logout implements Action
{
    //pkill -9 -u $USER
    //killall -u $USER

    private ArrayList<String> command;

    /**
     * При використанні цього конструктору команду буде застосована для поточного користувача.
     */
    public Logout() throws IOException
    {
        createBaseCommand();
        //command.add("$USER"); //------ Буде виконана правильно, тільки якщо передати командній оболонці, наприклад, Bash.
        command.add(determineCurrentUserName());
    }

    /**
     * За допомогою цього конструктору будуть завершені всі
     * процеси користувача з вказаним іменем.
     *@deprecated Не працює коректно.*/
    public Logout(final String userName)
    {
        createBaseCommand();
        command.add(userName);
    }

    private void createBaseCommand()
    {
        command = new ArrayList<>();
        command.add("killall");
        command.add("-u");
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }

    /**Визначає назву облікового запису поточного користувача системи за допомогою
     * виконання команди <code>whoami</code>.*/
    private String determineCurrentUserName() throws IOException
    {
        ProcessBuilder processBuilder = new ProcessBuilder("whoami");
        Process process = processBuilder.start();
        StringBuilder processOutput = ProcessUtils.readProcessOutput(process.getInputStream(), false);
        return processOutput.toString();
    }
}
