package org.yaldysse.atfx;

import javafx.scene.control.RadioButton;

public class ActionRadioButton extends RadioButton
{
    private TimerAction action;

    public ActionRadioButton(final TimerAction aAction)
    {
        action = aAction;
    }

    public TimerAction getAction()
    {
        return action;
    }
}
