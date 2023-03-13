package GeneralPackage;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.format.DateTimeFormatter;

public class TemplateMenuItem extends CustomMenuItem
{
    private TimerTemplate timerTemplate;
    private Label name;
    private Label info;
    private Label delete;
    private ATimerFX_gui main_obj;
    private ImageView delete_active;
    private ImageView delete_unActive;

    public TemplateMenuItem(final TimerTemplate newTemplate, ATimerFX_gui gui)
    {
        timerTemplate = newTemplate;
        main_obj = gui;
        initializeComponents();
    }

    private void initializeComponents()
    {
        name = new Label(timerTemplate.getTime().format(
                DateTimeFormatter.ISO_TIME) + "   [" + timerTemplate.getName() + "]");
        name.setTextFill(Color.DARKSLATEGRAY);
        name.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 12.0D));

        info = new Label();
        info.setTooltip(new TemplateToolTip(timerTemplate));
        TemplateToolTip tooltip = (TemplateToolTip) info.getTooltip();
        tooltip.setLanguagePropertiesFileName("Language_"
                + ATimerFX_gui.getCurrentLanguageName() + ".lang_templateTip");

        delete = new Label();
        delete.setOnMouseClicked(this::delete_Action);
        delete.setOnKeyPressed(this::delete_Action);

        delete.setOnMouseEntered(event ->
        {
            delete.setGraphic(delete_active);
        });

        delete.setOnMouseExited(event ->
        {
            delete.setGraphic(delete_unActive);
        });

        HBox body = new HBox(fxGui.rem * 0.5D);
        body.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(body, Priority.ALWAYS);

        HBox infoImage_box = new HBox(fxGui.rem * 0.4D, info, delete);
        infoImage_box.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(name, Priority.ALWAYS);
        HBox.setHgrow(infoImage_box, Priority.ALWAYS);

        body.getChildren().addAll(name, infoImage_box);
        //body.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setContent(body);

    }

    public void setName(final String newText)
    {
        name.setText(newText);
    }

    public void setInfoImageView(ImageView aImageView)
    {
        info.setGraphic(aImageView);
    }

    public void setDeleteImageView(ImageView aImageView)
    {
        delete.setGraphic(aImageView);
    }

    private void delete_Action(Event event)
    {
        main_obj.getTimerTemplates().remove(timerTemplate);
        main_obj.updateTimeTemplatesMenu();
    }

    public void setDeleteActiveImageView(ImageView aDeleteActiveIcon)
    {
        delete_active = aDeleteActiveIcon;
    }

    public void setDeleteUnActiveImageView(ImageView aDeleteUnActiveIcon)
    {
        delete_unActive = aDeleteUnActiveIcon;
        delete.setGraphic(delete_unActive);
    }

}
