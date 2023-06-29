package org.yaldysse.atfx.action.windows;

import org.yaldysse.atfx.process.ProcessUtils;
import org.yaldysse.atfx.action.Action;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Реалізовує операцію зміни яскравості екрану за допомогою WMIC для
 * ОС Windows.
 * <br>
 * <code>WMIC /NAMESPACE:\\root\wmi PATH WmiMonitorBrightnessMethods WHERE "Active=TRUE" CALL WmiSetBrightness Brightness=XXX Timeout=0
 * </code>
 */
public class Brightness implements Action
{
    private ArrayList<String> command;
    private byte brightnessInPercent;


    public Brightness(final byte aBrightnessInPercent)
    {
        if (aBrightnessInPercent < 1 || aBrightnessInPercent > 100)
        {
            throw new IllegalArgumentException("Значення повинно знаходитись в межах від 1 до 100");
        }
        brightnessInPercent = aBrightnessInPercent;

        command = new ArrayList<>();
        command.add("PowerShell");
        command.add("(Get-WmiObject -Namespace root/WMI -Class WmiMonitorBrightnessMethods).WmiSetBrightness(1,"
                + aBrightnessInPercent + ")");
//        command.add("WMIC");
//        command.add("/NAMESPACE:\\\\root\\wmi");
//        command.add("PATH");
//        command.add("WmiMonitorBrightnessMethods");
//        command.add("WHERE");
//        command.add("\"Active=TRUE\"");
//        command.add("CALL");
//        command.add("WmiSetBrightness");
//        command.add("Brightness=" + brightnessInPercent);
//        command.add("Timeout=0");
    }

    @Override
    public void perform() throws IOException
    {
        ProcessUtils.createSimpleAndRedirectOeToBase(command).start();
    }
}
