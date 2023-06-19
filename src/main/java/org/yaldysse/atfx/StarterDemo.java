package org.yaldysse.atfx;

import javafx.application.Application;
import org.yaldysse.tools.javafx.MissingJavaFxModulesInformer;

public class StarterDemo
{
    public static void main(String[] args)
    {
        try
        {
            new MissingJavaFxModulesInformer();
            System.exit(1);
        }
        catch (UnsupportedOperationException u)
        {
            System.out.println("JavaFX доступна.");
        }

        Application.launch(ATimerFX_gui.class);
    }
}
