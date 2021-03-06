/*
 * Copyright (C) Yaroslav Lytvynov (aka YALdysse) 2021-2022 <Yaroslav_A_Litvinov@yahoo.com>
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
import javafx.application.HostServices;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.yaldysse.tools.notification.AnimationType;
import org.yaldysse.tools.notification.Notification;
import org.yaldysse.tools.notification.NotificationType;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;


public class ATimerFX_gui extends Application
{
    private TextField timerName_textFiels;

    private Spinner hours_Spinner;
    private Spinner minutes_Spinner;
    private Spinner seconds_Spinner;

    private Text hours_text;
    private Text minutes_text;
    private Text seconds_text;
    private Label timerType_Label;

    private Button startTimer_button;
    private Button stopTimer_button;

    private CheckBox performActionAfterTimerWentOut_checkBox;

    private RadioButton shutdown_radio;
    private RadioButton suspend_radio;
    private RadioButton reboot_radio;
    private RadioButton custom_command;
    private RadioButton killProcess_RadioButton;
    private RadioButton logOut_RadioButton;
    private VBox radioGroup_Box;
    private TextField customCommand_textField;
    private TextField processID_TextField;
    private Timeline timer_obj;
    private VBox root;
    private Pane timerIsRunning_pane;
    private Label hoursAppearance_Label;
    private Label minutesAppearance_Label;
    private Label secondsAppearance_Label;
    private Label timerName_Label;
    private Label timerAction_Label;
    private MenuBar menuBar;
    private Menu general_menu;
    private Menu language_Menu;
    private Menu timerType_Menu;
    private RadioMenuItem countdownTimer_MenuItem;
    private RadioMenuItem specifiedTimeTimer_MenuItem;
    private MenuItem russianLanguage_MenuItem;
    private MenuItem ukrainianLanguage_MenuItem;
    private MenuItem englishLanguage_MenuItem;
    private MenuItem gitHubRepository_MenuItem;
    private Menu timer_Menu;
    private MenuItem startTimer_MenuItem;
    private MenuItem stopTimer_MenuItem;
    private MenuItem exit_menuItem;
    //private CheckMenuItem delayBeforeAction;
    private CheckBox delayBeforeAction_CheckBox;
    private BorderPane menu_BorderPane;
    private Spinner delayBeforeAction_Spinner;
    private Label delayCustomMenuItem_Label;
    private CustomMenuItem delay_CustomMenuItem;

    private static final double rem = new Text("").getBoundsInParent().getHeight();
    private final Label colon_Label = new Label(":");
    private final Label colon_Label2 = new Label(":");
    private int timerTimeInSeconds;
    private boolean action = false;
    private String actionDescription = null;

    //---------------- Pane
    private VBox superRoot;
    private HBox killProcess_HBox;
    private HBox delayCustomMenuItem_HBox;

    private LocalTime timerTime_LocalTime;
    Timeline timeline = null;

    private Scene scene;
    private Stage stage;

    private Text textToDefineWidth;
    public static final String NAME_OF_PROGRAM = "Advanced TimerFX";
    private String timerName_str;
    private String action_str;
    private String info_str;
    private String customCommandPromtText_str;
    private String shutdownActionDescription_str;
    private String suspendActionDescription_str;
    private String rebootActionDescription_str;
    private String killProcessActionDescription_str;
    private String processName_str;
    private String processNotFound_str;
    private String process_str;
    private String notFound_str;
    private String doYouWantToContinue_str;
    private String warning_str;
    private String logOutActionDescription_str;
    private String popup_str;
    private String theTimerHasExpired_str;
    private String inTimerType_str;
    private String atTimerType_str;
    private String startButton_str;
    private String notify_str;

    private Label actionInfo_Label;
    private Label actionValue_Label;
    private int locX = 0;
    private int locY = 0;
    private String currentLanguage_str = "English";
    private GridPane gp;

    private static final int PREFERRED_WIDTH = (int) (rem * 19.2D);
    private static final int PREFERRED_HEIGHT = (int) (rem * 15.6D);

    private boolean processHaveChildren = false;

    private Popup popup;
    private Notification notify;

    private Thread initializingTimerIsRunningComponent_Thread;

    public void start(Stage aStage)
    {
        long startTime = System.currentTimeMillis();

        System.out.println(System.getProperty("os.name"));

        //?????????? ?????? ?????????????????????????? ????????
        Thread initializeMenu_Thread = new Thread(() ->
        {
            initializeMenu();
        }, "Initializing Menu");
        initializeMenu_Thread.start();

        initComponents();

        stage = aStage;
        scene = new Scene(superRoot);
        scene.setOnMouseClicked(eventClicked ->
        {
            timer_Menu.setOnHidden(null);
            timer_Menu.hide();
        });

        stage.getIcons().add(applicationIcon_Image);
        stage.setOnCloseRequest(event ->
        {
            saveSettings();
        });

        importSettings();

        /*?????????????????????????? ???????????? ?? ???????????????????????? ?????????????? ???????????? scene.getWindow().sizeToScene();
         * ???????????????? ?? ?????????????????? ????????????????????. ???????????????????????? ???????????????????? ??????????????????????
         * ???? ?????????????????????? ???????????? ?????????????????? ???????? ?????????? ?????????? ??????????????, ?????????? ???????????????? ????????
         * ?????? ????????????, ?????? ?????????? ???? ?????????????????????? ???? stage.*/
        stage.setMinWidth(PREFERRED_WIDTH - 2);
        stage.setMinHeight(PREFERRED_HEIGHT - 2);
        //stage.setWidth(PREFERRED_WIDTH);
        stage.setX(locX);
        stage.setY(locY);

        stage.setScene(scene);
        stage.setTitle(NAME_OF_PROGRAM + " [build 35 Stable]");

        menu_BorderPane.setTop(menuBar);

        if (initializeMenu_Thread.isAlive())
        {
            try
            {
                initializeMenu_Thread.join();
            }
            catch (InterruptedException interruptedException)
            {
                interruptedException.printStackTrace();
            }
        }
        initLocalization(currentLanguage_str);

        scene.getWindow().sizeToScene();

        stage.show();
        System.out.println("?????????????? ????????????: " + (System.currentTimeMillis() - startTime));

        initializingTimerIsRunningComponent_Thread = new Thread(() ->
        {
            initializeTimerIsRunningComponents();
        });
        initializingTimerIsRunningComponent_Thread.start();
    }

    /**
     * ???????????????????? ???????????????? ?????????????????? ????????: ?????????????????? ??????????, ????????????, ???????????????????? ????????????????????????.
     * ?????????????????? ???????????????????? ??????????.
     */
    private void initializeMenu()
    {
        System.out.println(Thread.currentThread().getName());
        //--------------------------------- ????????
        menuBar = new MenuBar();
        general_menu = new Menu("General");
        language_Menu = new Menu("Language");
        timer_Menu = new Menu("Timer");
        timerType_Menu = new Menu("Timer type");

        //?????????????????? ?????????? ??????????
        try
        {
            russianFlag_Image = new Image(this.getClass().getClassLoader().getResource("Images/russia.png").openStream());
            ukraineFlag_Image = new Image(this.getClass().getClassLoader().getResource("Images/ukraine_2.png").openStream());
            unitedKingdom_Image = new Image(this.getClass().getClassLoader().getResource("Images/UK.png").openStream());
            exit_Image = new Image(this.getClass().getClassLoader().getResource("Images/exit.png").openStream());
            gitHub_Image = new Image(this.getClass().getClassLoader().getResource("Images/gitHub.png").openStream());
            language_Image = new Image(this.getClass().getClassLoader().getResource("Images/language_2.png").openStream());
            startTimer_Image = new Image(this.getClass().getClassLoader().getResource("Images/startTimer_3.png").openStream());
            stopTimer_Image = new Image(this.getClass().getClassLoader().getResource("Images/stopTimer_3.png").openStream());
            delay_Image = new Image(this.getClass().getClassLoader().getResource("Images/delay_1.png").openStream());
            timerType_Image = new Image(this.getClass().getClassLoader().getResource("Images/timerType.png").openStream());

        }
        catch (IOException ioExc)
        {
            ioExc.printStackTrace();
        }

        final double ICON_SIZE = 24.0D;

        ImageView russianFlag_ImageView = new ImageView(russianFlag_Image);
        russianFlag_ImageView.setPreserveRatio(true);
        //russianFlag_ImageView.setFitHeight(24);
        russianFlag_ImageView.setFitWidth(ICON_SIZE);

        ImageView ukraineFlag_ImageView = new ImageView(ukraineFlag_Image);
        ukraineFlag_ImageView.setPreserveRatio(true);
        ukraineFlag_ImageView.setFitWidth(ICON_SIZE);

        ImageView unitedKingdom_ImageView = new ImageView(unitedKingdom_Image);
        unitedKingdom_ImageView.setPreserveRatio(true);
        unitedKingdom_ImageView.setFitWidth(ICON_SIZE);

        ImageView exit_ImageView = new ImageView(exit_Image);
        exit_ImageView.setPreserveRatio(true);
        exit_ImageView.setFitWidth(ICON_SIZE);

        ImageView gitHub_ImageView = new ImageView(gitHub_Image);
        gitHub_ImageView.setPreserveRatio(true);
        gitHub_ImageView.setFitWidth(ICON_SIZE);

        ImageView language_ImageView = new ImageView(language_Image);
        language_ImageView.setPreserveRatio(true);
        language_ImageView.setFitWidth(ICON_SIZE);

        ImageView startTimer_ImageView = new ImageView(startTimer_Image);
        startTimer_ImageView.setPreserveRatio(true);
        startTimer_ImageView.setFitWidth(ICON_SIZE);

        ImageView stopTimer_ImageView = new ImageView(stopTimer_Image);
        stopTimer_ImageView.setPreserveRatio(true);
        stopTimer_ImageView.setFitWidth(ICON_SIZE);

        ImageView delay_ImageView = new ImageView(delay_Image);
        delay_ImageView.setPreserveRatio(true);
        delay_ImageView.setFitWidth(ICON_SIZE);

        ImageView timerType_ImageView = new ImageView(timerType_Image);
        timerType_ImageView.setPreserveRatio(true);
        timerType_ImageView.setFitWidth(ICON_SIZE);

        startTimer_MenuItem = new MenuItem("Start Timer", startTimer_ImageView);
        startTimer_MenuItem.setOnAction(event ->
        {
            startTimer_Action(null);
        });
        startTimer_MenuItem.acceleratorProperty().set(new KeyCodeCombination(KeyCode.ENTER, KeyCodeCombination.CONTROL_DOWN));

        stopTimer_MenuItem = new MenuItem("Stop Timer", stopTimer_ImageView);
        stopTimer_MenuItem.setOnAction(event ->
        {
            stopTimerButton_Action(null);
        });
        stopTimer_MenuItem.acceleratorProperty().set(new KeyCodeCombination(KeyCode.SPACE, KeyCodeCombination.CONTROL_DOWN));
        stopTimer_MenuItem.setDisable(true);

        //?????????? ?????? ????????, ?????????? ???????????????????????? ???????????????????? ?? ?????????? ???????????? ???????? ??????????
        //???????????????? ?????? CheckBox, Spinner ?? Label
        delayCustomMenuItem_HBox = new HBox(rem * 0.5D);

        delay_CustomMenuItem = new CustomMenuItem(delayCustomMenuItem_HBox);
        //delay_CustomMenuItem.setGraphic(delay_ImageView);

        delayBeforeAction_CheckBox = new CheckBox("Delay before command execution");
        delayBeforeAction_CheckBox.setTextFill(Color.BLACK);
        delayBeforeAction_CheckBox.setGraphic(delay_ImageView);
        delayBeforeAction_CheckBox.setSelected(true);
        delayBeforeAction_CheckBox.setOnAction(event ->
        {
            if (delayBeforeAction_CheckBox.isSelected())
            {
                YALtools.printDebugMessage("???????????????? ????????????????");
                delayCustomMenuItem_HBox.getChildren().add(delayBeforeAction_Spinner);
            }
            else
            {
                delayCustomMenuItem_HBox.getChildren().remove(delayBeforeAction_Spinner);
            }
            saveSettings();
        });

        delayBeforeAction_Spinner = new Spinner(1, 59, 15);
        delayBeforeAction_Spinner.setOnScroll(event ->
        {
            //timer_Menu.setOnHidden(null);
            int increment = 1;

            YALtools.printDebugMessage("getDeltaY: " + event.getDeltaY());

            if (event.getDeltaY() > 10)
            {
                delayBeforeAction_Spinner.getValueFactory().setValue((int) delayBeforeAction_Spinner.getValue() + increment);
            }
            else if (event.getDeltaY() < 0)
            {
                delayBeforeAction_Spinner.getValueFactory().setValue((int) delayBeforeAction_Spinner.getValue() - increment);
            }
        });
//        delayBeforeAction_Spinner.getValueFactory().valueProperty().addListener(event ->
//        {
//            timer_Menu.setOnHidden(null);
//        });
        delayBeforeAction_Spinner.setMinWidth(rem * 1.2D);
        delayBeforeAction_Spinner.setPrefWidth(rem * 4.0D);

        delayCustomMenuItem_HBox.getChildren().addAll(delayBeforeAction_CheckBox, delayBeforeAction_Spinner);

        exit_menuItem = new MenuItem("Exit", exit_ImageView);
        exit_menuItem.acceleratorProperty().set(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        exit_menuItem.setOnAction(event ->
        {
            YALtools.printDebugMessage("???????????????????? ???????????? ????????????????????.");
            saveSettings();
            System.exit(0);
        });

        gitHubRepository_MenuItem = new MenuItem("Repository on GitHub", gitHub_ImageView);
        gitHubRepository_MenuItem.setOnAction(event ->
        {
            getHostServices().showDocument("https://github.com/YALdysse/Advanced_TimerFX");
        });

        russianLanguage_MenuItem = new MenuItem("??????????????", russianFlag_ImageView);
        russianLanguage_MenuItem.setOnAction(event ->
        {
            initLocalization("Russian");
        });
        ukrainianLanguage_MenuItem = new MenuItem("????????????????????", ukraineFlag_ImageView);
        ukrainianLanguage_MenuItem.setOnAction(event ->
        {
            initLocalization("Ukrainian");
        });

        englishLanguage_MenuItem = new MenuItem("English", unitedKingdom_ImageView);
        englishLanguage_MenuItem.setOnAction(event ->
        {
            initLocalization("English");
        });

        specifiedTimeTimer_MenuItem = new RadioMenuItem();
        specifiedTimeTimer_MenuItem.setOnAction(event ->
        {
            countdownTimer_MenuItem.setSelected(false);
            timerType_Label.setText(atTimerType_str);
            startTimer_button.setText(notify_str + " " + atTimerType_str + " (" + startButton_str + ")");
            LocalTime time = LocalTime.now();
            hours_Spinner.getValueFactory().setValue(time.getHour());
            minutes_Spinner.getValueFactory().setValue(time.getMinute() + 1);
            seconds_Spinner.getValueFactory().setValue(time.getSecond());
        });

        countdownTimer_MenuItem = new RadioMenuItem();
        countdownTimer_MenuItem.setSelected(true);
        countdownTimer_MenuItem.setOnAction(event ->
        {
            specifiedTimeTimer_MenuItem.setSelected(false);
            timerType_Label.setText(inTimerType_str);
            startTimer_button.setText(notify_str + " " + inTimerType_str + " (" + startButton_str + ")");
        });

        language_Menu.setGraphic(language_ImageView);
        //timer_Menu.setGraphic(language_ImageView);
        timerType_Menu.setGraphic(timerType_ImageView);
        timerType_Menu.getItems().addAll(countdownTimer_MenuItem, specifiedTimeTimer_MenuItem);
        language_Menu.getItems().addAll(russianLanguage_MenuItem, ukrainianLanguage_MenuItem, englishLanguage_MenuItem);
        general_menu.getItems().addAll(language_Menu, gitHubRepository_MenuItem, exit_menuItem);
        timer_Menu.getItems().addAll(timerType_Menu, new SeparatorMenuItem(), startTimer_MenuItem, stopTimer_MenuItem, new SeparatorMenuItem(), delay_CustomMenuItem);

        menuBar.getMenus().addAll(general_menu, timer_Menu);
    }

    private Image russianFlag_Image;
    private Image ukraineFlag_Image;
    private Image unitedKingdom_Image;
    private Image exit_Image;
    private Image language_Image;
    private Image gitHub_Image;
    private Image startTimer_Image;
    private Image stopTimer_Image;
    private Image delay_Image;
    private Image applicationIcon_Image;
    private Image timerType_Image;

    private void initComponents()
    {
        YALtools.printDebugMessage("rem: " + rem);

        timerName_textFiels = new TextField();
        timerName_textFiels.setPromptText("Name of timer (optional)");
        //timerName_textFiels.setPrefWidth(5000.D);

        HBox timerName_Box = new HBox(rem * 0.1, timerName_textFiels);
        timerName_Box.setAlignment(Pos.CENTER);
        HBox.setHgrow(timerName_textFiels, Priority.ALWAYS);

        timerType_Label = new Label();
        timerType_Label.setFont(Font.font(timerType_Label.getFont().getName(), FontPosture.ITALIC,
                timerType_Label.getFont().getSize() - 2.0D));
        HBox timerType_HBox = new HBox(timerType_Label);
        timerType_HBox.setAlignment(Pos.CENTER);

        hours_Spinner = new Spinner(-1, 23, 0);
        //hours_Spinner.setMaxWidth(10);
        hours_Spinner.setMaxWidth(70);
        hours_Spinner.setOnScroll(event ->
        {
            int increment = 1;

            YALtools.printDebugMessage("getDeltaY: " + event.getDeltaY());

            if (event.getDeltaY() > 10)
            {
                hours_Spinner.getValueFactory().setValue((int) hours_Spinner.getValue() + increment);
            }
            else if (event.getDeltaY() < 0)
            {
                hours_Spinner.getValueFactory().setValue((int) hours_Spinner.getValue() - increment);
            }
        });
        hours_Spinner.valueProperty().addListener(event ->
        {
            if ((int) hours_Spinner.getValue() < 0)
            {
                hours_Spinner.getValueFactory().setValue(23);
            }
            if ((int) hours_Spinner.getValue() > 23)
            {
                hours_Spinner.getValueFactory().setValue(0);
            }
        });


        minutes_Spinner = new Spinner(-1, 60, 0);
        minutes_Spinner.setMaxWidth(70);
        minutes_Spinner.setOnScroll(event ->
        {
            int increment = 1;

            YALtools.printDebugMessage("getDeltaY: " + event.getDeltaY());

            if (event.getDeltaY() > 10)
            {
                minutes_Spinner.getValueFactory().setValue((int) minutes_Spinner.getValue() + increment);
            }
            else if (event.getDeltaY() < 0)
            {
                minutes_Spinner.getValueFactory().setValue((int) minutes_Spinner.getValue() - increment);
            }


        });
        minutes_Spinner.valueProperty().addListener(event ->
        {
            if ((int) minutes_Spinner.getValue() >= 60)
            {
                hours_Spinner.increment();
                minutes_Spinner.getValueFactory().setValue(0);
            }

            if ((int) minutes_Spinner.getValue() < 0)
            {
                if ((int) hours_Spinner.getValue() != 0)
                {
                    hours_Spinner.decrement();
                }
                minutes_Spinner.getValueFactory().setValue(59);
            }
        });

        seconds_Spinner = new Spinner(-1, 60, 1);
        seconds_Spinner.setMaxWidth(70);
        seconds_Spinner.setOnScroll(event ->
        {
            int increment = 1;

            YALtools.printDebugMessage("getDeltaY: " + event.getDeltaY());

            if (event.getDeltaY() > 10)
            {
                seconds_Spinner.getValueFactory().setValue((int) seconds_Spinner.getValue() + increment);
            }
            else if (event.getDeltaY() < 0)
            {
                seconds_Spinner.getValueFactory().setValue((int) seconds_Spinner.getValue() - increment);
            }
        });
        seconds_Spinner.getValueFactory().valueProperty().addListener(event ->
        {
            if ((int) seconds_Spinner.getValue() >= 60)
            {
                minutes_Spinner.increment();
                seconds_Spinner.getValueFactory().setValue(0);
            }
            if ((int) seconds_Spinner.getValue() < 0)
            {
                if ((int) minutes_Spinner.getValue() != 0)
                {
                    minutes_Spinner.decrement();
                }
                else if ((int) hours_Spinner.getValue() != 0
                        && (int) minutes_Spinner.getValue() == 0)
                {
                    hours_Spinner.decrement();
                    minutes_Spinner.getValueFactory().setValue(59);
                }
                seconds_Spinner.getValueFactory().setValue(59);
            }
        });

        final double TIME_TEXT_OPACITY = 0.55D;
        hours_text = new Text();
        hours_text.setOpacity(TIME_TEXT_OPACITY);
        minutes_text = new Text();
        minutes_text.setOpacity(TIME_TEXT_OPACITY);
        seconds_text = new Text();
        seconds_text.setOpacity(TIME_TEXT_OPACITY);

        gp = new GridPane();
        //gp.add(timerType_Label,0,0);
        gp.add(hours_Spinner, 0, 0);
        gp.add(minutes_Spinner, 1, 0);
        gp.add(seconds_Spinner, 2, 0);
        //gp.add(new Text(), 0, 1);
        gp.add(hours_text, 0, 1);
        gp.add(minutes_text, 1, 1);
        gp.add(seconds_text, 2, 1);
        gp.setAlignment(Pos.CENTER);
        //gp.setGridLin
        // esVisible(true);
        gp.setHgap(rem * 1.1);

        try
        {
            applicationIcon_Image = new Image(this.getClass().getClassLoader().getResource("Images/timer_1.png").openStream());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }

        menu_BorderPane = new BorderPane();
        //menu_BorderPane.setTop(menuBar);
        //=========================================

//        Image img = null;
//        //
//        try
//        {
//            img = new Image(this.getClass().getClassLoader().getResource("Images/uzor_lesnoj_motiv_131405_2796x2796.png").openStream());
//        }
//        catch (IOException ioExc)
//        {
//            System.exit(1);
//        }
//        BackgroundImage bImage = new BackgroundImage(img, BackgroundRepeat.REPEAT, null, BackgroundPosition.DEFAULT, new BackgroundSize(10, 20, true, true, true, true));
//        //superRoot.setBackground(new Background(bImage));
//        //

        root = new VBox(rem * 1.05D, timerName_Box, gp);
        //root.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        root.setPadding(new Insets(rem * 1.0D));

        superRoot = new VBox(menu_BorderPane, root);
        superRoot.setMinWidth(PREFERRED_WIDTH - 1);
        superRoot.setMaxWidth(PREFERRED_WIDTH + 2);

        startTimer_button = new Button();
        startTimer_button.setPrefWidth(PREFERRED_WIDTH * 0.5D);
        startTimer_button.setOnAction(event ->
        {
            startTimer_Action(event);
        });

        stopTimer_button = new Button();
        //startTimer_button.setPrefWidth(root.getPrefWidth() / 2);//?????????? ????????????
        stopTimer_button.setDisable(true);
//        stopTimer_button.setOnAction(event ->
//        {
//            stopTimerButton_Action(event);
//        });
        stopTimer_button.setOnMouseClicked(event ->
        {
            if (event.getClickCount() >= 2)
            {
                stopTimerButton_Action(null);
            }
            else
            {
                popup.show(stopTimer_button, event.getScreenX(), event.getScreenY() + stopTimer_button.getBoundsInParent().getHeight());
            }
        });

        HBox startTimer_Box = new HBox(rem * 1.0D, startTimer_button);
        startTimer_Box.setAlignment(Pos.CENTER);

        //???????????? ?? ???????????????????? ?????????? ?????????????????? ??????????????
        shutdown_radio = new RadioButton("Shut Down");
        shutdown_radio.setOnAction(event ->
        {
            if (radioGroup_Box.getChildren().contains(customCommand_textField))
            {
                radioGroup_Box.getChildren().remove(customCommand_textField);
            }
            if (killProcess_HBox.getChildren().contains(processID_TextField))
            {
                killProcess_HBox.getChildren().remove(processID_TextField);
            }
            actionDescription = shutdownActionDescription_str;
            startTimer_button.setDisable(false);
            startTimer_MenuItem.setDisable(false);
            scene.getWindow().sizeToScene();
        });

        suspend_radio = new RadioButton("Suspend the System");
        suspend_radio.setOnAction(event ->
        {
            if (radioGroup_Box.getChildren().contains(customCommand_textField))
            {
                radioGroup_Box.getChildren().remove(customCommand_textField);
            }
            if (killProcess_HBox.getChildren().contains(processID_TextField))
            {
                killProcess_HBox.getChildren().remove(processID_TextField);
            }
            actionDescription = suspendActionDescription_str;
            startTimer_button.setDisable(false);
            startTimer_MenuItem.setDisable(false);
            scene.getWindow().sizeToScene();
        });

        custom_command = new RadioButton("Custom command");
        custom_command.setOnAction(event ->
        {
            if (custom_command.isSelected())
            {
                radioGroup_Box.getChildren().add(radioGroup_Box.getChildren().indexOf(custom_command) + 1, customCommand_textField);
                customCommand_textField.setVisible(true);
            }
            if (killProcess_HBox.getChildren().contains(processID_TextField))
            {
                killProcess_HBox.getChildren().remove(processID_TextField);
            }
            startTimer_button.setDisable(false);
            startTimer_MenuItem.setDisable(false);
            scene.getWindow().sizeToScene();
        });

        reboot_radio = new RadioButton("Reboot");
        reboot_radio.setOnAction(event ->
        {
            if (radioGroup_Box.getChildren().contains(customCommand_textField))
            {
                radioGroup_Box.getChildren().remove(customCommand_textField);
            }
            if (killProcess_HBox.getChildren().contains(processID_TextField))
            {
                killProcess_HBox.getChildren().remove(processID_TextField);
            }
            actionDescription = rebootActionDescription_str;
            startTimer_button.setDisable(false);
            startTimer_MenuItem.setDisable(false);
            scene.getWindow().sizeToScene();
        });

        killProcess_RadioButton = new RadioButton("Kill Process");
        killProcess_RadioButton.setOnAction(event ->
        {
            if (radioGroup_Box.getChildren().contains(customCommand_textField))
            {
                radioGroup_Box.getChildren().remove(customCommand_textField);
            }

            processID_TextField = new TextField();
            processID_TextField.setPrefColumnCount(5);
            processID_TextField.setPromptText("PID");

            processID_TextField.setOnKeyTyped(eventTyped ->
            {
                long startTime = System.currentTimeMillis();
                YALtools.printDebugMessage("Character: " + eventTyped.getCharacter());
                if (processID_TextField.getText().length() != 0)
                {
                    if (!eventTyped.getCharacter().equals("0")
                            && !eventTyped.getCharacter().equals("1")
                            && !eventTyped.getCharacter().equals("2")
                            && !eventTyped.getCharacter().equals("3")
                            && !eventTyped.getCharacter().equals("4")
                            && !eventTyped.getCharacter().equals("5")
                            && !eventTyped.getCharacter().equals("6")
                            && !eventTyped.getCharacter().equals("7")
                            && !eventTyped.getCharacter().equals("8")
                            && !eventTyped.getCharacter().equals("9"))
                    {
                        YALtools.printDebugMessage("???????????????????????? ????????????!");
                        processID_TextField.setText(processID_TextField.getText().substring(0, processID_TextField.getText().length() - 1));
                        processID_TextField.selectRange(processID_TextField.getText().length(), processID_TextField.getText().length());
                    }
                }
                if (processID_TextField.getText().length() > 5)
                {
                    processID_TextField.setText(processID_TextField.getText().substring(0, 5));
                    processID_TextField.selectRange(5, 5);
                }
                System.out.println("?????????? ???? ??????????????????: " + (System.currentTimeMillis() - startTime));
            });
            killProcess_HBox.getChildren().add(killProcess_HBox.getChildren().indexOf(killProcess_RadioButton) + 1, processID_TextField);

            actionDescription = killProcessActionDescription_str;
            startTimer_button.setDisable(false);
            startTimer_MenuItem.setDisable(false);
            scene.getWindow().sizeToScene();
        });

        killProcess_HBox = new HBox(rem * 0.7D, killProcess_RadioButton);
        killProcess_HBox.setAlignment(Pos.BASELINE_LEFT);

        customCommand_textField = new TextField();
        customCommand_textField.setPromptText(customCommandPromtText_str);
        customCommand_textField.setVisible(false);

        logOut_RadioButton = new RadioButton("Log out");
        logOut_RadioButton.setOnAction(event ->
        {
            killProcess_HBox.getChildren().remove(processID_TextField);
            radioGroup_Box.getChildren().remove(customCommand_textField);

            actionDescription = logOutActionDescription_str;
            startTimer_button.setDisable(false);
            startTimer_MenuItem.setDisable(false);
            scene.getWindow().sizeToScene();
        });

        ToggleGroup radioToggleGroup = new ToggleGroup();
        radioToggleGroup.getToggles().addAll(shutdown_radio, suspend_radio, reboot_radio, logOut_RadioButton, custom_command, killProcess_RadioButton);

        radioGroup_Box = new VBox(rem * 0.4D, shutdown_radio, suspend_radio, reboot_radio, logOut_RadioButton, custom_command, killProcess_HBox);
        radioGroup_Box.setPadding(new Insets(0.0D, 0.0D, 0.0D, rem * 2.1D));
        //radioGroup_Box.setBorder(new javafx.scene.layout.Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.DOTTED, new CornerRadii(rem * 0.1D),BorderWidths.FULL)));

        performActionAfterTimerWentOut_checkBox = new CheckBox("Perform Action when timer went out");
        performActionAfterTimerWentOut_checkBox.setWrapText(true);
        //performActionAfterTimerWentOut_checkBox.setBackground(new Background(bImage));
        performActionAfterTimerWentOut_checkBox.setOnAction(event ->
        {
            if (performActionAfterTimerWentOut_checkBox.isSelected())
            {
                action = true;
                root.getChildren().add(radioGroup_Box);

                YALtools.printDebugMessage("getLayoutBounds:" + root.getLayoutBounds().getHeight());
                YALtools.printDebugMessage("getBoundsInParent:" + root.getBoundsInParent().getHeight());
                YALtools.printDebugMessage("getBoundsInLocal:" + root.getBoundsInLocal().getHeight());
                YALtools.printDebugMessage("AllPaddings:" + (15 * root.getChildren().size()));
                //stage.setHeight(PREFERRED_HEIGHT + ((rem * 2.1D) * radioGroup_Box.getChildren().size()));
                //stage.setMinHeight(scene.getWindow().getHeight());

                if (!shutdown_radio.isSelected() &&
                        !reboot_radio.isSelected()
                        && !suspend_radio.isSelected()
                        && !killProcess_RadioButton.isSelected()
                        && !custom_command.isSelected()
                        && !logOut_RadioButton.isSelected())
                {
                    startTimer_button.setDisable(true);
                    startTimer_MenuItem.setDisable(true);
                }

                //System.out.println("abra: " + root.getBoundsInParent().getHeight());
                //stage.setHeight(stage.getHeight() + root.getBoundsInParent().getHeight());
            }
            else
            {
                action = false;
                root.getChildren().remove(radioGroup_Box);
                //stage.setHeight(PREFERRED_HEIGHT);
                //stage.setMinHeight(PREFERRED_HEIGHT);

                startTimer_button.setDisable(false);
                startTimer_MenuItem.setDisable(false);
            }
            System.out.println("Height before: " + scene.getWindow().getHeight());
            scene.getWindow().sizeToScene();
            System.out.println("Height after: " + scene.getWindow().getHeight());

            //stage.setWidth(PREFERRED_WIDTH);

        });
        //===================================================

        root.getChildren().addAll(startTimer_Box, new Separator(), performActionAfterTimerWentOut_checkBox);
        root.setOnRotate(event ->
        {
            timerWentOutAction();
        });


        if (superRoot.getChildren().contains(menu_BorderPane))
        {
            YALtools.printDebugMessage("???????? ???????????? ?? ???????????? ?????????? ??????????????????????????");
        }
        YALtools.printDebugMessage("superRoot-getBoundsInParent " + superRoot.getBoundsInParent().getHeight());
        YALtools.printDebugMessage("superRoot-getBoundsInLocal " + superRoot.getBoundsInLocal().getHeight());
        YALtools.printDebugMessage("gp: " + gp.getLayoutBounds().getHeight());
        YALtools.printDebugMessage("PREFERRED_HEIGHT: " + PREFERRED_HEIGHT);
    }

    private void timerWentOutAction()
    {
        stage.setIconified(false);
        //stage.setAlwaysOnTop(true);

        String message = null;
        if (!timerName_textFiels.getText().equals(""))
        {
            message = "The timer with name '" + timerName_textFiels.getText() + "' has expired.";
        }
        else
        {
            message = theTimerHasExpired_str;
        }

        showNotification(NAME_OF_PROGRAM, message);


        if (performActionAfterTimerWentOut_checkBox.isSelected())
        {

            if (delayBeforeAction_CheckBox.isSelected())
            {
                stage.setOpacity(0.5D);

                int tmpDelayTime = (int) delayBeforeAction_Spinner.getValue();
                TimerAlert ta = new TimerAlert();
                ta.initOwner(scene.getWindow());
                ta.initModality(Modality.APPLICATION_MODAL);
                ta.setAlwaysOnTop(true);
                ta.start(warning_str, doYouWantToContinue_str, tmpDelayTime);

                ta.setOnHidden(eventHidden ->
                {
                    stage.setOpacity(1.0D);
                    stage.setAlwaysOnTop(false);

                    if (ta.getResultButton().getText().equals("No"))
                    {
                        YALtools.printDebugMessage("???????????? ??????????????.");
                    }
                    else
                    {
                        performAction();
                        YALtools.printDebugMessage("Starting: stopTimerButton_Action");
                    }
                });
            }
            else
            {
                stage.setOpacity(1.0D);
                stage.setAlwaysOnTop(false);
                performAction();
            }
        }

        stopTimerButton_Action(null);
    }

    public boolean showNotification(final String aTitle, final String aMessage)
    {
        notify = new Notification(aTitle, aMessage);
        notify.setNotificationTitle(aTitle);
        notify.setMessage(aMessage);
        notify.setWidthPercent(0.25);
        notify.setHeightPercent(0.1D);
        notify.setIcon(NotificationType.INFORMATION);
        notify.setIconSizePercent(0.5D);
        notify.setDisplayDuration(Duration.seconds(10.0D));
        notify.setDisappearanceAnimation(AnimationType.FADE, Duration.seconds(1.0D));
        notify.showNotification();

        // new Notification("Advanced TimerFX", "Time is up").showNotification();
        return true;
    }

    private void showAlertAfterTimerStopping(Alert al_obj)
    {
        if (al_obj.getResult() == ButtonType.NO)
        {

        }
        else
        {
            performAction();
        }

        startTimer_button.setDisable(false);
        YALtools.printDebugMessage("Starting: stopTimerButton_Action");
        stopTimerButton_Action(null);
        //scene.setRoot(root);
    }

    //?????????? ???????????? ?????? ????????, ?????????? ???????????????????? ???????? ???????????? ???????????? ?????? ???????????? BorderPane
    private VBox timerIsRunning_pane_superRoot;
    private HBox stopTimer_Box;
    private HBox timerInformation_Box;
    private Font numbers_Font;
    private Label timerInformation_Label;
    private Font fontForLabels;
    private Font fontForLabelsValues;
    private GridPane timerInfoAppearance_GridPane;
    private Alert processContinue_Alert;
    private HBox secondMinutesHoursAppearance_Box;
    private Label timerNameValue_Label;


    private void startTimer_Action(ActionEvent event)
    {
        long startTime = System.currentTimeMillis();
        saveSettings();

        if (notify != null)
        {
            notify.hideNotification();
        }

        if (killProcess_RadioButton.isSelected())
        {
            if (processContinue_Alert == null)
            {
                processContinue_Alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
            }

            if (getProcessName(Integer.parseInt(processID_TextField.getText())).equals(""))
            {
                scene.getWindow().setOpacity(0.5D);
                processContinue_Alert.setContentText(process_str + " " + processID_TextField.getText() + " " + notFound_str + ".");
                processContinue_Alert.setHeaderText(warning_str);
                processContinue_Alert.setTitle(processNotFound_str);
                processContinue_Alert.getButtonTypes().clear();
                processContinue_Alert.getButtonTypes().add(ButtonType.OK);
                processContinue_Alert.showAndWait();
                scene.getWindow().setOpacity(1.0D);
                return;
            }
            else
            {
                actionDescription = killProcessActionDescription_str + processID_TextField.getText() + " - " + processName_str;
                processContinue_Alert.setContentText(process_str + " [" + processID_TextField.getText() + "] " + processName_str + ". " + doYouWantToContinue_str);
                processContinue_Alert.setHeaderText(warning_str);
                processContinue_Alert.showAndWait();
                if (processContinue_Alert.getResult() == ButtonType.NO)
                {
                    return;
                }
            }
        }

        int timerHours = Integer.parseInt(hours_Spinner.getValue().toString());
        int timerMinutes = Integer.parseInt(minutes_Spinner.getValue().toString());
        int timerSeconds = Integer.parseInt(seconds_Spinner.getValue().toString());

        if (timerHours == 0 && timerMinutes == 0 && timerSeconds == 0)
        {
            timerSeconds = 1;
        }

        LocalTime countdownTimer = LocalTime.of(timerHours, timerMinutes, timerSeconds);

        if (specifiedTimeTimer_MenuItem.isSelected())
        {
            LocalTime time = LocalTime.now();

            LocalTime destinationTime = LocalTime.of(timerHours, timerMinutes, timerSeconds);
            countdownTimer = destinationTime.minusHours(time.getHour());
            countdownTimer = countdownTimer.minusMinutes(time.getMinute());
            countdownTimer = countdownTimer.minusSeconds(time.getSecond());
        }

        timerTimeInSeconds = countdownTimer.get(ChronoField.SECOND_OF_DAY);
        System.out.println(timerHours + ":" + timerMinutes + ":" + timerSeconds);
        System.out.println("?????????? ?????????????? ?? ????????????????: " + timerTimeInSeconds);


        stopTimer_button.setDisable(false);
        stopTimer_MenuItem.setDisable(false);
        startTimer_button.setDisable(true);
        startTimer_MenuItem.setDisable(true);
        exit_menuItem.setDisable(true);

        timer_obj = new Timeline(new KeyFrame(Duration.seconds(timerTimeInSeconds), ev ->
        {
            YALtools.printDebugMessage("MainTimer_TimeLine was ended.");
            timerWentOutAction();
        }));
        timer_obj.play();


        timerTime_LocalTime = countdownTimer;

        if (initializingTimerIsRunningComponent_Thread.isAlive())
        {
            try
            {
                initializingTimerIsRunningComponent_Thread.join();
            }
            catch (InterruptedException interruptedException)
            {
                interruptedException.printStackTrace();
            }
        }


        //???????????????? ???????????????? ?? ?????????????????? ?????????????? ?? ?????????????????? ????????????????
        if (timerName_textFiels.getText() != null &&
                !timerName_textFiels.getText().equals(""))
        {
            /*???????? ???????????? ???????????????????? ?? ???????? ??????????????, ??.??. ???????? ?????? ??????????????
             * ???????????????????????? ???? ????????????*/
            if (timerName_Label == null)
            {
                timerName_Label = new Label(timerName_str);
                timerName_Label.setFont(fontForLabels);

                timerNameValue_Label = new Label(timerName_textFiels.getText());
                timerNameValue_Label.setFont(fontForLabelsValues);
                timerNameValue_Label.setWrapText(true);
            }
            timerNameValue_Label.setText(timerName_textFiels.getText());

            //?????? ???????????????? ?????? ????????, ?????????? ?????????? ?????????? ???????????? ???? ?????? ????????????????
            textToDefineWidth = new Text(timerName_Label.getText());
            textToDefineWidth.setFont(fontForLabels);
            YALtools.printDebugMessage("???????????? ???????????? 'timerNameLabel:': " + textToDefineWidth.getBoundsInParent().getWidth());
            timerName_Label.setMinWidth(textToDefineWidth.getBoundsInParent().getWidth());

            if (!timerInfoAppearance_GridPane.getChildren().contains(timerName_Label))
            {
                System.out.println("RowCoutn: " + timerInfoAppearance_GridPane.getRowConstraints().size());

                timerInfoAppearance_GridPane.addRow(0, timerName_Label, timerNameValue_Label);
                System.out.println("RowCoutn: " + timerInfoAppearance_GridPane.getRowConstraints().size());

            }
        }
        else
        {
            if (timerInfoAppearance_GridPane.getChildren().contains(timerName_Label))
            {
                timerInfoAppearance_GridPane.getChildren().removeAll(timerName_Label, timerNameValue_Label);
            }
        }


        if (action)
        {
            //???????????????????? ?????????????????? ???????? ?????? ?????????????? ???????????????????????? ???? ????????????
            if (actionInfo_Label == null)
            {
                actionInfo_Label = new Label(action_str);
                actionInfo_Label.setFont(fontForLabels);

                actionValue_Label = new Label(actionDescription);
                actionValue_Label.setFont(fontForLabelsValues);
                actionValue_Label.setWrapText(true);
            }

            actionValue_Label.setText(actionDescription);
            //?????? ???????????????? ?????? ????????, ?????????? ?????????? ?????????? ???????????? ???? ?????? ????????????????
            textToDefineWidth = new Text(actionInfo_Label.getText());
            textToDefineWidth.setFont(fontForLabels);
            YALtools.printDebugMessage("???????????? ???????????? 'Action:': " + textToDefineWidth.getBoundsInParent().getWidth());
            actionInfo_Label.setMinWidth(textToDefineWidth.getBoundsInParent().getWidth());

            if (!timerInfoAppearance_GridPane.getChildren().contains(actionInfo_Label))
            {
                System.out.println("RowCoutn: " + timerInfoAppearance_GridPane.getRowConstraints().size());
                timerInfoAppearance_GridPane.addRow(1, actionInfo_Label, actionValue_Label);
            }


            if (killProcess_RadioButton.isSelected() && !processID_TextField.getText().equals(""))
            {
                actionValue_Label.setText(actionValue_Label.getText());
            }
        }
        else
        {
            if (timerInfoAppearance_GridPane.getChildren().contains(actionInfo_Label))
            {
                timerInfoAppearance_GridPane.getChildren().removeAll(actionInfo_Label, actionValue_Label);
            }
        }


        //???????? ?? ?????? ?? ?????????? ???????????? ????????, ?????????????????? ??????????????????
        if (!timerIsRunning_pane_superRoot.getChildren().contains(timerIsRunning_pane))
        {
            timerIsRunning_pane_superRoot.getChildren().add(timerIsRunning_pane);
        }

        if (!timerIsRunning_pane_superRoot.getChildren().contains(menu_BorderPane))
        {
            timerIsRunning_pane_superRoot.getChildren().add(0, menu_BorderPane);
        }

        if (!timerIsRunning_pane.getChildren().contains(secondMinutesHoursAppearance_Box))
        {
            timerIsRunning_pane.getChildren().addAll(secondMinutesHoursAppearance_Box, stopTimer_Box,
                    new Separator());
        }


        //?????????????????? ?? ?????????????????? ?????????????? ?????????????????? ???????? ???????? ?????? ?????????? ???????????????? ?????? ????????????????
        if (!timerName_textFiels.getText().equals("") || performActionAfterTimerWentOut_checkBox.isSelected())
        {
            if (!timerIsRunning_pane.getChildren().contains(timerInformation_Box))
            {
                timerIsRunning_pane.getChildren().add(timerInformation_Box);
            }

            if (!timerIsRunning_pane.getChildren().contains(timerInfoAppearance_GridPane))
            {
                timerIsRunning_pane.getChildren().add(timerInfoAppearance_GridPane);
            }
        }
        else
        {
            if (timerIsRunning_pane.getChildren().contains(timerInformation_Box))
            {
                timerIsRunning_pane.getChildren().remove(timerInformation_Box);
            }
            if (timerIsRunning_pane.getChildren().contains(timerInfoAppearance_GridPane))
            {
                timerIsRunning_pane.getChildren().remove(timerInfoAppearance_GridPane);
            }
        }

        hoursAppearance_Label.setText(String.valueOf(countdownTimer.getHour()));
        minutesAppearance_Label.setText(String.valueOf(countdownTimer.getMinute()));
        secondsAppearance_Label.setText(String.valueOf(countdownTimer.getSecond()));

        superRoot.setVisible(false);
        scene.setRoot(timerIsRunning_pane_superRoot);
        timerIsRunning_pane_superRoot.setVisible(true);
        scene.getWindow().sizeToScene();

        stage.setMinHeight(scene.getWindow().getHeight() - 1);

        startTimerToUpdatingTimeAppearance();

        System.out.println("?????????? ?????????? ?????????????? ???????????? ??????????: " + (System.currentTimeMillis() - startTime));
    }

    /**
     * ?????????? ???????????????????? ?????????????????????????? ??????????????????????, ?????????????? ???????????????????????? ?????????? ???????????? ??????????????.
     */
    private void initializeTimerIsRunningComponents()
    {
        //?????????????????????????? ?????????? ?????????????????????? ?????? ?????????????????????? ???????????????????? ?? ???????????????????? ??????????????
        if (timerIsRunning_pane_superRoot == null)
        {
            timerIsRunning_pane_superRoot = new VBox();
            timerIsRunning_pane_superRoot.setMaxWidth(PREFERRED_WIDTH + 3);
            timerIsRunning_pane_superRoot.setPrefWidth(PREFERRED_WIDTH);
            timerIsRunning_pane_superRoot.setMinWidth(PREFERRED_WIDTH - 1);
        }

        if (timerIsRunning_pane == null)
        {
            timerIsRunning_pane = new VBox(rem * 1.0D);
            timerIsRunning_pane.setPadding(new Insets(rem * 1.20D));
        }

        if (numbers_Font == null)
        {
            try
            {
                URL numbersFont_URL = this.getClass().getClassLoader().getResource("Fonts/Jersey Sharp.ttf");
                numbers_Font = Font.loadFont(numbersFont_URL.openStream(), 36.0);
            }
            catch (IOException ioExc)
            {
                YALtools.printDebugMessage("???????????? ??????????-???????????? ?????? ???????????????? ????????????.\n" + ioExc.toString());
            }
        }

        if (hoursAppearance_Label == null)
        {
            hoursAppearance_Label = new Label();
            minutesAppearance_Label = new Label();
            secondsAppearance_Label = new Label();

            hoursAppearance_Label.setFont(numbers_Font);
            minutesAppearance_Label.setFont(numbers_Font);
            secondsAppearance_Label.setFont(numbers_Font);
            colon_Label.setFont(numbers_Font);
            colon_Label2.setFont(numbers_Font);
        }

        if (secondMinutesHoursAppearance_Box == null)
        {
            secondMinutesHoursAppearance_Box = new HBox(rem * 0.25D, hoursAppearance_Label, colon_Label, minutesAppearance_Label, colon_Label2, secondsAppearance_Label);
            secondMinutesHoursAppearance_Box.setAlignment(Pos.CENTER);
        }

        if (stopTimer_Box == null)
        {
            stopTimer_Box = new HBox(stopTimer_button);
            stopTimer_Box.setAlignment(Pos.CENTER);
        }

        if (timerInformation_Label == null)
        {
            timerInformation_Label = new Label(info_str);
            try
            {
                timerInformation_Label.setFont(YALtools.createFontFXFromResources("Fonts/Lettres_ombrees_ornees.otf", 26.0D));
            }
            catch (java.io.IOException ioExc)
            {
                YALtools.printDebugMessage("???????????? ??????????-???????????? ?????? ???????????????? ?????????? ????????????.\n" + ioExc.toString());
            }
        }

        if (timerInformation_Box == null)
        {
            timerInformation_Box = new HBox(timerInformation_Label);
            timerInformation_Box.setAlignment(Pos.CENTER);
        }

        if (fontForLabels == null)
        {
            try
            {
                fontForLabels = YALtools.createFontFXFromResources("Fonts/Kingthings Petrock.ttf", 26.0D);
                fontForLabelsValues = YALtools.createFontFXFromResources("Fonts/Quicksand_Bold.ttf", 14.0D);
            }
            catch (IOException ioExc)
            {
                ioExc.printStackTrace();
            }
        }

        if (timerInfoAppearance_GridPane == null)
        {
            timerInfoAppearance_GridPane = new GridPane();
            timerInfoAppearance_GridPane.setHgap(rem * 0.5D);
        }

        if (popup == null)
        {
            HBox rootPopup_HBox = new HBox();
            rootPopup_HBox.setBackground(new Background(new BackgroundFill(Color.BLANCHEDALMOND, new CornerRadii(15.0D), Insets.EMPTY)));
            rootPopup_HBox.setPadding(new Insets(rem * 0.45D));
            rootPopup_HBox.setBorder(new Border(new BorderStroke(Color.CHOCOLATE, BorderStrokeStyle.SOLID,
                    new CornerRadii(15.0D), new BorderWidths(rem * 0.09D))));

            Label textPopup = new Label(popup_str);
            textPopup.setFont(Font.font(textPopup.getFont().getName(), FontWeight.BOLD, 12.0D));

            rootPopup_HBox.getChildren().add(textPopup);

            popup = new Popup();
            popup.getContent().add(rootPopup_HBox);
            popup.setAutoHide(true);
        }
        System.out.println("?????????? ?????????????????????????? ?????????????????????? ?????????????????? ???????????? ?????????????? ????????????????????.");
    }

    private void performAction()
    {
        boolean linux = false;
        boolean windows = false;

        if (System.getProperty("os.name").contains("Windows"))
        {
            windows = true;
        }
        else if (System.getProperty("os.name").contains("Linux"))
        {
            linux = true;
        }

        StringBuilder command_strBuilder = new StringBuilder();

        if (windows)
        {
            if (performActionAfterTimerWentOut_checkBox.isSelected())
            {
                String[] command_arr = null;

                //command_strBuilder.append("cmd /c start cmd.exe /K \"");
                if (shutdown_radio.isSelected())
                {
                    command_arr = new String[3];
                    command_arr[0] = "cmd";
                    command_arr[1] = "/C";
                    command_arr[2] = "shutdown /p";
                    //command_strBuilder.append("shutdown /p\"");
                    //command_arr[3] = "/s /p";
                }
                else if (suspend_radio.isSelected())
                {
                    command_arr = new String[3];
                    command_arr[0] = "cmd";
                    command_arr[1] = "/C";
                    command_arr[2] = "rundll32 powrprof.dll,SetSuspendState 0,1,0";
                    //command_arr[1] = "powrprof.dll, SetSuspendState 0,1,0";
                }
                else if (custom_command.isSelected())
                {
                    command_arr = new String[3];
                    command_arr[0] = "cmd";
                    command_arr[1] = "/C";
                    command_arr[2] = customCommand_textField.getText();
                }
                else if (reboot_radio.isSelected())
                {
                    command_arr = new String[3];
                    command_arr[0] = "cmd";
                    command_arr[1] = "/C";
                    command_arr[2] = "shutdown /r /t 1 /f";
                    //command_arr[6] = "/r 1 /f";
                }
                else if (killProcess_RadioButton.isSelected())
                {
                    command_arr = new String[4];

                    command_arr[0] = "taskkill";
                    command_arr[1] = "/PID";
                    command_arr[2] = processID_TextField.getText();
                    command_arr[3] = "/F";

                    if (processHaveChildren)
                    {
                        command_arr[1] = "/IM";
                        command_arr[2] = processName_str;
                    }
                }
                else if (logOut_RadioButton.isSelected())
                {
                    command_arr = new String[1];
                    command_arr[0] = "logoff";
                }

                System.out.println("Windows.??????????????: ");
                for (String tmpStr : command_arr)
                {
                    System.out.print(tmpStr);
                }

                Process proc = null;
                try
                {
                    proc = new ProcessBuilder(command_arr).start();
                    //proc.waitFor();
                    YALtools.readInputStream(proc.getErrorStream());
                    proc.destroy();
                }
                catch (IOException ioException)
                {
                    try
                    {
                        YALtools.printDebugMessage("?????????? ???????????? ??????????????????:" + YALtools.readInputStream(proc.getInputStream()).toString());

                    }
                    catch (IOException ioExc)
                    {
                        System.exit(1);
                    }
                    YALtools.printDebugMessage("???????????? ??????????-???????????? ?????? ???????????????????? ?????????????? ?????? Windows: " + ioException.toString());
                    YALtools.printDebugMessage("================================");
                    ioException.printStackTrace();
                    YALtools.printDebugMessage("================================\n\n");
                }
//                catch (InterruptedException interExc)
//                {
//                    YALtools.printDebugMessage(interExc.toString());
//                }
            }
            YALtools.printDebugMessage("???????????????????? ?????????????? Wind ??????????????????.");
        }


        if (linux)
        {
            YALtools.printDebugMessage("?????????????? ????: Linux");

            try
            {
                YALtools.printDebugMessage("?????? ??????????????: " + timerName_textFiels.getText());
                String[] commandArray = null;

                if (shutdown_radio.isSelected())
                {
                    commandArray = new String[3];
                    commandArray[0] = "bash";
                    commandArray[1] = "-c";
                    commandArray[2] = "systemctl poweroff";

                }
                else if (suspend_radio.isSelected())
                {
                    commandArray = new String[3];
                    commandArray[0] = "bash";
                    commandArray[1] = "-c";
                    commandArray[2] = "systemctl suspend";
                    //command_strBuilder.append(tmpParam);
                }
                else if (custom_command.isSelected())
                {
                    commandArray = new String[3];
                    commandArray[0] = "bash";
                    commandArray[1] = "-c";
                    commandArray[2] = customCommand_textField.getText();
                    //command_strBuilder.append(customCommand_textField.getText());
                }
                else if (reboot_radio.isSelected())
                {
                    commandArray = new String[3];
                    commandArray[0] = "bash";
                    commandArray[1] = "-c";
                    commandArray[2] = "systemctl reboot";
                    //commandArray[2] = "firefox mail.yahoo.com & audacious /media/yaroslav/Freedom/1.\\ Audio/1996\\ Pure\\ Instinct\\ [1996\\ Japan\\ AMCE-950\\ EW]/1996\\ -\\ Pure\\ Instinct\\ (AMCE-950)/10\\ -\\ You\\ And\\ I.flac & gnome-terminal";
                }
                else if (killProcess_RadioButton.isSelected())
                {
                    commandArray = new String[3];
                    commandArray[0] = "kill";
                    commandArray[1] = "-9";
                    commandArray[2] = processID_TextField.getText();
                }
                else if (logOut_RadioButton.isSelected())
                {
                    commandArray = new String[4];
                    commandArray[0] = "killall";
                    commandArray[1] = "-w";
                    commandArray[2] = "-u";
                    commandArray[3] = "$USER";
                }

                if (performActionAfterTimerWentOut_checkBox.isSelected())
                {
                    System.out.println("Linux. ??????????????: '");

                    /*???????????? ?????????????? ?????????????? - ?????????????????? ?????????????? ?? ????????????????????, ???????????????????? ???????? ??????????????????
                     * */
                    //String[] commandArray1 = {"bash", "-c" , "audacious /home/yaroslav/10-YouAndI.flac -H", "celluloid '/media/yaroslav/Freedom/2. Videos/Drayv.2011.x264.BDRip.1080p.mkv'"};
                    for (String tmpStr : commandArray)
                    {
                        System.out.print(tmpStr);
                    }
                    System.out.println("'\n");


                    Process proc_1 = new ProcessBuilder(commandArray).start();
                    YALtools.readInputStream(proc_1.getErrorStream());
                    //proc_1.waitFor();
                    proc_1.destroy();
                }
            }
            catch (IOException ioExc)
            {
                YALtools.printDebugMessage("???????????????? ???????????? ??????????-???????????? ?????? ???????????????????? ??????????????: \n" + ioExc.toString());
            }
//            catch (InterruptedException iterExc)
//            {
//                YALtools.printDebugMessage(iterExc.toString());
//                return;
//            }
        }
    }

    private void startTimerToUpdatingTimeAppearance()
    {
        if (timeline == null)
        {
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev ->
            {
                YALtools.printDebugMessage("TimeLine");
                timerTime_LocalTime = timerTime_LocalTime.minusSeconds(1);
                hoursAppearance_Label.setText(String.valueOf(timerTime_LocalTime.getHour()));
                minutesAppearance_Label.setText(String.valueOf(timerTime_LocalTime.getMinute()));
                secondsAppearance_Label.setText(String.valueOf(timerTime_LocalTime.getSecond()));
            }));
        }
        timeline.setCycleCount(timerTimeInSeconds);
        timeline.play();
    }

    private void stopTimerButton_Action(ActionEvent event)
    {
        timeline.stop();
        timer_obj.stop();
        YALtools.printDebugMessage("???????????? ?????????????? ?????????????????? ??????????????.");
        stopTimer_button.setDisable(true);
        stopTimer_MenuItem.setDisable(true);
        startTimer_button.setDisable(false);
        startTimer_MenuItem.setDisable(false);
        exit_menuItem.setDisable(false);

        YALtools.printDebugMessage("??????-???? ?????????? superRoot ?????????? ??????????????????: " + superRoot.getChildren().size());

        timerIsRunning_pane_superRoot.setVisible(false);
        scene.setRoot(superRoot);
        superRoot.setVisible(true);

        /*???????????????? ?????? ?????? ????????, ?????????? ?????????????????? ???????????????? ???????????????????? ???????? ?????? ?????????? ?????????????????? ???????? ??????????*/
        if (!superRoot.getChildren().contains(menu_BorderPane))
        {
            superRoot.getChildren().add(0, menu_BorderPane);
        }

        scene.getWindow().sizeToScene();

        stage.setMinHeight(scene.getWindow().getHeight() - 1);

    }

    public LocalTime getTimerTime()
    {
        return timerTime_LocalTime;
    }

    public Label getHoursAppearance_Label()
    {
        return hoursAppearance_Label;
    }

    public Label getMinutesAppearance_Label()
    {
        return minutesAppearance_Label;
    }

    public Label getSecondsAppearance_Label()
    {
        return secondsAppearance_Label;
    }

    private void initLocalization(final String aLocale)
    {
        Preferences local = null;
        try
        {
            local = Preferences.userRoot().node("YALdysse/AdvancedTimerFX");

            URL langNode_URL = this.getClass().getClassLoader().getResource("Localizations/Language_" + aLocale + ".lang");
            YALtools.printDebugMessage("???????? ?? ?????????? ??????????????????????: " + langNode_URL.toExternalForm());

            local.importPreferences(langNode_URL.openStream());

            startButton_str = local.get("startTimer_button", "Start");
            inTimerType_str = local.get("timerType_In_Label", "in");
            atTimerType_str = local.get("timerType_At_Label", "at");
            notify_str = local.get("notify_str", "Notify");

            if (countdownTimer_MenuItem.isSelected())
            {
                startTimer_button.setText(notify_str + " " + inTimerType_str + " (" + startButton_str + ")");
            }
            else
            {
                startTimer_button.setText(notify_str + " " + atTimerType_str + " (" + startButton_str + ")");
            }

            stopTimer_button.setText(local.get("stopTimer_button", "Start"));
            startTimer_MenuItem.setText(local.get("startTimer_MenuItem", "Start"));
            stopTimer_MenuItem.setText(local.get("stopTimer_MenuItem", "Start"));
            performActionAfterTimerWentOut_checkBox.setText(local.get("performActionAfterTimerWentOut_checkBox", "Start"));
            shutdown_radio.setText(local.get("shutdown_radio", "Shut Down"));
            suspend_radio.setText(local.get("suspend_radio", "Suspend the System"));
            custom_command.setText(local.get("customCommand_Label", "Custom command"));
            reboot_radio.setText(local.get("reboot_radio", "Reboot"));
            timerType_Menu.setText(local.get("timerType_Menu", "Timer type"));
            specifiedTimeTimer_MenuItem.setText(local.get("specifiedTime_MenuItem", "at specified time"));
            countdownTimer_MenuItem.setText(local.get("countdownTimer_MenuItem", "in specified time"));
            timerName_textFiels.setPromptText(local.get("TimerName_PromtText", "Name of timer (optional)"));
            timerName_str = local.get("timerName_Label", "Name of timer:");
            action_str = local.get("timerAction_Label", "Action: ");
            info_str = local.get("info_text", "Info");
            gitHubRepository_MenuItem.setText(local.get("gitHubRepository_MenuItem", "Repository on GitHub"));
            customCommandPromtText_str = local.get("customCommandPromtText", "Enter here command that will be performed after timer went out");
            delayBeforeAction_CheckBox.setText(local.get("delayBeforeAction_CheckBox", "Delay before action"));
            suspendActionDescription_str = local.get("suspendActionDescription", "Suspend the System");
            rebootActionDescription_str = local.get("rebootActionDescription", "Reboot the System");
            shutdownActionDescription_str = local.get("shutdownActionDescription", "Shut Down the System");
            killProcess_RadioButton.setText(local.get("killProcess_radio", "Kill process"));
            killProcessActionDescription_str = local.get("killProcessActionDescription", "Kill process with PID ");
            processNotFound_str = local.get("processNotFound_str", "Process not found");
            process_str = local.get("process_str", "Process");
            notFound_str = local.get("notFound_str", "not found");
            doYouWantToContinue_str = local.get("doYouWantToContinue_str", "Do you want to continue ?");
            warning_str = local.get("warning_str", "Warning!");
            logOut_RadioButton.setText(local.get("logOut_RadioButton", "Log out"));
            logOutActionDescription_str = local.get("logOut_ActionDescription_str", "Log out");
            popup_str = local.get("popup_str", "To stop timer click left button twice.");
            customCommand_textField.setPromptText(customCommandPromtText_str);
            theTimerHasExpired_str = local.get("theTimerHasExpired_str", "The timer has expired.");


            if (timerInformation_Label != null)
            {
                timerInformation_Label.setText(info_str);
            }

            if (timerName_Label != null)
            {
                timerName_Label.setText(local.get("timerName_Label", "Start"));
            }

            if (actionInfo_Label != null)
            {
                actionInfo_Label.setText(local.get("timerAction_Label", "Action:"));

                //???????????????????????? ????????????????
                if (reboot_radio.isSelected())
                {
                    actionDescription = rebootActionDescription_str;
                    actionValue_Label.setText(rebootActionDescription_str);
                }
                else if (suspend_radio.isSelected())
                {
                    actionDescription = suspendActionDescription_str;
                    actionValue_Label.setText(suspendActionDescription_str);
                }
                else if (shutdown_radio.isSelected())
                {
                    actionDescription = shutdownActionDescription_str;
                    actionValue_Label.setText(shutdownActionDescription_str);
                }
                else if (killProcess_RadioButton.isSelected())
                {
                    actionDescription = killProcessActionDescription_str + processID_TextField.getText();
                    actionValue_Label.setText(actionDescription);
                }
                else if (logOut_RadioButton.isSelected())
                {
                    actionDescription = logOutActionDescription_str;
                    actionValue_Label.setText(actionDescription);
                }
            }

//            if (timerType_Label != null)
//            {
//                if (specifiedTimeTimer_MenuItem.isSelected())
//                {
//                    timerType_Label.setText(atTimerType_str);
//                }
//                else
//                {
//                    timerType_Label.setText(inTimerType_str);
//                }
//            }

            general_menu.setText(local.get("general_menu", "General"));
            language_Menu.setText(local.get("language_Menu", "Language"));
            timer_Menu.setText(local.get("timer_Menu", "Timer"));
            exit_menuItem.setText(local.get("exit_MenuItem", "Exit"));

            if (hours_text != null)
            {
                hours_text.setText(local.get("hours_Text", "Hours"));
                minutes_text.setText(local.get("minutes_Text", "Minutes"));
                seconds_text.setText(local.get("seconds_Text", "Seconds"));
            }

            if (popup != null)
            {
                HBox temporary = (HBox) popup.getContent().get(0);
                Label temporaryLabel = (Label) temporary.getChildren().get(0);
                temporaryLabel.setText(popup_str);
            }

            if (hoursAppearance_Label != null)
            {
                hoursAppearance_Label.setText(local.get("hours_Text", "Hours"));
                minutesAppearance_Label.setText(local.get("minutes_Text", "Minutes"));
                secondsAppearance_Label.setText(local.get("seconds_Text", "Seconds"));
            }
            local.clear();
            local.parent().removeNode();
        }
        catch (IOException ioException)
        {
            YALtools.printDebugMessage("???????????????? ???????????? ?????? ?????????????? ???????? ??????????????????????.");
            Alert alert = new Alert(Alert.AlertType.ERROR, "???????????????? ???????????? ?????? ???????????????? ?????????? ??????????????????????.", ButtonType.OK);
            return;
        }
        catch (InvalidPreferencesFormatException invPrefFormExc)
        {
            YALtools.printDebugMessage("?????????????????????? ???????????? ?????????? ???????? ??????????????????????.\n" + invPrefFormExc.toString());
            return;
        }
        catch (BackingStoreException invPrefFormExc)
        {
            YALtools.printDebugMessage("???????????????? ???????????? ?????? ???????????????? ?????????? ???????? ??????????????????????.\n" + invPrefFormExc.toString());
            return;
        }
        currentLanguage_str = aLocale;
        //YALtools.printDebugMessage(local.absolutePath());
    }

    private void saveSettings()
    {
        Preferences pref = Preferences.userRoot().node("YALdysse/AdvancedTimerFX/Settings");

        pref.putInt("locX", (int) stage.getX());
        pref.putInt("locY", (int) stage.getY());
        pref.put("Language", currentLanguage_str);
        pref.putBoolean("delayBeforeAction_CheckBox", delayBeforeAction_CheckBox.isSelected());
        pref.putInt("delayBeforeAction", (int) delayBeforeAction_Spinner.getValue());


        try
        {
            YALtools.printDebugMessage(YALtools.getJarLocation().getAbsolutePath());
            FileOutputStream fis = new FileOutputStream(new File(YALtools.getJarLocation().getParent() + "/settings.pref"));
            pref.exportNode(fis);

            pref.clear();
            pref.parent().parent().removeNode();
        }
        catch (FileNotFoundException fileNFExc)
        {
            YALtools.printDebugMessage("???????????????? ???????????? ?????? ?????????????????????? ???????????????? ???????????????????? ????????.\n" + fileNFExc.toString());
        }
        catch (BackingStoreException backStoreExc)
        {
            YALtools.printDebugMessage("???????????????? ?????????? ?????? ???????????????? ???????? ????????????????.\n" + backStoreExc.toString());
        }
        catch (IOException ioExc)
        {
            YALtools.printDebugMessage("???????????????? ???????????? ??????????-???????????? ?????? ???????????????????? ???????? ????????????????.\n" + ioExc.toString());
        }
        catch (Exception ioExc)
        {
            YALtools.printDebugMessage("???????????????? ???????????? ??????????-???????????? ???????? ????????????????.\n" + ioExc.toString());
        }
    }

    private void importSettings()
    {
        long startTime = System.currentTimeMillis();

        Preferences pref = Preferences.userRoot().node("YALdysse/AdvancedTimerFX/Settings");

        try
        {
            YALtools.printDebugMessage("?????????? ???????? ????????????????: " + YALtools.getJarLocation().getParent() + "/settings.pref");

            FileInputStream fis = new FileInputStream(new File(YALtools.getJarLocation().getParent() + "/settings.pref"));
            pref.importPreferences(fis);

            currentLanguage_str = pref.get("Language", "English");
            locX = pref.getInt("locX", 0);
            locY = pref.getInt("locY", 0);
            delayBeforeAction_CheckBox.setSelected(pref.getBoolean("delayBeforeAction_CheckBox", true));
            delayBeforeAction_Spinner.getValueFactory().setValue(pref.getInt("delayBeforeAction", 15));

            if (delayBeforeAction_CheckBox.isSelected())
            {
                delayCustomMenuItem_HBox.getChildren().add(delayBeforeAction_Spinner);
            }
            else delayCustomMenuItem_HBox.getChildren().remove(delayBeforeAction_Spinner);

            pref.clear();
            pref.parent().parent().removeNode();
        }
        catch (FileNotFoundException fileNFExc)
        {
            YALtools.printDebugMessage("???????????????? ???????????? ?????? ?????????????????????? ???????????????? ???????????????????? ????????.\n" + fileNFExc.toString());
        }
        catch (BackingStoreException backStoreExc)
        {
            YALtools.printDebugMessage("???????????????? ?????????? ?????? ???????????????? ???????? ????????????????.\n" + backStoreExc.toString());
        }
        catch (IOException ioExc)
        {
            YALtools.printDebugMessage("???????????????? ???????????? ??????????-???????????? ?????? ???????????????? ???????? ????????????????.\n" + ioExc.toString());
        }
        catch (Exception ioExc)
        {
            YALtools.printDebugMessage("???????????????? ???????????? ??????????-???????????? ???????? ????????????????.\n" + ioExc.toString());
        }
        System.out.println("Settings: " + (System.currentTimeMillis() - startTime));
    }

    private String getProcessName(final int aPID)
    {
        if (System.getProperty("os.name").indexOf("Linux") != -1)
        {
            try
            {
                String[] commandArr = {"bash", "-c", "ps", "ax", "|", "grep", "-i ", String.valueOf(aPID)};
                Process proc = Runtime.getRuntime().exec(commandArr);
                StringBuilder result_strBuilder = YALtools.readInputStream(proc.getInputStream());
                YALtools.printDebugMessage("ErrorStream: " + YALtools.readInputStream(proc.getErrorStream()));

                YALtools.printDebugMessage("InputStream: " + result_strBuilder.toString());

                //??????????????????
                String[] splitted = result_strBuilder.toString().split("\n");
                String result_str = "";
                for (String tmpStr : splitted)
                {
                    if (tmpStr.indexOf(processID_TextField.getText()) != -1)
                    {
                        result_str = tmpStr;
                    }
                }
                YALtools.printDebugMessage("???????????????????? ?? ????????????????: " + result_str);

                splitted = result_str.split(" ");
                processName_str = splitted[splitted.length - 1];
                YALtools.printDebugMessage("?????? ????????????????: " + processName_str);
                return processName_str;
            }
            catch (IOException ioExc)
            {
                YALtools.printDebugMessage("???????????????? ???????????? ?????????? ???????????? ?????? ?????????????????????? ?????????????? ???????????? ????????????????.\n" + ioExc.toString());
            }
        }
        else if (System.getProperty("os.name").indexOf("Windows") != -1)
        {
            try
            {
                YALtools.printDebugMessage("?????????? ???????????????? ?? windows " + aPID);

                String[] commandArr = {"tasklist"};
                Process proc = Runtime.getRuntime().exec(commandArr);
                StringBuilder result_strBuilder = YALtools.readInputStream(proc.getInputStream());
                YALtools.printDebugMessage("ErrorStream: " + YALtools.readInputStream(proc.getErrorStream()));

                YALtools.printDebugMessage("InputStream: " + result_strBuilder.toString());

                //??????????????????
                String[] splitted = result_strBuilder.toString().split("\n");
                String result_str = "";
                for (String tmpStr : splitted)
                {
                    if (tmpStr.indexOf(String.valueOf(aPID)) != -1)
                    {
                        result_str = tmpStr;
                    }
                }
                YALtools.printDebugMessage("???????????????????? ?? ????????????????: " + result_str);

                String[] curProcess_splitted_str = result_str.split(" ");
                processName_str = curProcess_splitted_str[0];
                YALtools.printDebugMessage("?????? ????????????????: " + processName_str);

                //???????? ???? ?????? ???????????????? ?? ?????????? ????????????
                String tmpStr = "";
                int countEquals = 0;

                for (int k = 0; k < splitted.length; k++)
                {
                    tmpStr = splitted[k].split(" ")[0];
                    if (tmpStr.equals(processName_str))
                    {
                        countEquals++;
                    }
                }

                if (countEquals >= 2)
                {
                    processHaveChildren = true;
                    YALtools.printDebugMessage("?? ?????????????? ?????????????? ??????????????.");
                }

                return processName_str;
            }
            catch (IOException ioExc)
            {
                YALtools.printDebugMessage("???????????????? ???????????? ?????????? ???????????? ?????? ?????????????????????? ?????????????? ???????????? ????????????????.\n" + ioExc.toString());
            }
        }
        return null;
    }
}
