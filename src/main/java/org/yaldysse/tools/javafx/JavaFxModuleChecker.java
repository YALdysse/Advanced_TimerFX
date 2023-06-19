package org.yaldysse.tools.javafx;

import javafx.beans.property.SimpleBooleanProperty;
//import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.media.*;
import javafx.scene.web.PromptData;

import java.util.ArrayList;

/**
 * Перевіряє які модулі JavaFX недоступні до використання за допомогою
 * створення об'єктів, що відносяться до кожного з модулів.
 */
public class JavaFxModuleChecker
{
    public static String JAVAFX_BASE_MODULE_NAME = "javafx.base";
    public static String JAVAFX_CONTROLS_MODULE_NAME = "javafx.controls";
    public static String JAVAFX_FXML_MODULE_NAME = "javafx.fxml";
    public static String JAVAFX_GRAPHICS_MODULE_NAME = "javafx.graphics";
    public static String JAVAFX_MEDIA_MODULE_NAME = "javafx.media";
    public static String JAVAFX_SWING_MODULE_NAME = "javafx.swing";
    public static String JAVAFX_WEB_MODULE_NAME = "javafx.web";
    private final ArrayList<String> missedModules;
    private boolean available;

    private JavaFxModuleChecker()
    {
        missedModules = new ArrayList<>();
    }

    public static JavaFxModuleChecker get()
    {
        JavaFxModuleChecker javaFxChecker = new JavaFxModuleChecker();
        javaFxChecker.checkingModulesByCreatingObject();
        return javaFxChecker;
    }

    private void checkingModulesByCreatingObject()
    {
        if (!checkBase())
        {
            missedModules.add(JAVAFX_BASE_MODULE_NAME);
        }
//        if (!checkControls())
//        {
//            missedModules.add(JAVAFX_CONTROLS_MODULE_NAME);
//        }
        if (!checkFxml())
        {
            missedModules.add(JAVAFX_FXML_MODULE_NAME);
        }
        if (!checkGraphics())
        {
            missedModules.add(JAVAFX_GRAPHICS_MODULE_NAME);
        }
        if (!checkSwing())
        {
            missedModules.add(JAVAFX_SWING_MODULE_NAME);
        }
        if (!checkMedia())
        {
            missedModules.add(JAVAFX_MEDIA_MODULE_NAME);
        }
        if (!checkWeb())
        {
            missedModules.add(JAVAFX_WEB_MODULE_NAME);
        }

        if (missedModules.size() == 0)
        {
            available = true;
        }
    }

    private boolean checkBase()
    {
        try
        {
            //javafx.base
            SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty();
        }
        catch (NoClassDefFoundError n)
        {
            return false;
        }
        return true;
    }

    private boolean checkControls()
    {
        try
        {
            //javafx.controls
            CheckBox checkBox = new CheckBox();
        }
        catch (NoClassDefFoundError n)
        {
            return false;
        }
        return true;
    }

    private boolean checkFxml()
    {
        try
        {
            //javafx.fxml
            FXMLLoader fxmlLoader = new FXMLLoader();
        }
        catch (NoClassDefFoundError n)
        {
            return false;
        }
        return true;
    }

    private boolean checkGraphics()
    {
        try
        {
            //javafx.graphics
            HBox hBox = new HBox();
        }
        catch (NoClassDefFoundError n)
        {
            return false;
        }
        return true;
    }

    private boolean checkMedia()
    {
        try
        {
            //javafx.media
            MediaView mediaView = new MediaView();
        }
        catch (NoClassDefFoundError n)
        {
            return false;
        }
        return true;
    }

    private boolean checkSwing()
    {
        try
        {
            //javafx.swing
            //JFXPanel jfxPanel = new JFXPanel();
        }
        catch (NoClassDefFoundError n)
        {
            return false;
        }
        return true;
    }

    private boolean checkWeb()
    {
        try
        {
            //javafx.web
            new PromptData("s", "f");
            //HTMLEditor htmlEditor = new HTMLEditor();
        }
        catch (NoClassDefFoundError n)
        {
            return false;
        }
        return true;
    }

    public ArrayList<String> getMissingJavaFxModules()
    {
        return missedModules;
    }

    public boolean isJavaFxLibraryAvailable()
    {
        return available;
    }
}

