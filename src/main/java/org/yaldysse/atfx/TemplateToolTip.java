package org.yaldysse.atfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

public class TemplateToolTip extends Tooltip
{
    private Label templateNameValue;
    private Label delayCellName;
    private Label delayValue;
    private Label timerTypeCellName;
    private Label actionCellName;
    private Label actionValue_Label;
    private Label timeCellName;
    private Label timeValue;
    private TimerTemplate timerTemplate;
    private Properties language;
    private VBox vertical;

    public TemplateToolTip(TimerTemplate newTemplate)
    {
        timerTemplate = newTemplate;
        language = new Properties();
        initialize();
        updateInfo();
    }

    private void initialize()
    {
        final double CELL_ITEMS_SPACING = fxGui.rem * 0.3D;
        final Insets DATA_PADDING = new Insets(fxGui.rem * 0.2D, fxGui.rem * 0.4D,
                fxGui.rem * 0.2D, fxGui.rem * 0.4D);
        final Insets CORNER_NODE_PADDING = new Insets(fxGui.rem * 0.2);
        Scene scene = this.getScene();

        vertical = new VBox(fxGui.rem * 0.8D);
        vertical.setFillWidth(true);
        final double cellNameOpacity = 0.8D;
        final double valuesOpacity = 0.95D;

        //----------------------- I рівень
        templateNameValue = new Label();
        //templateName.setAlignment(Pos.CENTER);
        templateNameValue.setOpacity(0.9D);
        templateNameValue.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 16.0D));
        templateNameValue.setWrapText(true);

        HBox name_hbox = new HBox(templateNameValue);
        HBox.setHgrow(name_hbox, Priority.ALWAYS);
        name_hbox.setFillHeight(true);
        name_hbox.setAlignment(Pos.CENTER);
        name_hbox.setBorder(new Border(new BorderStroke(Color.LIGHTSTEELBLUE, BorderStrokeStyle.SOLID,
                new CornerRadii(fxGui.rem * 0.2D), BorderStroke.MEDIUM)));
        name_hbox.setPadding(DATA_PADDING);

        //------------------------ II рівень
        //------------------------Комірка часу
        timeCellName = new Label("TIME");
        timeCellName.setFont(Font.font(Font.getDefault().getName(), FontWeight.SEMI_BOLD, 9.0D));
        timeCellName.setOpacity(cellNameOpacity);

        timeValue = new Label("TIME_VALUE");
        timeValue.setOpacity(valuesOpacity);
        timeValue.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 15.0D));

        timerTypeCellName = new Label("TYPE");
        timerTypeCellName.setOpacity(valuesOpacity);
        timerTypeCellName.setFont(timeCellName.getFont());

        HBox templateTime_hbox = new HBox(timeValue);
        templateTime_hbox.setFillHeight(true);
        templateTime_hbox.setAlignment(Pos.CENTER);
        templateTime_hbox.setPadding(name_hbox.getPadding());

        HBox timerType_box = new HBox(timerTypeCellName);
        timerType_box.setFillHeight(true);
        timerType_box.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(timerType_box, Priority.ALWAYS);
        //timerType_box.setPadding(new Insets(0.0D, fxGui.rem * 0.1D, fxGui.rem * 0.2D, fxGui.rem * 0.1D));


        //------------------------Контейнер комірки часу
        VBox timerTime_cell_box = new VBox(CELL_ITEMS_SPACING);
        timerTime_cell_box.setFillWidth(true);
        timerTime_cell_box.setPadding(new Insets(fxGui.rem * 0.2D));
        timerTime_cell_box.setBorder(new Border(new BorderStroke(Color.GAINSBORO,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));

        //------------------------Назва комірки з часом
        HBox timerTimerName_up_box = new HBox(timeCellName);
        timerTimerName_up_box.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(timerTimerName_up_box, Priority.ALWAYS);

        HBox timer_up_united_box = new HBox(fxGui.rem * 0.5D, timerType_box, timerTimerName_up_box);
        HBox.setHgrow(timer_up_united_box, Priority.ALWAYS);

        timerTime_cell_box.getChildren().addAll(timer_up_united_box, templateTime_hbox);
        //timerTime_cell_box.setBorder(name_hbox.getBorder());


        //------------------------Контейнер затримки дії
        VBox timerDelay_cell_box = new VBox(CELL_ITEMS_SPACING);
        timerDelay_cell_box.setFillWidth(true);
        timerDelay_cell_box.setPadding(timerTime_cell_box.getPadding());
        timerDelay_cell_box.setBorder(timerTime_cell_box.getBorder());
        timerDelay_cell_box.widthProperty().add(timerTime_cell_box.widthProperty());


        delayCellName = new Label("DELAY");
        delayCellName.setFont(timeCellName.getFont());
        delayCellName.setOpacity(cellNameOpacity);

        delayValue = new Label();
        delayValue.setFont(timeValue.getFont());
        delayValue.setOpacity(valuesOpacity);

        //------------------------Контейнер назви комірки затримки дії
        HBox timerDelayName_up_box = new HBox(delayCellName);
        timerDelayName_up_box.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(timerDelayName_up_box, Priority.ALWAYS);

        //------------------------Контейнер значення комірки затримки дії
        HBox timerDelay_box = new HBox(delayValue);
        timerDelay_box.setFillHeight(true);
        timerDelay_box.setAlignment(Pos.CENTER);
        timerDelay_box.setPadding(DATA_PADDING);

        timerDelay_cell_box.getChildren().addAll(timerDelayName_up_box, timerDelay_box);

        //------------------------ III рівень
        //------------------------Комірка Дія
        actionCellName = new Label("ACTION");
        actionCellName.setFont(delayCellName.getFont());
        actionCellName.setOpacity(cellNameOpacity);

        HBox actionName_Pane = new HBox(actionCellName);
        actionName_Pane.setAlignment(Pos.TOP_RIGHT);
        //actionName_Pane.setPadding(CORNER_NODE_PADDING);
        HBox.setHgrow(actionName_Pane, Priority.ALWAYS);

        //------------ Дія
        actionValue_Label = new Label("ACTION_VALUE");
        actionValue_Label.setFont(timeValue.getFont());
        actionValue_Label.setOpacity(valuesOpacity);
        actionValue_Label.setWrapText(true);

        HBox action_Pane = new HBox(actionValue_Label);
        action_Pane.setAlignment(Pos.CENTER);
        action_Pane.setPadding(DATA_PADDING);

        HBox.setHgrow(actionValue_Label,Priority.ALWAYS);
        HBox.setHgrow(action_Pane,Priority.ALWAYS);

        VBox timerAction_cell_box = new VBox(CELL_ITEMS_SPACING,
                actionName_Pane, action_Pane);
        timerAction_cell_box.setBorder(timerTime_cell_box.getBorder());
        timerAction_cell_box.setPadding(new Insets(fxGui.rem * 0.2D));


        GridPane gp = new GridPane();
        gp.addRow(0, timerTime_cell_box, timerDelay_cell_box);
        gp.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(fxGui.rem * 0.1D),
                BorderStroke.MEDIUM)));
        gp.setPadding(new Insets(fxGui.rem * 0.4D));
        gp.setHgap(fxGui.rem * 0.8D);
        gp.setAlignment(Pos.CENTER);

        vertical.getChildren().addAll(name_hbox, gp, timerAction_cell_box);
        vertical.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,
                CornerRadii.EMPTY, Insets.EMPTY)));
        vertical.setMinHeight(fxGui.rem * 7.0D);
        vertical.setMinWidth(fxGui.rem * 5.0D);
        vertical.setPadding(new Insets(fxGui.rem * 0.5D));

        scene.setRoot(vertical);

        setShowDelay(Duration.millis(60));
        setShowDuration(Duration.INDEFINITE);

        System.out.println(timerTime_cell_box.getBoundsInParent().getWidth());
    }

    public void updateInfo()
    {
        templateNameValue.setText(timerTemplate.getName());
        if(templateNameValue.getText().equals(""))
        {
            templateNameValue.setText("- - - ");
        }
        //delayName.setText("" + timerTemplate.hasActionDelay());
        delayValue.setText("" + timerTemplate.getActionDelayValue());

        if (!timerTemplate.hasActionDelay())
        {
            delayValue.setText("0");
        }

        if (timerTemplate.isCountdownTimer())
        {
            timeValue.setText(timerTemplate.getTime().format(DateTimeFormatter.ISO_TIME));
        }
        else
        {
            timeValue.setText(timerTemplate.getTime().format(DateTimeFormatter.ISO_TIME));
        }

        ArrayList<TimerAction> actions = timerTemplate.getActions();
        final String ACTION_DESCRIPTION_SUFFIX = "_ActionDescription";
        String translation;

        if (actions.contains(TimerAction.KILL_PROCESS_BY_PID))
        {
            translation = language.getProperty(TimerAction.KILL_PROCESS_BY_PID.name()
                            + ACTION_DESCRIPTION_SUFFIX,
                    "<NOT FOUND:>" + TimerAction.KILL_PROCESS_BY_PID + ACTION_DESCRIPTION_SUFFIX);
            actionValue_Label.setText(translation + " " + timerTemplate.getProcessToKill().getPid()
                    + " [" + timerTemplate.getProcessToKill().getShortCommand() + " ]");
        }
        else if (actions.contains(TimerAction.BRIGHTNESS))
        {
            translation = language.getProperty(TimerAction.BRIGHTNESS.name()
                            + ACTION_DESCRIPTION_SUFFIX,
                    "<NOT FOUND:>" + TimerAction.BRIGHTNESS + ACTION_DESCRIPTION_SUFFIX);
            actionValue_Label.setText(translation + " " + timerTemplate.getBrightness() + "%");
        }
        else if (actions.contains(TimerAction.CUSTOM_COMMAND))
        {
            System.out.println("custom");
            translation = language.getProperty(TimerAction.CUSTOM_COMMAND.name()
                            + ACTION_DESCRIPTION_SUFFIX,
                    "<NOT FOUND:>" + TimerAction.CUSTOM_COMMAND + ACTION_DESCRIPTION_SUFFIX);
            actionValue_Label.setText(translation + " " + timerTemplate.getCustomCommand());
        }
        else if (actions.size() > 0)
        {
            translation = language.getProperty(actions.get(0).name() + ACTION_DESCRIPTION_SUFFIX,
                    "<NOT_FOUND:>" + actions.get(0).name() + ACTION_DESCRIPTION_SUFFIX);
            actionValue_Label.setText(translation);
        }
    }

    public void setLanguageProperties(Properties aLanguageProperties)
    {
        language = aLanguageProperties;
        updateLocalization();
    }

    public void setLanguagePropertiesFileName(final String fileName)
    {
        try
        {
            Properties properties = new Properties();
            properties.load(new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream("/Localizations/" + fileName), StandardCharsets.UTF_8)));

            if (properties.containsKey("delay_caps_str"))
            {
                language = properties;
                updateLocalization();
            }
            else
            {
                throw new IOException("Нет перевода или файл не правильний.");
            }
        }
        catch (IOException ioException)
        {
            System.out.println("Возникла ошибка при загрузке файла-локализации подсказки шаблона.\n" + ioException);
        }
        catch (NullPointerException nullPointerException)
        {
            System.out.println("Файл с локализацией подсказки шаблона не найден.\n" + nullPointerException);
        }
    }

    private void updateLocalization()
    {
        delayCellName.setText(language.getProperty("delay_caps_str", "DELAY"));
        timeCellName.setText(language.getProperty("time_caps_str", "TIME"));
        actionCellName.setText(language.getProperty("action_caps_str", "ACTION"));

        if (timerTemplate.isCountdownTimer())
        {
            timeValue.setText(timerTemplate.getTime().format(DateTimeFormatter.ISO_TIME));
            timerTypeCellName.setText(language.getProperty("in", "in"));
        }
        else
        {
            timeValue.setText(timerTemplate.getTime().format(DateTimeFormatter.ISO_TIME));
            timerTypeCellName.setText(language.getProperty("at", "at"));
        }

        updateInfo();
    }

    public VBox getRootLayoutNode()
    {
        return vertical;
    }

    public void setTimerTemplate(final TimerTemplate aTemplate)
    {
        timerTemplate=aTemplate;
        updateInfo();
    }
}

