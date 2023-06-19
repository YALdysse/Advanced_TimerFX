package org.yaldysse.atfx;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils
{
    /**Створює об'єкт ProcessBuilder з вказаними командами та перенапрявляє
     * поток виводу та помилок процесу до стандартних потоків вводу та помилок відповідно.*/
    public static ProcessBuilder createSimpleAndRedirectOeToBase(List<String> commands)
    {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        return processBuilder;
    }

    /**
     * Зчитує дані з потоку вводу використовуючи таблицю кодування символів UTF-8.
     */
    public static StringBuilder readProcessOutput(final InputStream is, final boolean
                                                  addEndOfLineSymbol) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                is, StandardCharsets.UTF_8));

        String line = null;
        StringBuilder output = new StringBuilder();

        while ((line = br.readLine()) != null)
        {
            output.append(line);

            if(addEndOfLineSymbol)
            {
                output.append("\n");
            }
        }

        return output;
    }

    public static ArrayList<String> readProcessOutputToArrayList(final InputStream is) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                is, StandardCharsets.UTF_8));

        String line = null;
        ArrayList<String> output = new ArrayList<>();

        while ((line = br.readLine()) != null)
        {
            output.add(line);

        }

        return output;
    }
}
