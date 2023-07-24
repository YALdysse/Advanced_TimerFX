/*
 * Copyright (C) Yaroslav Lytvynov (aka YALdysse) 2021-2023 <Yaroslav_A_Litvinov@yahoo.com>
 *
 * Advanced TimerFX is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Advanced TimerFX is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @license GPL-3.0+ <https://spdx.org/licenses/GPL-3.0+.html>
 */

package org.yaldysse.atfx;

import org.yaldysse.atfx.action.Action;
import org.yaldysse.atfx.process.Process;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Описывает поведение шаблона настроек таймера: времени,
 * названия и команд.
 */
public class TimerTemplate implements Serializable
{
    private String name;
    private LocalTime time;
    private boolean countdownTimer;
    private boolean actionDelay;
    private int actionDelayValue;
    private byte brightness;
    private Process processToKill;
    private String customCommand_str;
    private Action customCommandAction;
    private ArrayList<TimerAction> timerActionTypes;
    //private Sound notificationSound;
    private long soundSize;
    private String soundPath;
    private String soundName;

    public TimerTemplate(final String newName, LocalTime newTime,
                         final boolean countdown, final boolean newActionDelay,
                         final int newActionDelayValue, final Sound newSound)
    {
        name = newName;
        time = newTime;
        countdownTimer = countdown;
        actionDelay = newActionDelay;
        actionDelayValue = newActionDelayValue;
        timerActionTypes = new ArrayList<>();
        soundName = newSound.getName();
        soundPath = newSound.getPath();
        soundSize = newSound.getSize();
    }

    public boolean hasActionDelay()
    {
        return actionDelay;
    }

    public int getActionDelayValue()
    {
        return actionDelayValue;
    }

    public String getName()
    {
        return name;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public ArrayList<TimerAction> getActions()
    {
        return timerActionTypes;
    }

    public boolean isCountdownTimer()
    {
        return countdownTimer;
    }

    public void setName(String name)
    {
        if (name == null)
        {
            throw new NullPointerException();
        }
        this.name = name;
    }

    public void setTime(LocalTime time)
    {
        if (time == null)
        {
            throw new NullPointerException();
        }
        this.time = time;
    }

    public void setCountdownTimer(boolean countdownTimer)
    {
        this.countdownTimer = countdownTimer;
    }

    public void setActionDelay(boolean actionDelay)
    {
        this.actionDelay = actionDelay;
    }

    public void setActionDelayValue(int actionDelayValue)
    {
        if (actionDelayValue > 60 || actionDelayValue < 1)
        {
            throw new IllegalArgumentException("Action delay must be greeter than 0 and" +
                    "less than 60");
        }
        this.actionDelayValue = actionDelayValue;
    }

    public void setActions(ArrayList<TimerAction> actions)
    {
        timerActionTypes = actions;
    }

    public void setCustomCommandAction(final Action action)
    {
        customCommandAction = action;
    }

    public Action getCustomCommandAction()
    {
        return customCommandAction;
    }

    public static void exportTimerTemplatesToFile(final Path targetPath, final
    ArrayList<TimerTemplate> timerTemplates)
            throws IOException
    {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(targetPath.toFile()))))
        {
            for (TimerTemplate template : timerTemplates)
            {
                objectOutputStream.writeUTF("---=== D!ssect!0n ===---");
                objectOutputStream.writeObject(template);
                objectOutputStream.writeUTF("---=== D!ssect!0n ===---");
            }
        }
    }

    public static ArrayList<TimerTemplate> importTimerTemplatesFromFile(final Path targetPath)
            throws IOException
    {
        System.out.println("ImportPath: " + targetPath.toAbsolutePath().toString());
        ArrayList<TimerTemplate> timerTemplates = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(
                new FileInputStream(targetPath.toFile()))))
        {
            while (objectInputStream.available() > 0)
            {
                objectInputStream.readUTF();
                timerTemplates.add((TimerTemplate) objectInputStream.readObject());
                System.out.println("Зчитаний розподілювач об`єктів. " + objectInputStream.readUTF());
            }
        }
        catch (ClassNotFoundException ioException)
        {
            ioException.printStackTrace();
        }
        return timerTemplates;
    }

    public void setBrightness(final byte aBrightness)
    {
        brightness = aBrightness;
    }

    public void setProcessToKill(final Process aProcessToKill)
    {
        processToKill = aProcessToKill;
    }

    public void setCustomCommand_str(final String aCustomCommand)
    {
        customCommand_str = aCustomCommand;
    }

    public String getCustomCommand()
    {
        return customCommand_str;
    }

    public Process getProcessToKill()
    {
        return processToKill;
    }

    public byte getBrightness()
    {
        return brightness;
    }

//    public Sound getNotificationSound()
//    {
//        return notificationSound;
//    }
//
//    public void setNotificationSound(final Sound notificationSound)
//    {
//        this.notificationSound = notificationSound;
//    }


    public long getSoundSize()
    {
        return soundSize;
    }

    public String getSoundPath()
    {
        return soundPath;
    }

    public String getSoundName()
    {
        return soundName;
    }

    public void setSoundSize(long soundSize)
    {
        this.soundSize = soundSize;
    }

    public void setSoundPath(String soundPath)
    {
        this.soundPath = soundPath;
    }

    public void setSoundName(String soundName)
    {
        this.soundName = soundName;
    }
}
