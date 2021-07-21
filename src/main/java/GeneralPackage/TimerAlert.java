/*
 * Copyright (C) Yaroslav Lytvynov (aka YALdysse) 2021 <Yaroslav_A_Litvinov@yahoo.com>
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

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.time.LocalTime;

public class TimerAlert extends Stage
{
    private static final double rem = new Text("").getBoundsInParent().getHeight();
    private static final int PREFERRED_WIDTH = (int)(rem * 22.0D);
    private static final int PREFERRED_HEIGHT = 300;
    private VBox root;

    private Label confirm_Label = null;
    private Button yes_Button;
    private Button no_Button;

    private Button resultButton;
    private Label warning_Label;
    private Label timeLeft_Label;

    private Image img;
    private Timeline timeLineBeforeAction;
    private LocalTime leaveTime_LocalTime;

    public final double LEFT_TIME = 15.0D;

    public void start(final String aHeaderText, final String aMessage)
    {
        confirm_Label = new Label(aMessage);

        HBox warning_HBox = new HBox();

        warning_Label = new Label(aHeaderText);

        try
        {
            warning_Label.setFont(YALtools.createFontFXFromResources("Fonts/7fonts.ru_Stamper_RG.ttf", 22.0D));
        }
        catch (IOException ioException)
        {

        }

        timeLeft_Label = new Label();

        try
        {
            img = new javafx.scene.image.Image(this.getClass().getClassLoader().getResource("Images/warning.png").openStream());
        }
        catch (IOException ioExc)
        {

        }

        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(32.0D);
        imgView.setFitHeight(32.0D);

        warning_HBox.getChildren().add(warning_Label);
        warning_HBox.getChildren().add(timeLeft_Label);
        warning_HBox.getChildren().add(imgView);
        warning_HBox.setAlignment(Pos.CENTER_RIGHT);
        warning_HBox.setSpacing(15.0D);

        root = new VBox(20, warning_HBox, new Separator(), confirm_Label);
        root.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        root.setPadding(new Insets(15.0D));

        yes_Button = new Button("Yes");
        yes_Button.setOnAction(event ->
        {
            resultButton = yes_Button;
            hide();
        });

        no_Button = new Button("No");
        no_Button.setOnAction(event ->
        {
            resultButton = no_Button;
            hide();
        });

        HBox buttons_Box = new HBox(10, yes_Button, no_Button);
        buttons_Box.setAlignment(Pos.CENTER_RIGHT);
        root.getChildren().add(buttons_Box);

        timeLineBeforeAction = new Timeline(new KeyFrame(Duration.seconds(LEFT_TIME), event ->
        {
            YALtools.printDebugMessage("Ожидание действия закончилось. Выполняем действие таймера...");
            resultButton = yes_Button;
            hide();
        }));
        leaveTime_LocalTime = LocalTime.of(0, 0, (int) LEFT_TIME);
        timeLineBeforeAction.play();

        //====== Описываем таймер для отсчета времени
        final boolean[] changeColor = {false};
        Timeline appearance_TimeLine = new Timeline(new KeyFrame(Duration.seconds(1), event ->
        {
            leaveTime_LocalTime = leaveTime_LocalTime.minusSeconds(1);
            timeLeft_Label.setText(leaveTime_LocalTime.toString());


//            if (changeColor[0])
//            {
//                warning_Label.setTextFill(Color.RED);
//                changeColor[0] = false;
//            } else
//            {
//                warning_Label.setTextFill(Color.BLACK);
//                changeColor[0] = true;
//            }
        }));
        appearance_TimeLine.setCycleCount((int) LEFT_TIME);
        appearance_TimeLine.play();

        Timeline changingWarningColor_TimeLine = new Timeline(new KeyFrame(Duration.millis(800), event ->
        {

            if (changeColor[0])
            {
                warning_Label.setTextFill(Color.RED);
                changeColor[0] = false;
            } else
            {
                warning_Label.setTextFill(Color.BLACK);
                changeColor[0] = true;
            }
        }));
        changingWarningColor_TimeLine.setCycleCount((int) (LEFT_TIME * 1000) / 800);
        changingWarningColor_TimeLine.play();
        //===================================================

        this.setOnCloseRequest(event ->
        {
            YALtools.printDebugMessage("Попытка закрыть окно через оконный менеджер.");
            resultButton = yes_Button;
            hide();
        });

        YALtools.printDebugMessage("" + root.getBoundsInParent().getHeight());

        this.setScene(new Scene(root));
        this.setTitle("Timer Alert");
        //this.initModality(Modality.APPLICATION_MODAL);
        this.show();
    }

    public Button getResultButton()
    {
        return resultButton;
    }

}
