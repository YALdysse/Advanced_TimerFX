package org.yaldysse.atfx;

import org.yaldysse.atfx.process.Process;
import org.yaldysse.atfx.process.ProcessUtils;

import java.io.IOException;
import java.util.ArrayList;

public class ProcessList
{
    private ArrayList<Process> processes;

    public ProcessList() throws IOException
    {
        processes = new ArrayList<>();

        String osName = System.getProperty("os.name");

        if (osName.contains("Linux"))
        {
            readProcessesLinux();
        } else if (osName.contains("Windows"))
        {
            readProcessesWindows();
        }
    }

    /**
     * Отримує список всіх процесів в системі за допомогою команди ps.
     * Дані виводяться в такому форматі: PID, USER, COMM.
     */
    private void readProcessesLinux() throws IOException
    {
        ArrayList<String> ps = new ArrayList<>();
        ps.add("ps");
        ps.add("-A");
        ps.add("--format");
        ps.add("pid,user,comm");
        java.lang.Process process = new ProcessBuilder(ps).start();
        ArrayList<String> rawProcessList = ProcessUtils.readProcessOutputToArrayList(process.getInputStream());
        process.destroy();

        processes = workRawProcessDataLinux(rawProcessList);
    }

    /**
     * Обробляє колекцію, що містить 'сирі' дані про процеси, перетворюючи їх
     * на колекцію об'єктів {@link Process}.
     */
    private ArrayList<Process> workRawProcessDataLinux(final ArrayList<String> rawProcessList)
    {
        ArrayList<Process> result = new ArrayList<>();

        Process temporaryProcess;
        String[] splittedData;

        for (int k = 1; k < rawProcessList.size(); k++)
        {
            splittedData = splitRawProcessString(rawProcessList.get(k));
            result.add(new Process(Integer.parseInt(splittedData[0]),
                    splittedData[1], splittedData[2]));
        }

        return result;
    }

    /**
     * Розділяє рядок з даними про процесс на частини, щоб їх було простіще
     * інтерпретувати. Предполагається, що рядок формується зі стовпців
     * PID, USER, COMM.
     */
    private String[] splitRawProcessString(final String processString)
    {
        String[] result = new String[3];
        int resultIndex = 0;

        int startIndex = -1, endIndex = -1;
        char currentChar;
        for (int k = 0; k < processString.length(); k++)
        {
            currentChar = processString.charAt(k);

            if (currentChar != ' ' && currentChar != '\t')
            {
                if (startIndex == -1)
                {
                    startIndex = k;
                    if (resultIndex == 2)
                    {
                        result[resultIndex] = processString.substring(startIndex);
                        break;
                    }
                }
                continue;
            } else
            {
                if (startIndex != -1)
                {
                    endIndex = k;
                    result[resultIndex] = processString.substring(startIndex,
                            endIndex);
                    resultIndex++;
                }
                startIndex = -1;
                endIndex = -1;
            }
        }
        return result;
    }

    private void readProcessesWindows() throws IOException
    {
        ArrayList<String> ps = new ArrayList<>();
        ps.add("tasklist");
        ps.add("/FO");
        ps.add("CSV");
        java.lang.Process process = new ProcessBuilder(ps).start();
        ArrayList<String> rawProcessList = ProcessUtils.readProcessOutputToArrayList(process.getInputStream());
        process.destroy();
        processes = workRawProcessDataWindows(rawProcessList);
    }


    private ArrayList<Process> workRawProcessDataWindows(final ArrayList<String> rawProcessData)
    {
        ArrayList<Process> result = new ArrayList<>();
        String[] splitted;

        String string;
        for (int k = 1; k < rawProcessData.size(); k++)
        {
            string = rawProcessData.get(k);
            splitted = string.split(",");
            result.add(new Process(Integer.parseInt(removeQuatationFromString(splitted[1])),
                    "<UNKNOWN>", removeQuatationFromString(splitted[0])));
        }
        return result;
    }

    /**
     * Видаляє з рядка кавички, що повинні бути розташовані по краях.
     */
    private String removeQuatationFromString(final String text)
    {
        return text.substring(1, text.length() - 1);
    }


    public ArrayList<Process> getProcesses()
    {
        return processes;
    }
}
