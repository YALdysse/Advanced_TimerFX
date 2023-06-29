package org.yaldysse.atfx.action.linux;

import org.yaldysse.atfx.process.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Реалізовує операції зміни яскравості дисплею (як інтенсивності світіння, та як гамми) для ОС GNU/Linux.
 * Дозволяє використовувати один з методів, що визначається переліком {@link BrightnessMethod}.
 * <br>Метод {@link BrightnessMethod#XRANDR} змінює значення гамми для поточного монітору,
 * а метод {@link BrightnessMethod#BACKLIGHT_CONFIG_FILE} змінює безпосередньо інтенсивність
 * світіння монітора.
 * <br><strong>Важливо: для коректної роботи методу {@link BrightnessMethod#BACKLIGHT_CONFIG_FILE}
 * необхідній права суперкористувача.</strong>
 * <br><br>
 * Працює за допомогою команди <em>xrandr</em>:
 * <br>
 * <code>xrandr --output LVDC --brightness 0.3
 * </code>
 */
/*
* qdbus org.kde.Solid.PowerManagement /org/kde/Solid/PowerManagement/Actions/BrightnessControl org.kde.Solid.PowerManagement.Actions.BrightnessControl.setBrightness 0
 */
public class Brightness implements Action
{
    //xrandr --listmonitors
    //xrandr --output LVDS-1 --brightness 0.75
    private final ArrayList<String> command;
    private byte brightnessInPercent;
    private BrightnessMethod brightnessMethod;
    private Path backlightBrightnessConfigFile_Path;
    private short brightnessForBacklightConfigFile;

    /**
     * @deprecated
     */
    private Brightness(final byte aBrightnessInPercent)
    {
        if (aBrightnessInPercent < (byte) 0 || aBrightnessInPercent > (byte) 100)
        {
            throw new IllegalArgumentException("Значення повинно бути в діапазоні від 0 до 100");
        }
        brightnessInPercent = aBrightnessInPercent;

        command = new ArrayList<>();
        command.add("xrandr");
        command.add("--output");
        //momitor name
        command.add("--brightness");
        //brighness value
    }

    public Brightness(final BrightnessMethod aBrightnessMethod,
                      final byte aBrightnessInPercent) throws IOException
    {
        if (aBrightnessInPercent < (byte) 0 || aBrightnessInPercent > (byte) 100)
        {
            throw new IllegalArgumentException("Значення повинно бути в діапазоні від 0 до 100");
        }

        brightnessMethod = aBrightnessMethod;
        brightnessInPercent = aBrightnessInPercent;
        command = new ArrayList<>();

        preparingToPerform();
    }

    /**
     * Створює команду (заповнює колекцію) командою для зміни яскравості (гамми монитору).
     */
    private void createCommandForXrandrMethod() throws IOException
    {
        command.clear();
        command.add("xrandr");
        command.add("--output");
        command.add(determinePrimaryMonitorName());
        command.add("--brightness");
        command.add("" + (double) brightnessInPercent / 100.0D);
    }

    @Override
    public void perform() throws IOException
    {
        switch (brightnessMethod)
        {
            case XRANDR:
                ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
                break;
            case BACKLIGHT_CONFIG_FILE:
                setBrightnessUsingBacklightConfigFile(backlightBrightnessConfigFile_Path,
                        brightnessForBacklightConfigFile);
                break;
        }
    }

    private String determinePrimaryMonitorName() throws IOException
    {
        //визначаємо поточний монітор
        ProcessBuilder processBuilder = new ProcessBuilder("xrandr", "--listmonitors");
        Process process = processBuilder.start();

        StringBuilder monitorList = ProcessUtils.readProcessOutput(process.getInputStream(), true);
        String primaryMonitorRow = getPrimaryMonitorRowFromRawMonitorList(monitorList.toString().split("\n"));
        return primaryMonitorRow.split(" ")[5];
    }


    /**
     * Знаходить в масиві рядків з моніторами активний.
     * Активний монітор скоріш за все виділяється символом '*':
     * <br>
     * Вивід команди '<code>xrandr --listmonitors</code>':
     * <br>
     * <code>
     * Monitors: 1  <br>
     * 0: +*LVDS 1600/310x900/174+0+0  LVDS
     * </code>
     */
    private String getPrimaryMonitorRowFromRawMonitorList(final String[] monitorList)
    {
        for (String string : monitorList)
        {
            if (string.contains("*"))
            {
                return string;
            }
        }
        return "<NONE>";
    }

    /**
     * Розраховує та повертає шлях до конфігураційного файлу, в якому міститься
     * значення яксравості екрану. Значення яскравості записується в діапазоні від 0 до 255.
     * Як правило, цей файл знаходиться за шляхом:
     * <br>
     * <em>/sys/class/backlight/  <strong>&lt;gpu_name&gt;</strong>  /brightness</em>
     */
    private Path definePathToBacklightBrightnessConfigFile() throws IOException
    {
        Path basePath = Paths.get("/sys/class/backlight/");
        return getFirstBacklightFolderName().resolve("brightness");
    }

    private Path getFirstBacklightFolderName() throws IOException
    {
        Path basePath = Paths.get("/sys/class/backlight/");
        Stream<Path> files = Files.list(basePath);
        return (Path) files.toArray()[0];
    }

    private void setBrightnessUsingBacklightConfigFile(final Path backlightConfigFile,
                                                       final short aBrightnessForBacklight) throws IOException
    {
        writeStringToFile(backlightConfigFile, "" + aBrightnessForBacklight);
    }

    /**
     * Перетворює значення яскравості у відсотків в відповідне значення формату файлу
     * Brightness.
     */
    private short convertPercentToUnsignedByteValue(final byte value)
    {
        //100 = 255
        //50 = 127;
        //25 = x
        //
        //value * 255)
        return (short) ((value * 255) / 100);
    }

    /**
     * Записує рядок до вказаного файлу. Якщо вказаного файлу не існує, то він
     * буде створений з таблицей кодування символів UTF-8.
     */
    private void writeStringToFile(final Path targetPath, final String value) throws IOException
    {
        try (BufferedWriter bw = Files.newBufferedWriter(targetPath, StandardCharsets.UTF_8, StandardOpenOption.WRITE))
        {
            bw.write(value);
        }
    }

    public void setBrightnessInPercent(final byte newBrightnessInPercent)
    {
        if (newBrightnessInPercent < 0 || newBrightnessInPercent > 100)
        {
            throw new IllegalArgumentException("Значення повинно бути в діапазоні від 0 до 100");
        }

        brightnessInPercent = newBrightnessInPercent;

        brightnessForBacklightConfigFile = convertPercentToUnsignedByteValue(brightnessInPercent);
    }

    public void setBrightnessMethod(final BrightnessMethod newBrightnessMethod)
    {
        brightnessMethod = newBrightnessMethod;
    }

    private void preparingToPerform() throws IOException
    {
        switch (brightnessMethod)
        {
            case XRANDR:
                createCommandForXrandrMethod();
                break;
            case BACKLIGHT_CONFIG_FILE:
                backlightBrightnessConfigFile_Path = definePathToBacklightBrightnessConfigFile();
                brightnessForBacklightConfigFile = convertPercentToUnsignedByteValue(brightnessInPercent);
                break;
        }
    }

    public boolean isBrightnessConfigWritableCurrentUser()
    {
        return Files.isWritable(backlightBrightnessConfigFile_Path);
    }
}
