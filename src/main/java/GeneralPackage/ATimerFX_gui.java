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
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;
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

    private Button startTimer_button;
    private Button stopTimer_button;

    private CheckBox performActionAfterTimerWentOut_checkBox;

    private RadioButton shutdown_radio;
    private RadioButton suspend_radio;
    private RadioButton reboot_radio;
    private RadioButton custom_command;
    private TextField customCommand_textField;
    private Timeline timer_obj;
    private Pane root;
    private Pane timerIsRunning_pane;
    private Label hoursAppearance_Label;
    private Label minutesAppearance_Label;
    private Label secondsAppearance_Label;
    private Label timerName_Label;
    private Label timerAction_Label;
    private MenuBar menuBar;
    private Menu general_menu;
    private Menu language_Menu;
    private MenuItem russianLanguage_MenuItem;
    private MenuItem ukrainianLanguage_MenuItem;
    private MenuItem englishLanguage_MenuItem;
    private MenuItem gitHubRepository_MenuItem;
    private Menu timer_Menu;
    private MenuItem startTimer_MenuItem;
    private MenuItem stopTimer_MenuItem;
    private MenuItem exit_menuItem;
    private BorderPane menu_BorderPane;

    private static final double rem = new Text("").getBoundsInParent().getHeight();
    private final Label colon_Label = new Label(":");
    private final Label colon_Label2 = new Label(":");
    private int timerTimeInSeconds;
    private boolean action = false;
    private String actionDescription = null;

    private VBox superRoot;

    private LocalTime timerTime_LocalTime;
    Timeline timeline = null;

    private Scene scene;
    private Stage stage;

    private Text textToDefineWidth;
    private String timerName_str = "Name of Timer: ";
    private String action_str = "Action: ";
    private String info_str = "Info";
    private String customCommandPromtText_str = "Enter here command that will be performed after timer went out";

    private Label actionInfo_Label;
    private int locX = 0;
    private int locY = 0;
    private String currentLanguage_str = "English";

    private static final int PREFERRED_WIDTH = 360;
    private static final int PREFERRED_HEIGHT = (int) (rem * 17.4D);

    public void start(Stage aStage)
    {
        initComponents();
        stage = aStage;
        scene = new Scene(superRoot);

        //superRoot.prefHeightProperty().bind(scene.heightProperty());
        //superRoot.prefWidthProperty().bind(scene.widthProperty());

        stage.setOnCloseRequest(event ->
        {
            saveSettings();
        });

        importSettings();
        initLocalization(currentLanguage_str);

        stage.setMinHeight(PREFERRED_HEIGHT);
        stage.setMinWidth(PREFERRED_WIDTH);
        stage.setX(locX);
        stage.setY(locY);

        stage.setScene(scene);
        stage.setTitle("Advanced Timer FX [build 16 Beta]");

        scene.getWindow().setWidth(PREFERRED_WIDTH);
        scene.getWindow().setHeight(PREFERRED_HEIGHT);

        stage.show();
    }

    private Image russianFlag_Image;
    private Image ukraineFlag_Image;
    private Image unitedKingdom_Image;
    private Image exit_Image;
    private Image gitHub_Image;

    private void initComponents()
    {
        YALtools.printDebugMessage("rem: " + rem);

        timerName_textFiels = new TextField();
        timerName_textFiels.setPromptText("Name of timer (optional)");
        //timerName_textFiels.setMaxWidth(Double.MAX_VALUE);

        HBox timerName_Box = new HBox(rem * 0.1, timerName_textFiels);
        timerName_Box.setAlignment(Pos.CENTER);

        hours_Spinner = new Spinner(0, 23, 0);
        //hours_Spinner.setMaxWidth(10);
        hours_Spinner.setMaxWidth(70);
        hours_Spinner.setOnScroll(event ->
        {
            int increment = 1;

            YALtools.printDebugMessage("getDeltaY: " + event.getDeltaY());

            if (event.getDeltaY() > 10)
            {
                hours_Spinner.getValueFactory().setValue((int) hours_Spinner.getValue() + increment);
            } else if (event.getDeltaY() < 0)
            {
                hours_Spinner.getValueFactory().setValue((int) hours_Spinner.getValue() - increment);
            }
        });

        minutes_Spinner = new Spinner(0, 59, 0);
        minutes_Spinner.setMaxWidth(70);
        minutes_Spinner.setOnScroll(event ->
        {
            int increment = 1;

            YALtools.printDebugMessage("getDeltaY: " + event.getDeltaY());

            if (event.getDeltaY() > 10)
            {
                minutes_Spinner.getValueFactory().setValue((int) minutes_Spinner.getValue() + increment);
            } else if (event.getDeltaY() < 0)
            {
                minutes_Spinner.getValueFactory().setValue((int) minutes_Spinner.getValue() - increment);
            }
        });

        seconds_Spinner = new Spinner(1, 59, 1);
        seconds_Spinner.setMaxWidth(70);
        seconds_Spinner.setOnScroll(event ->
        {
            int increment = 1;

            YALtools.printDebugMessage("getDeltaY: " + event.getDeltaY());

            if (event.getDeltaY() > 10)
            {
                seconds_Spinner.getValueFactory().setValue((int) seconds_Spinner.getValue() + increment);
            } else if (event.getDeltaY() < 0)
            {
                seconds_Spinner.getValueFactory().setValue((int) seconds_Spinner.getValue() - increment);
            }
        });

        hours_text = new Text("Hours");
        minutes_text = new Text("Minutes");
        seconds_text = new Text("Seconds");

        javafx.scene.layout.GridPane gp = new GridPane();
        gp.add(hours_Spinner, 0, 0);
        gp.add(minutes_Spinner, 1, 0);
        gp.add(seconds_Spinner, 2, 0);
        gp.add(hours_text, 0, 1);
        gp.add(minutes_text, 1, 1);
        gp.add(seconds_text, 2, 1);
        gp.setAlignment(Pos.CENTER);
        //gp.setGridLin
        // esVisible(true);
        gp.setHgap(rem * 1.1);

        //--------------------------------- Меню
        menuBar = new MenuBar();
        general_menu = new Menu("General");
        language_Menu = new Menu("Language");
        timer_Menu = new Menu("Timer");

        //Загружыем флаги стран
        try
        {
            russianFlag_Image = new Image(this.getClass().getClassLoader().getResource("Images/russia.png").openStream());
            ukraineFlag_Image = new Image(this.getClass().getClassLoader().getResource("Images/ukraine_2.png").openStream());
            unitedKingdom_Image = new Image(this.getClass().getClassLoader().getResource("Images/UK.png").openStream());
            exit_Image = new Image(this.getClass().getClassLoader().getResource("Images/exit.png").openStream());
            gitHub_Image = new Image(this.getClass().getClassLoader().getResource("Images/gitHub.png").openStream());
        }
        catch (IOException ioExc)
        {

        }
        ImageView russianFlag_ImageView = new ImageView(russianFlag_Image);
        russianFlag_ImageView.setFitHeight(24);
        russianFlag_ImageView.setFitWidth(24);

        ImageView ukraineFlag_ImageView = new ImageView(ukraineFlag_Image);
        ukraineFlag_ImageView.setFitHeight(24);
        ukraineFlag_ImageView.setFitWidth(24);

        ImageView unitedKingdom_ImageView = new ImageView(unitedKingdom_Image);
        unitedKingdom_ImageView.setFitHeight(24);
        unitedKingdom_ImageView.setFitWidth(24);

        ImageView exit_ImageView = new ImageView(exit_Image);
        exit_ImageView.setFitWidth(24);
        exit_ImageView.setFitHeight(24);

        ImageView gitHub_ImageView = new ImageView(gitHub_Image);
        gitHub_ImageView.setFitWidth(24);
        gitHub_ImageView.setFitHeight(24);

        startTimer_MenuItem = new MenuItem("Start Timer");
        startTimer_MenuItem.setOnAction(event ->
        {
            startTimer_Action(null);
        });
        startTimer_MenuItem.acceleratorProperty().set(new KeyCodeCombination(KeyCode.ENTER, KeyCodeCombination.CONTROL_DOWN));

        stopTimer_MenuItem = new MenuItem("Stop Timer");
        stopTimer_MenuItem.setOnAction(event ->
        {
            stopTimerButton_Action(null);
        });
        stopTimer_MenuItem.acceleratorProperty().set(new KeyCodeCombination(KeyCode.SPACE, KeyCodeCombination.CONTROL_DOWN));
        stopTimer_MenuItem.setDisable(true);

        exit_menuItem = new MenuItem("Exit", exit_ImageView);
        exit_menuItem.acceleratorProperty().set(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        exit_menuItem.setOnAction(event ->
        {
            YALtools.printDebugMessage("Завершение работы приложения.");
            saveSettings();
            System.exit(0);
        });

        gitHubRepository_MenuItem = new MenuItem("Repository on GitHub", gitHub_ImageView);
        gitHubRepository_MenuItem.setOnAction(event ->
        {
            YALtools.printDebugMessage("Переходим к репозиторию GitHub.");
            try
            {
                Desktop.getDesktop().browse(new URI("https://github.com/YALdysse/Advanced_TimerFX"));
            }
            catch (URISyntaxException uriSynExc)
            {
                YALtools.printDebugMessage("sdk");
            }
            catch (IOException ioExc)
            {
                YALtools.printDebugMessage("Возникла ошибка ввода-вывода при переходе к репозиторию GitHub.\n" + ioExc.toString());
            }
        });


        russianLanguage_MenuItem = new MenuItem("Русский", russianFlag_ImageView);
        russianLanguage_MenuItem.setOnAction(event ->
        {
            initLocalization("Russian");
        });
        ukrainianLanguage_MenuItem = new MenuItem("Українська", ukraineFlag_ImageView);
        ukrainianLanguage_MenuItem.setOnAction(event ->
        {
            initLocalization("Ukrainian");
        });

        englishLanguage_MenuItem = new MenuItem("English", unitedKingdom_ImageView);
        englishLanguage_MenuItem.setOnAction(event ->
        {
            initLocalization("English");
        });

        language_Menu.getItems().addAll(russianLanguage_MenuItem, ukrainianLanguage_MenuItem, englishLanguage_MenuItem);
        general_menu.getItems().addAll(language_Menu, gitHubRepository_MenuItem, exit_menuItem);
        timer_Menu.getItems().addAll(startTimer_MenuItem, stopTimer_MenuItem);

        menuBar.getMenus().addAll(general_menu, timer_Menu);

        menu_BorderPane = new BorderPane();
        menu_BorderPane.setTop(menuBar);
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
        root.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        root.setPadding(new Insets(rem * 1.0D));

        superRoot = new VBox(menu_BorderPane, root);

        timerName_textFiels.setPrefWidth(root.getPrefWidth() - 15);


        startTimer_button = new Button("Start");
        startTimer_button.setPrefWidth(root.getPrefWidth() / 2);
        startTimer_button.setOnAction(event ->
        {
            startTimer_Action(event);
        });

        stopTimer_button = new Button("Stop");
        startTimer_button.setPrefWidth(root.getPrefWidth() / 2);//Здесь ошибка
        stopTimer_button.setDisable(true);
        stopTimer_button.setOnAction(event ->
        {
            stopTimerButton_Action(event);
        });

        HBox startTimer_Box = new HBox(rem * 1.0D, startTimer_button);
        startTimer_Box.setAlignment(Pos.CENTER);
        //startTimer_Box.setPrefWidth(root.getPrefWidth()-15);

        //Панель с действиями после истечении времени
        shutdown_radio = new RadioButton("Shut Down");
        shutdown_radio.setOnAction(event ->
        {
            customCommand_textField.setVisible(false);
            actionDescription = "Shut Down the System after timer ending.";
        });

        suspend_radio = new RadioButton("Suspend the System");
        suspend_radio.setOnAction(event ->
        {
            customCommand_textField.setVisible(false);
            actionDescription = "Suspend the System after timer ending.";
        });

        custom_command = new RadioButton("Custom command");
        custom_command.setOnAction(event ->
        {
            if (custom_command.isSelected())
            {
                customCommand_textField.setVisible(true);
            }
        });

        reboot_radio = new RadioButton("Reboot");
        reboot_radio.setOnAction(event ->
        {
            customCommand_textField.setVisible(false);
            actionDescription = "Reboot the System after timer ending.";
        });

        customCommand_textField = new TextField();
        customCommand_textField.setPromptText(customCommandPromtText_str);
        customCommand_textField.setVisible(false);

        ToggleGroup radioToggleGroup = new ToggleGroup();
        radioToggleGroup.getToggles().addAll(shutdown_radio, suspend_radio, reboot_radio, custom_command);

        VBox radioGroup_Box = new VBox(rem * 0.4D, shutdown_radio, suspend_radio, reboot_radio, custom_command, customCommand_textField);
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
                scene.getWindow().setHeight(PREFERRED_HEIGHT + ((rem * 1.85D) * radioGroup_Box.getChildren().size()));
            } else
            {
                action = false;
                root.getChildren().remove(radioGroup_Box);
                scene.getWindow().setHeight(PREFERRED_HEIGHT);
            }
        });
        //===================================================

        root.getChildren().addAll(startTimer_Box, new Separator(), performActionAfterTimerWentOut_checkBox);
        root.setOnRotate(event ->
        {
            timerWentOutAction(event);
        });


        if (superRoot.getChildren().contains(menu_BorderPane))
        {
            YALtools.printDebugMessage("Меню входит в панель после инициализации");
        }
        YALtools.printDebugMessage("superRoot-getBoundsInParent " + superRoot.getBoundsInParent().getHeight());
        YALtools.printDebugMessage("superRoot-getBoundsInLocal " + superRoot.getBoundsInLocal().getHeight());
        YALtools.printDebugMessage("gp: " + gp.getLayoutBounds().getHeight());
        YALtools.printDebugMessage("PREFERRED_HEIGHT: " + PREFERRED_HEIGHT);
    }

    private void timerWentOutAction(RotateEvent event)
    {
        boolean alertWasHidden = false;
//        timer_obj.stop();
//        timeline.stop();
//        Alert al = new Alert(Alert.AlertType.WARNING, "Do you want to continue ?", ButtonType.YES, ButtonType.NO);
//        al.setOnHidden(event1 ->
//        {
//            showAlertAfterTimerStopping(al);
//
//        });
//
//        al.show();

        //Окно с таймером на передний план
        //Это сделано для того, чтобы после исхода времени таймера главное окно программы всегда
        //поднималось поверх других до тех, пор, пока пользователь не кликнет в окне
        scene.setOnMouseClicked(evetn ->
        {
            YALtools.printDebugMessage("Мышь вошла в окно");
            stage.setAlwaysOnTop(false);
        });
        stage.setAlwaysOnTop(true);


        if (performActionAfterTimerWentOut_checkBox.isSelected())
        {
            TimerAlert ta = new TimerAlert();
            ta.initModality(Modality.APPLICATION_MODAL);
            ta.setAlwaysOnTop(true);
            ta.setHeight(190);
            ta.setMinHeight(190);
            ta.setMinWidth(300);
            ta.start("Warning", "Do you want to continue ?");
            //ta.start(new Stage());

            ta.setOnHidden(eventHidden ->
            {
                if (ta.getResultButton().getText().equals("No"))
                {
                    YALtools.printDebugMessage("Таймер отменен.");
                    stopTimerButton_Action(null);
                    return;
                } else
                {
                    performAction();
                    YALtools.printDebugMessage("Starting: stopTimerButton_Action");
                    stopTimerButton_Action(null);
                }
            });
        } else
        {
            //Уведомление
            Alert timerWentOut_Alert = new Alert(Alert.AlertType.INFORMATION, "---", ButtonType.OK);
            timerWentOut_Alert.initStyle(StageStyle.UTILITY);
            timerWentOut_Alert.setHeaderText("Timer '" + timerName_textFiels.getText() + "' went out");
            timerWentOut_Alert.setTitle("Advanced TimerFX");
            timerWentOut_Alert.initModality(Modality.APPLICATION_MODAL);
            timerWentOut_Alert.show();

            performAction();
            stopTimerButton_Action(null);
        }

    }

    private void showAlertAfterTimerStopping(Alert al_obj)
    {
        if (al_obj.getResult() == ButtonType.NO)
        {

        } else
        {
            performAction();
        }

        startTimer_button.setDisable(false);
        YALtools.printDebugMessage("Starting: stopTimerButton_Action");
        stopTimerButton_Action(null);
        //scene.setRoot(root);
    }

    private void performAction()
    {
        boolean linux = false;
        boolean windows = false;

        if (System.getProperty("os.name").indexOf("Windows") != -1)
        {
            windows = true;
        } else if (System.getProperty("os.name").indexOf("Linux") != -1)
        {
            linux = true;
        }

        StringBuilder command_strBuilder = new StringBuilder();

        if (windows)
        {
            if (performActionAfterTimerWentOut_checkBox.isSelected())
            {
                String[] command_arr = null;

                System.out.println("Windows");

                //command_strBuilder.append("cmd /c start cmd.exe /K \"");
                if (shutdown_radio.isSelected())
                {
                    command_arr = new String[6];
                    command_arr[0] = "cmd";
                    command_arr[1] = "/C";
                    command_arr[2] = "start";
                    command_arr[3] = "cmd.exe";
                    command_arr[4] = "/C";
                    command_arr[5] = "shutdown /p";
                    //command_strBuilder.append("shutdown /p\"");
                    //command_arr[3] = "/s /p";
                } else if (suspend_radio.isSelected())
                {
                    command_arr = new String[6];
                    command_arr[0] = "cmd";
                    command_arr[1] = "/C";
                    command_arr[2] = "start";
                    command_arr[3] = "cmd.exe";
                    command_arr[4] = "/C";
                    command_arr[5] = "rundll32 powrprof.dll,SetSuspendState 0,1,0";
                    //command_arr[1] = "powrprof.dll, SetSuspendState 0,1,0";
                } else if (custom_command.isSelected())
                {
                    command_arr = new String[6];
                    command_arr[0] = "cmd";
                    command_arr[1] = "/C";
                    command_arr[2] = "start";
                    command_arr[3] = "cmd.exe";
                    command_arr[4] = "/C";
                    command_arr[5] = customCommand_textField.getText();
                } else if (reboot_radio.isSelected())
                {
                    command_arr = new String[6];
                    command_arr[0] = "cmd";
                    command_arr[1] = "/C";
                    command_arr[2] = "start";
                    command_arr[3] = "cmd.exe";
                    command_arr[4] = "/C";
                    command_arr[5] = "shutdown /r /t 1 /f";
                    //command_arr[6] = "/r 1 /f";
                }

                System.out.println("Windows.Команда: ");
                for (String tmpStr : command_arr)
                {
                    System.out.print(tmpStr);
                }

                Process proc = null;
                try
                {
                    proc = Runtime.getRuntime().exec(command_arr);
                    proc.waitFor();
                    proc.destroy();
                    YALtools.readInputStream(proc.getErrorStream());
                }
                catch (IOException ioException)
                {
                    try
                    {
                        YALtools.printDebugMessage("Поток ошибок процессае:" + YALtools.readInputStream(proc.getInputStream()).toString());

                    }
                    catch (IOException ioExc)
                    {
                        System.exit(1);
                    }
                    YALtools.printDebugMessage("Ошибка ввода-вывода при выполнении команды под Windows: " + ioException.toString());
                    YALtools.printDebugMessage("================================");
                    ioException.printStackTrace();
                    YALtools.printDebugMessage("================================\n\n");
                }
                catch (InterruptedException interExc)
                {
                    YALtools.printDebugMessage(interExc.toString());
                }
            }
            YALtools.printDebugMessage("Выколнение коды Wind заверешно.");
        }


        if (linux)
        {
            YALtools.printDebugMessage("Текущая ОС: Linux");

            boolean isBash = false;

            Process proc = null;

            try
            {
                YALtools.printDebugMessage("Имя таймера: " + timerName_textFiels.getText());
                String[] commandNotify = {"notify-send", "-u", "critical", "Advanced TimerFX", "Timer '" + timerName_textFiels.getText() + "' went out."};
                String[] commandArray = null;
                //System.out.println(com);
                //Runtime.getRuntime().exec(com);

                proc = Runtime.getRuntime().exec(commandNotify);
                YALtools.printDebugMessage("Поток ошибок команды: " + YALtools.readInputStream(proc.getErrorStream()).toString());

                if (shutdown_radio.isSelected())
                {
                    commandArray = new String[3];
                    commandArray[0] = "bash";
                    commandArray[1] = "-c";
                    commandArray[2] = "systemctl poweroff";

                } else if (suspend_radio.isSelected())
                {
                    commandArray = new String[3];
                    commandArray[0] = "bash";
                    commandArray[1] = "-c";
                    commandArray[2] = "systemctl suspend";
                    //command_strBuilder.append(tmpParam);
                } else if (custom_command.isSelected())
                {
                    commandArray = new String[3];
                    commandArray[0] = "bash";
                    commandArray[1] = "-c";
                    commandArray[2] = customCommand_textField.getText();
                    //command_strBuilder.append(customCommand_textField.getText());
                } else if (reboot_radio.isSelected())
                {
                    commandArray = new String[3];
                    commandArray[0] = "bash";
                    commandArray[1] = "-c";
                    commandArray[2] = "systemctl reboot";
                    //commandArray[2] = "firefox mail.yahoo.com & audacious /media/yaroslav/Freedom/1.\\ Audio/1996\\ Pure\\ Instinct\\ [1996\\ Japan\\ AMCE-950\\ EW]/1996\\ -\\ Pure\\ Instinct\\ (AMCE-950)/10\\ -\\ You\\ And\\ I.flac & gnome-terminal";
                }

                if (performActionAfterTimerWentOut_checkBox.isSelected())
                {
                    System.out.println("Linux. Команда: '");

                    /*Каждый элемент массива - отдельная команда с параметром, следующими идут аргументы
                     * */
                    //String[] commandArray1 = {"bash", "-c" , "audacious /home/yaroslav/10-YouAndI.flac -H", "celluloid '/media/yaroslav/Freedom/2. Videos/Drayv.2011.x264.BDRip.1080p.mkv'"};
                    for (String tmpStr : commandArray)
                    {
                        System.out.print(tmpStr);
                    }
                    System.out.println("'\n");

                    //Runtime.getRuntime().exec(command_strBuilder.toString().split(" "));
                    String[] hren = {"bash", "-c", "firefox"};
                    //Runtime.getRuntime().exec(command_strBuilder.toString());

                    Process proc_1 = Runtime.getRuntime().exec(commandArray);
                    YALtools.readInputStream(proc_1.getErrorStream());
                    proc_1.waitFor();
                    proc_1.destroy();
                }

                proc.waitFor();
                proc.destroy();
                //proc.destroy();
            }
            catch (IOException ioExc)
            {
                YALtools.printDebugMessage("Возникла ошибка ввода-вывода при выполнении команды: \n" + ioExc.toString());
            }
            catch (InterruptedException iterExc)
            {
                YALtools.printDebugMessage(iterExc.toString());
                return;
            }
        }
    }

    private void startTimer_Action(ActionEvent event)
    {
        int timerHours = Integer.parseInt(hours_Spinner.getValue().toString());
        int timerMinutes = Integer.parseInt(minutes_Spinner.getValue().toString());
        int timerSeconds = Integer.parseInt(seconds_Spinner.getValue().toString());

        timerTimeInSeconds = (((timerMinutes + 1) * 60) - 60) + timerSeconds;//Минуты
        timerTimeInSeconds += (((timerHours + 1) * 3600) - 3600);//Добавляем часы
        System.out.println(timerHours + ":" + timerMinutes + ":" + timerSeconds);
        System.out.println("Время таймера в секундах: " + timerTimeInSeconds);

        long timerTime = timerTimeInSeconds * 1000;
        System.out.println("Время таймера в миллисекундах: " + timerTime);
        //timer_obj.schedule(timerTask_obj, timerTime);

        stopTimer_button.setDisable(false);
        stopTimer_MenuItem.setDisable(false);
        startTimer_button.setDisable(true);
        startTimer_MenuItem.setDisable(true);
        exit_menuItem.setDisable(true);

        //Собственно, наш основной таймер
        timer_obj = new Timeline(new KeyFrame(Duration.seconds(timerTimeInSeconds), ev ->
        {
            YALtools.printDebugMessage("MainTimer_TimeLine was ended.");
            timerWentOutAction(null);
            //superRoot.fireEvent(new RotateEvent(RotateEvent.ROTATE, 0, 0, 0, 0, true, true, true, true, true, true, 0, 0, null));
        }));
        timer_obj.play();


        //Инициализация новых компонентов для отображения информации о запущенном таймере
        VBox timerIsRunning_pane_superRoot = new VBox(menuBar);

        VBox timerIsRunning_pane = new VBox(15);

        timerTime_LocalTime = LocalTime.of(timerHours, timerMinutes, timerSeconds);

        hoursAppearance_Label = new Label(String.valueOf(timerHours));
        minutesAppearance_Label = new Label(String.valueOf(timerMinutes));
        secondsAppearance_Label = new Label(String.valueOf(timerSeconds));

        Font numbers_Font = null;

        try
        {
            URL numbersFont_URL = this.getClass().getClassLoader().getResource("Fonts/Jersey Sharp.ttf");
            numbers_Font = Font.loadFont(numbersFont_URL.openStream(), 36.0);
        }
        catch (IOException ioExc)
        {
            YALtools.printDebugMessage("Ошибка ввода-вывода при загрузке шрифта.\n" + ioExc.toString());
        }

        hoursAppearance_Label.setFont(numbers_Font);
        minutesAppearance_Label.setFont(numbers_Font);
        secondsAppearance_Label.setFont(numbers_Font);
        colon_Label.setFont(numbers_Font);
        colon_Label2.setFont(numbers_Font);

        HBox secondMinutesHoursAppearance_Box = new HBox(3, hoursAppearance_Label, colon_Label, minutesAppearance_Label, colon_Label2, secondsAppearance_Label);
        secondMinutesHoursAppearance_Box.setAlignment(Pos.CENTER);
        //secondMinutesHoursAppearance_Box.getChildren().add(colon_Label);

        timerIsRunning_pane_superRoot.getChildren().add(timerIsRunning_pane);
        timerIsRunning_pane.getChildren().add(secondMinutesHoursAppearance_Box);

        HBox stopTimer_Box = new HBox(stopTimer_button);
        stopTimer_Box.setAlignment(Pos.CENTER);

        Label timerInformation_Label = new Label(info_str);
        try
        {
            timerInformation_Label.setFont(YALtools.createFontFXFromResources("Fonts/Lettres_ombrees_ornees.otf", 26.0D));
        }
        catch (java.io.IOException ioExc)
        {
            YALtools.printDebugMessage("Ошибка ввода-вывода при загрузке файла шрифта.\n" + ioExc.toString());
        }

        HBox timerInformation_Box = new HBox();

        if (!timerName_textFiels.getText().equals("") || performActionAfterTimerWentOut_checkBox.isSelected())
        {
            timerInformation_Box.getChildren().add(timerInformation_Label);
            timerInformation_Box.setAlignment(Pos.CENTER);
        }


        timerIsRunning_pane.setPadding(new Insets(20));
        timerIsRunning_pane.getChildren().add(stopTimer_Box);
        timerIsRunning_pane.getChildren().add(new Separator());
        timerIsRunning_pane.getChildren().add(timerInformation_Box);

        Font fontForLabels = null;
        Font fontForLabelsValues = null;
        try
        {
            fontForLabels = YALtools.createFontFXFromResources("Fonts/Kingthings Petrock.ttf", 26.0D);
            fontForLabelsValues = YALtools.createFontFXFromResources("Fonts/Quicksand_Bold.ttf", 14.0D);
        }
        catch (IOException ioExc)
        {

        }

        GridPane timerInfoAppearance_GridPane = new GridPane();
        timerInfoAppearance_GridPane.setHgap(7);

        if (timerName_textFiels.getText() != null &&
                !timerName_textFiels.getText().equals(""))
        {
            timerName_Label = new Label(timerName_str);
            timerName_Label.setFont(fontForLabels);

            Label timerNameValue_Label = new Label(timerName_textFiels.getText());
            timerNameValue_Label.setFont(fontForLabelsValues);
            timerNameValue_Label.setWrapText(true);

            //Это делается для того, чтобы текст этого лейбла не был сокращен
            textToDefineWidth = new Text(timerName_Label.getText());
            textToDefineWidth.setFont(fontForLabels);
            YALtools.printDebugMessage("Ширина текста 'timerNameLabel:': " + textToDefineWidth.getBoundsInParent().getWidth());
            timerName_Label.setMinWidth(textToDefineWidth.getBoundsInParent().getWidth());
            //

            //HBox timerNameAppearance_Box = new HBox(10, timerName_Label, timerNameValue_Label);
            //timerNameAppearance_Box.setAlignment(Pos.CENTER_LEFT);
            //timerIsRunning_pane.getChildren().add(timerNameAppearance_Box);
            timerInfoAppearance_GridPane.addRow(timerInfoAppearance_GridPane.getRowConstraints().size(), timerName_Label, timerNameValue_Label);
        }

        if (action)
        {

            actionInfo_Label = new Label(action_str);
            actionInfo_Label.setFont(fontForLabels);

            //Это делается для того, чтобы текст этого лейбла не был сокращен
            textToDefineWidth = new Text(actionInfo_Label.getText());
            textToDefineWidth.setFont(fontForLabels);
            YALtools.printDebugMessage("Ширина текста 'Action:': " + textToDefineWidth.getBoundsInParent().getWidth());
            actionInfo_Label.setMinWidth(textToDefineWidth.getBoundsInParent().getWidth());
            //

            //actionInfo_Label.setMinWidth(250);
            Label actionValue_Label = new Label(actionDescription);
            actionValue_Label.setFont(fontForLabelsValues);
            actionValue_Label.setWrapText(true);

            timerInfoAppearance_GridPane.addRow(timerInfoAppearance_GridPane.getRowConstraints().size() + 1, actionInfo_Label, actionValue_Label);


            //YALtools.printDebugMessage(String.valueOf(timerInfoAppearance_GridPane.get));
            YALtools.printDebugMessage("actionInfo:getBoundsInParent: " + actionInfo_Label.getBoundsInParent().getWidth());
            YALtools.printDebugMessage("actionInfo:getLayoutBounds: " + actionInfo_Label.getLayoutBounds().getWidth());

//            HBox actionInfoAppearance_Box = new HBox(10, actionInfo_Label, actionValue_Label);
//            timerIsRunning_pane.getChildren().add(actionInfoAppearance_Box);
        }

        timerIsRunning_pane.getChildren().add(timerInfoAppearance_GridPane);

        superRoot.setVisible(false);
        timerIsRunning_pane_superRoot.setVisible(true);
        scene.setRoot(timerIsRunning_pane_superRoot);

        startTimerToUpdatingTimeAppearance();
    }

    private void startTimerToUpdatingTimeAppearance()
    {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev ->
        {
            YALtools.printDebugMessage("TimeLine");
            timerTime_LocalTime = timerTime_LocalTime.minusSeconds(1);
            hoursAppearance_Label.setText(String.valueOf(timerTime_LocalTime.getHour()));
            minutesAppearance_Label.setText(String.valueOf(timerTime_LocalTime.getMinute()));
            secondsAppearance_Label.setText(String.valueOf(timerTime_LocalTime.getSecond()));
        }));
        timeline.setCycleCount(timerTimeInSeconds);
        timeline.play();
    }

    private void stopTimerButton_Action(ActionEvent event)
    {
        timeline.stop();
        timer_obj.stop();
        YALtools.printDebugMessage("Нажата клавиша остановки таймера.");
        stopTimer_button.setDisable(true);
        stopTimer_MenuItem.setDisable(true);
        startTimer_button.setDisable(false);
        startTimer_MenuItem.setDisable(false);
        exit_menuItem.setDisable(false);

        //Сделано во избежание пропадания меню после смены корнувого узла
        menu_BorderPane.setTop(menuBar);

        YALtools.printDebugMessage("Кол-во узлов superRoot после остановки: " + superRoot.getChildren().size());

        scene.setRoot(superRoot);
        superRoot.setVisible(true);


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
            YALtools.printDebugMessage("Путь к файлу локализации: " + langNode_URL.toExternalForm());

            local.importPreferences(langNode_URL.openStream());


            startTimer_button.setText(local.get("startTimer_button", "Start"));
            stopTimer_button.setText(local.get("stopTimer_button", "Start"));
            startTimer_MenuItem.setText(local.get("startTimer_MenuItem", "Start"));
            stopTimer_MenuItem.setText(local.get("stopTimer_MenuItem", "Start"));
            performActionAfterTimerWentOut_checkBox.setText(local.get("performActionAfterTimerWentOut_checkBox", "Start"));
            shutdown_radio.setText(local.get("shutdown_radio", "Shut Down"));
            suspend_radio.setText(local.get("suspend_radio", "Suspend the System"));
            custom_command.setText(local.get("customCommand_Label", "Custom command"));
            reboot_radio.setText(local.get("reboot_radio", "Reboot"));
            timerName_textFiels.setPromptText(local.get("TimerName_PromtText", "Name of timer (optional)"));
            timerName_str = local.get("timerName_Label", "Name of timer:");
            action_str = local.get("timerAction_Label", "Action: ");
            info_str = local.get("info_text", "Info");
            gitHubRepository_MenuItem.setText(local.get("gitHubRepository_MenuItem", "Repository on GitHub"));
            customCommandPromtText_str = local.get("customCommandPromtText", "Enter here command that will be performed after timer went out");

            customCommand_textField.setPromptText(customCommandPromtText_str);

            if (timerName_Label != null)
            {
                timerName_Label.setText(local.get("timerName_Label", "Start"));
            }

            if (actionInfo_Label != null)
            {
                actionInfo_Label.setText(local.get("timerAction_Label", "Action:"));
            }


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
            YALtools.printDebugMessage("Возникла ошибка при импорте узла локализации.");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Возникла ошибка при загрузке файла локализации.", ButtonType.OK);
            return;
        }
        catch (InvalidPreferencesFormatException invPrefFormExc)
        {
            YALtools.printDebugMessage("Некорретный формат файла узла локализации.\n" + invPrefFormExc.toString());
            return;
        }
        catch (BackingStoreException invPrefFormExc)
        {
            YALtools.printDebugMessage("Возникла ошибка при удалении файла узла локализации.\n" + invPrefFormExc.toString());
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
            YALtools.printDebugMessage("Возникла ошибка при обнаружении каталога сохранения узла.\n" + fileNFExc.toString());
        }
        catch (BackingStoreException backStoreExc)
        {
            YALtools.printDebugMessage("Возникла ошиба при удалении узла настроек.\n" + backStoreExc.toString());
        }
        catch (IOException ioExc)
        {
            YALtools.printDebugMessage("Возникла ошибка ввода-вывода при сохранении узла настроек.\n" + ioExc.toString());
        }
        catch (Exception ioExc)
        {
            YALtools.printDebugMessage("Возникла ошибка ввода-вывода узла настроек.\n" + ioExc.toString());
        }
    }

    private void importSettings()
    {
        Preferences pref = Preferences.userRoot().node("YALdysse/AdvancedTimerFX/Settings");

        try
        {
            YALtools.printDebugMessage("Поиск узла настроек: " + YALtools.getJarLocation().getParent() + "/settings.pref");

            FileInputStream fis = new FileInputStream(new File(YALtools.getJarLocation().getParent() + "/settings.pref"));
            pref.importPreferences(fis);

            currentLanguage_str = pref.get("Language", "English");
            locX = pref.getInt("locX", 0);
            locY = pref.getInt("locY", 0);

            pref.clear();
            pref.parent().parent().removeNode();
        }
        catch (FileNotFoundException fileNFExc)
        {
            YALtools.printDebugMessage("Возникла ошибка при обнаружении каталога сохранения узла.\n" + fileNFExc.toString());
        }
        catch (BackingStoreException backStoreExc)
        {
            YALtools.printDebugMessage("Возникла ошиба при удалении узла настроек.\n" + backStoreExc.toString());
        }
        catch (IOException ioExc)
        {
            YALtools.printDebugMessage("Возникла ошибка ввода-вывода при загрузке узла настроек.\n" + ioExc.toString());
        }
        catch (Exception ioExc)
        {
            YALtools.printDebugMessage("Возникла ошибка ввода-вывода узла настроек.\n" + ioExc.toString());
        }
    }
}
