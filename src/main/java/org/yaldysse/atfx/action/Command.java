package org.yaldysse.atfx.action;

import org.yaldysse.atfx.ProcessUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Реалізує операцію виконання будь-якої зовнішньої команди.
 */
public class Command implements Action
{
    private ArrayList<String> command;

    /**
     * Передана команда розбивається на частини по пробілам.
     * Теоретично, це не правильно, але воно працює.
     */
    public Command(final String aCommand)
    {
        command = splitStringBySpace(aCommand);
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }

    private ArrayList<String> splitStringBySpace(final String aCommandString)
    {
        return arrayToArrayList(aCommandString.split(" "));
    }

    private ArrayList<String> arrayToArrayList(final String[] array)
    {

        ArrayList<String> arrayList = new ArrayList<>();

        for (String currentString : array)
        {
            arrayList.add(currentString);
        }
        return arrayList;
    }
}
