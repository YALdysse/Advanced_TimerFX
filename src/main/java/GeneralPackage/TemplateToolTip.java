package GeneralPackage;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class TemplateToolTip extends Tooltip
{
    private Label templateName;
    private Label templateTime;
    private Label delayName;
    private Label delayValue;
    private Label timerTypeName;

    private TimerTemplate timerTemplate;
    private Properties language;

    public TemplateToolTip(TimerTemplate newTemplate)
    {
        timerTemplate = newTemplate;
        initialize();
        updateInfo();
    }

    private void initialize()
    {
        Scene scene = this.getScene();

        VBox vertical = new VBox(fxGui.rem * 0.8D);
        vertical.setFillWidth(true);
        final double titleNameOpacity = 0.8D;
        final double valuesOpacity = 0.95D;

        //----------------------- I рівень
        templateName = new Label();
        //templateName.setAlignment(Pos.CENTER);
        templateName.setOpacity(0.9D);
        templateName.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 16.0D));

        HBox name_hbox = new HBox(templateName);
        HBox.setHgrow(name_hbox, Priority.ALWAYS);
        name_hbox.setFillHeight(true);
        name_hbox.setAlignment(Pos.CENTER);
        name_hbox.setBorder(new Border(new BorderStroke(Color.LIGHTSTEELBLUE, BorderStrokeStyle.SOLID,
                new CornerRadii(fxGui.rem * 0.2D), BorderStroke.MEDIUM)));
        name_hbox.setPadding(new Insets(fxGui.rem * 0.15D, fxGui.rem * 0.25D,
                fxGui.rem * 0.15D, fxGui.rem * 0.25D));

        //------------------------ II рівень
        //------------------------Комірка часу
        Label templateTimeName = new Label("TIME");
        templateTimeName.setFont(Font.font(Font.getDefault().getName(), FontWeight.SEMI_BOLD, 9.0D));
        templateTimeName.setOpacity(titleNameOpacity);

        templateTime = new Label("TIME_VALUE");
        templateTime.setOpacity(valuesOpacity);
        templateTime.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 15.0D));

        timerTypeName = new Label("TYPE");
        timerTypeName.setOpacity(valuesOpacity);
        timerTypeName.setFont(templateTimeName.getFont());

        HBox templateTime_hbox = new HBox(templateTime);
        templateTime_hbox.setFillHeight(true);
        templateTime_hbox.setAlignment(Pos.CENTER);
        templateTime_hbox.setPadding(new Insets(0.0D, fxGui.rem * 0.1D, fxGui.rem * 0.2D, fxGui.rem * 0.1D));

        HBox timerType_box = new HBox(timerTypeName);
        timerType_box.setFillHeight(true);
        timerType_box.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(timerType_box, Priority.ALWAYS);
        //timerType_box.setPadding(new Insets(0.0D, fxGui.rem * 0.1D, fxGui.rem * 0.2D, fxGui.rem * 0.1D));


        //------------------------Контейнер комірки часу
        VBox timerTime_cell_box = new VBox(fxGui.rem * 0.3D);
        timerTime_cell_box.setFillWidth(true);
        timerTime_cell_box.setPadding(new Insets(fxGui.rem * 0.2D));
        timerTime_cell_box.setBorder(new Border(new BorderStroke(Color.GAINSBORO,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));

        //------------------------Назва комірки з часом
        HBox timerTimerName_up_box = new HBox(templateTimeName);
        timerTimerName_up_box.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(timerTimerName_up_box, Priority.ALWAYS);

        HBox timer_up_united_box = new HBox(fxGui.rem * 0.5D, timerType_box, timerTimerName_up_box);
        HBox.setHgrow(timer_up_united_box, Priority.ALWAYS);

        timerTime_cell_box.getChildren().addAll(timer_up_united_box, templateTime_hbox);
        //timerTime_cell_box.setBorder(name_hbox.getBorder());


        //------------------------Контейнер затримки дії
        VBox timerDelay_cell_box = new VBox(fxGui.rem * 0.3D);
        timerDelay_cell_box.setFillWidth(true);
        timerDelay_cell_box.setPadding(timerTime_cell_box.getPadding());
        timerDelay_cell_box.setBorder(timerTime_cell_box.getBorder());
        timerDelay_cell_box.widthProperty().add(timerTime_cell_box.widthProperty());


        delayName = new Label("DELAY");
        delayName.setFont(templateTimeName.getFont());
        delayName.setOpacity(titleNameOpacity);

        delayValue = new Label();
        delayValue.setFont(templateTime.getFont());
        delayValue.setOpacity(valuesOpacity);

        //------------------------Контейнер назви комірки затримки дії
        HBox timerDelayName_up_box = new HBox(delayName);
        timerDelayName_up_box.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(timerDelayName_up_box, Priority.ALWAYS);

        //------------------------Контейнер значення комірки затримки дії
        HBox timerDelay_box = new HBox(delayValue);
        timerDelay_box.setFillHeight(true);
        timerDelay_box.setAlignment(Pos.CENTER);

        timerDelay_cell_box.getChildren().addAll(timerDelayName_up_box, timerDelay_box);

        GridPane gp = new GridPane();
        gp.addRow(0, timerTime_cell_box, timerDelay_cell_box);
        gp.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(fxGui.rem * 0.1D),
                BorderStroke.MEDIUM)));
        gp.setPadding(new Insets(fxGui.rem * 0.4D));

        gp.setHgap(fxGui.rem * 0.8D);
        gp.setAlignment(Pos.CENTER);

        vertical.getChildren().addAll(name_hbox, gp);
        vertical.setBackground(Background.fill(Color.MINTCREAM));
        vertical.setMinHeight(fxGui.rem * 7.0D);
        vertical.setMinWidth(fxGui.rem * 5.0D);
        vertical.setPadding(new Insets(fxGui.rem * 0.5D));

        scene.setRoot(vertical);

        setShowDelay(Duration.millis(60));
        setShowDuration(Duration.INDEFINITE);

        System.out.println(timerTime_cell_box.getBoundsInParent().getWidth());
    }

    private void updateInfo()
    {
        templateName.setText(timerTemplate.getName());
        //delayName.setText("" + timerTemplate.hasActionDelay());
        delayValue.setText("" + timerTemplate.getActionDelayValue());

        if (!timerTemplate.hasActionDelay())
        {
            delayValue.setText("0");
        }

        if (timerTemplate.isCountdownTimer())
        {
            templateTime.setText(timerTemplate.getTime().format(DateTimeFormatter.ISO_TIME));
        } else
        {
            templateTime.setText(timerTemplate.getTime().format(DateTimeFormatter.ISO_TIME));
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
            } else
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
        delayName.setText(language.getProperty("delay_caps_str", "DELAY"));
        templateTime.setText(language.getProperty("time_caps_str", "TIME"));

        if (timerTemplate.isCountdownTimer())
        {
            templateTime.setText(timerTemplate.getTime().format(DateTimeFormatter.ISO_TIME));
            timerTypeName.setText(language.getProperty("in", "in"));
        } else
        {
            templateTime.setText(timerTemplate.getTime().format(DateTimeFormatter.ISO_TIME));
            timerTypeName.setText(language.getProperty("at", "at"));
        }
    }
}
