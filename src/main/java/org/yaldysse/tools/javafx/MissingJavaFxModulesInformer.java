package org.yaldysse.tools.javafx;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Взаємодіє з класом {@link JavaFxModuleChecker} для того,
 * щоб виявити які з модулей JavaFX не доступні.
 * Призначений для відображення інформації про недоступність
 * модулів та рекомендації щодо розв'язання проблеми.
 */
public class MissingJavaFxModulesInformer
{
    private String uk_full;
    private String en_full;
    private Locale locale;
    private JavaFxModuleChecker javaFxModuleChecker;
    private final ArrayList<Locale> supportedLocales;

    public MissingJavaFxModulesInformer()
    {
        supportedLocales = new ArrayList<>();
        locale = Locale.getDefault();
        javaFxModuleChecker = JavaFxModuleChecker.get();

        if (javaFxModuleChecker.getMissingJavaFxModules().size() == 0)
        {
            throw new UnsupportedOperationException("Все працює коректно.");
        }

        initStrings();
        show();
    }

    public MissingJavaFxModulesInformer(final Locale aLocale, final JavaFxModuleChecker aChecker) throws IllegalArgumentException
    {
        supportedLocales = new ArrayList<>();
        locale = aLocale;
        javaFxModuleChecker = aChecker;
        initStrings();
        checkingLocaleSupport(aLocale);
        show();
    }

    private void checkingLocaleSupport(final Locale aLocale) throws IllegalArgumentException
    {
        for (Locale loc : supportedLocales)
        {
            if (loc.getLanguage().equals(aLocale.getLanguage()))
            {
                return;
            }
        }

        throw new IllegalArgumentException("Unsupported locale");
    }

    private void initStrings()
    {
        String uk_message = "Не вдалось знайти компоненти бібліотеки JavaFX. Відсутні модулі:\n";
        String uk_jdk_message = "\nРекомендуємо вам завантажити бібліотеку Full Java Runtime Environment " +
                "(JRE) версії 17 LTS або новіше від Liberica JDK з сайту bellsoft:\n" +
                "https://bell-sw.com/pages/downloads/";

        uk_full = uk_message;
        for (String module : javaFxModuleChecker.getMissingJavaFxModules())
        {
            uk_full += module + "\n";
        }
        uk_full += uk_jdk_message;
        supportedLocales.add(new Locale("uk"));

        //----------------------------------- EN
        String en_message = "JavaFX libs not found. Missing modules:\n";
        String en_jdk_message = "\n We recommend downloading the Full Java Runtime Environment (JRE) " +
                "version 17 LTS or later of the Liberica JDK from the bellsoft site:"
                + "                https://bell-sw.com/pages/downloads/";

        en_full = en_message;
        for (String module : javaFxModuleChecker.getMissingJavaFxModules())
        {
            en_full += module + "\n";
        }
        en_full += en_jdk_message;
        supportedLocales.add(new Locale("en"));
    }

    private void show()
    {
        String fullMessage = "";

        if (locale.getLanguage().equals("uk"))
        {
            fullMessage = uk_full;
        }
        else if (locale.getLanguage().equals("en"))
        {
            fullMessage = en_full;
        }

        int result = JOptionPane.showConfirmDialog(null, fullMessage,
                "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

        try
        {

            if (result == JOptionPane.OK_OPTION)
            {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                {
                    Desktop.getDesktop().browse(new URI("https://bell-sw.com/pages/downloads/"));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        System.exit(0);
    }
}
