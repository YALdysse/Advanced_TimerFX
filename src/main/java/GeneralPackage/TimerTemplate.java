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

package GeneralPackage;

import javafx.scene.control.RadioButton;

import java.io.Serializable;
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
    private ArrayList<RadioButton> actions;

    public TimerTemplate(final String newName, LocalTime newTime,
                         final boolean countdown, final boolean newActionDelay,
                         final int newActionDelayValue)
    {
        name = newName;
        time = newTime;
        countdownTimer = countdown;
        actionDelay = newActionDelay;
        actionDelayValue = newActionDelayValue;
        actions = new ArrayList<>();
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

    public ArrayList<RadioButton> getActions()
    {
        return actions;
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

    public void setActions(ArrayList<RadioButton> actions)
    {
        this.actions = actions;
    }
}
