/*
 * Copyright (C) Yaroslav Lytvynov (aka YALdysse) 2021-2023 <Yaroslav_A_Litvinov@yahoo.com>
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

package org.yaldysse.atfx;

import javafx.beans.Observable;
import javafx.scene.effect.DropShadow;
import org.yaldysse.animation.FallingSnowflakes;
import org.yaldysse.animation.flowers.FlowersGrowing;
import org.yaldysse.atfx.action.Action;
import org.yaldysse.atfx.action.Command;
import org.yaldysse.atfx.process.Process;
import org.yaldysse.atfx.process.ProcessSelector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.yaldysse.atfx.action.linux.*;
import org.yaldysse.tools.notification.AnimationType;
import org.yaldysse.tools.notification.Notification;
import org.yaldysse.tools.notification.NotificationType;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

/**
 * Рекомендації
 * <br>
 * Відключення {@link Menu} не забороняє користуватись {@link MenuItem}, що входять до цього Menu
 * за допомогою горячих клавіш.
 * <p>
 * CSS Warning генеруються, коли MenuBar додається до якогось layout.
 */
public class ATimerFX_gui extends Application
{
    private TextField timerName_textFiels;
    private Spinner<Integer> hours_Spinner;
    private Spinner<Integer> minutes_Spinner;
    private Spinner<Integer> seconds_Spinner;
    private Text hours_text;
    private Text minutes_text;
    private Text seconds_text;
    private Label timerType_Label;
    private Button startTimer_button;
    private CheckBox performActionAfterTimerWentOut_checkBox;
    private ArrayList<ActionRadioButton> radioButtons;
    private ArrayList<Pane> radioButtonsBoxes;
    private VBox radioGroup_Box;
    private TextField customCommand_textField;
    private Timeline timer_obj;
    private VBox root;
    private Pane timerIsRunning_pane;
    private Label hoursAppearance_Label;
    private Label minutesAppearance_Label;
    private Label secondsAppearance_Label;
    private Label timerName_Label;
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
    private MenuItem createTimeTemplate_MenuItem;
    private Menu timer_Menu;
    private MenuItem startTimer_MenuItem;
    private MenuItem stopTimer_MenuItem;
    private MenuItem exit_menuItem;
    private CheckMenuItem playAnimation_MenuItem;
    private CheckBox delayBeforeAction_CheckBox;
    private Spinner<Integer> delayBeforeAction_Spinner;
    private int timerTimeInSeconds;
    private String actionDescription = null;
    private VBox superRoot;
    private HBox delayCustomMenuItem_HBox;
    private LocalTime timerTime_LocalTime;
    Timeline timeline = null;
    private Scene scene;
    private Stage stage;
    public static final String NAME_OF_PROGRAM = "Advanced TimerFX";
    public static final String PROPERTY_FILE_NAME = "Advanced_TimerFX.properties";
    public final String TIMER_TEMPLATES_FILE_NAME = "ATfx_TimerTemplates.templates";
    private String timerName_str;
    private String action_str;
    private String info_str;
    private ArrayList<String> actionDescriptionStrings;
    private String customCommandPromtText_str;
    private String doYouWantToContinue_str;
    private String warning_str;
    private String popup_str;
    private String theTimerHasExpired_str;
    private String inTimerType_str;
    private String atTimerType_str;
    private String startButton_str;
    private String notify_str;
    private String timerTemplateCreated_str;
    private String allTimerTemplatesRemoved_str;
    private String timerTemplateApplied_str;
    private Label actionInfo_Label;
    private Label actionValue_Label;
    private String needRootPrivilege_str;
    private int locX;
    private int locY;
    private static String currentLanguage_str;
    private GridPane gp;
    private static final int PREFERRED_WIDTH = (int) (fxGui.rem * 18.7D);
    private static final int PREFERRED_HEIGHT = (int) (fxGui.rem * 15.6D);
    private Popup popup;
    private Notification notify;
    private Thread initializingTimerIsRunningComponent_Thread;
    private ArrayList<TimerTemplate> timerTemplates;
    private Menu timeTemplates_Menu;
    private MenuItem removeAllTimeTemplates_MenuItem;
    private ArrayList<TemplateMenuItem> timeTemplate_MenuItemsArray;
    private VBox timerIsRunning_pane_superRoot;
    private Font numbers_Font;
    private Label timerInformation_Label;
    private Font fontForLabels;
    private Font fontForLabelsValues;
    private Label timerNameValue_Label;
    private Button brightnessValue;
    private byte brightness;
    public final double ICON_SIZE = 24.0D;
    private Image info_Image;
    private Image applicationIcon_Image;
    private Image deleteTemplate_Image;
    private Image deleteUnActive_Image;
    private byte brightnessStep;
    private ToggleGroup actionRadioButtonToggleGroup;
    private VBox timerNameInfo_Pane;
    private VBox timerActionInfo_Pane;
    private Button openProcessSelector_Button;
    private Process processToKill;
    private CheckBox killProcessWithChildren_CheckBox;
    private FallingSnowflakes fallingSnowflakes;
    private FlowersGrowing flowersGrowing;
    private Background whiteSmokeBackground;
    private Background wingterBackground;
    private Background springBackground;
    private HBox timerInformation_Box;
    private Border brightnessFocusedBorder;
    private DropShadow brighnessDropShadow;
    private CustomMenuItem loopTimer_CustomMenuItem;
    private CheckBox loopTimer_CheckBox;
    private boolean stoppedByUser;

    public void start(Stage aStage)
    {
        long startTime = System.currentTimeMillis();

        System.out.println(System.getProperty("os.name"));

        Thread initializeMenu_Thread = new Thread(this::initializeMenu, "Initializing Menu");
        initializeMenu_Thread.start();

        initializeComponents();

        scene = new Scene(superRoot);

        stage = aStage;
        stage.getIcons().add(applicationIcon_Image);
        stage.setOnCloseRequest(event -> exit());

        importSettings();

        /*Использование вместе с многократным вызовом метода scene.getWindow().sizeToScene();
         * приводит к появлению артефактов. Исправляется установкой ограничения
         * на минимальную ширину корневого узла сцены таким образом, чтобы корневой узел
         * был больше, чем такое же ограничение на stage.*/
        stage.setX(locX);
        stage.setY(locY);
        stage.setScene(scene);
        stage.setTitle(NAME_OF_PROGRAM + " [build 47 Stable]");
        stage.setMinWidth(PREFERRED_WIDTH + fxGui.rem * 0.1D);
        stage.setMaxWidth(PREFERRED_WIDTH + fxGui.rem * 0.5D);
        stage.setMinHeight(PREFERRED_HEIGHT - fxGui.rem * 1.1D);

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

        //Увага: Викликає проблеми з css!!!=======================================
        //Будь-який menubar викликає помилки пов'язані з css, якщо його додавати в BorderPane
        //Як виявилось, воно генерується також і при  додаванні в vbox на перше місце.
//        menu_BorderPane.setTop(new MenuBar(new Menu("a"),
//                new Menu("b")));
        //superRoot.getChildren().add(0,new Label());

        initLocalization(currentLanguage_str);

        superRoot.getChildren().add(0, menuBar);

        //stage.show();
        //stage.hide();
        stage.setHeight(fxGui.rem * 18.9D);
        stage.show();

        System.out.println("Времени прошло: " + (System.currentTimeMillis() - startTime));

        initializingTimerIsRunningComponent_Thread = new Thread(this::initializeTimerIsRunningComponents);
        initializingTimerIsRunningComponent_Thread.start();
    }

    private void initializeFlowersGrowingAnimation()
    {
        //superRoot.setBackground(Background.EMPTY);

        Pane pane = new Pane();
        pane.setBackground(Background.EMPTY);
        //створення контейнеру
        StackPane sp = new StackPane();
        sp.getChildren().addAll(pane, timerIsRunning_pane);
        //sp.getChildren().add(superRoot);
        sp.setPickOnBounds(true);
        sp.setBackground(Background.EMPTY);

        timerIsRunning_pane_superRoot.getChildren().add(1, sp);
        VBox.setVgrow(sp, Priority.ALWAYS);
        //scene.setRoot(sp);
        stage.hide();
        stage.show();


        flowersGrowing = new FlowersGrowing(pane);
        //flowersGrowing.setObjectsXOffset(60.0D);
    }

    private void initializeFallingSnowflakesAnimation()
    {
        //superRoot.setBackground(Background.EMPTY);

        Pane pane = new Pane();
        pane.setBackground(Background.EMPTY);
        //створення контейнеру
        StackPane sp = new StackPane();
        sp.getChildren().addAll(pane, timerIsRunning_pane);
        //sp.getChildren().add(superRoot);
        sp.setPickOnBounds(true);
        sp.setBackground(Background.EMPTY);

        timerIsRunning_pane_superRoot.getChildren().add(1, sp);
        VBox.setVgrow(sp, Priority.ALWAYS);
        //scene.setRoot(sp);
        stage.hide();
        stage.show();


        fallingSnowflakes = new FallingSnowflakes(pane, 8, 12, 24);
    }

    /**
     * Производит создание элементов меню: указывает имена, иконки, реализацию обработчиков.
     * Полностью автономный метод.
     */
    private void initializeMenu()
    {
        System.out.println(Thread.currentThread().getName());
        //--------------------------------- Меню
        menuBar = new MenuBar();
        general_menu = new Menu();
        language_Menu = new Menu();
        timer_Menu = new Menu();
        timerType_Menu = new Menu();
        timeTemplates_Menu = new Menu();

        timeTemplate_MenuItemsArray = new ArrayList<>();

        Image russianFlag_Image = new Image(getClass().getResourceAsStream("/Images/russia.png"));
        Image ukraineFlag_Image = new Image(getClass().getResourceAsStream("/Images/ukraine_2.png"));
        Image unitedKingdom_Image = new Image(getClass().getResourceAsStream("/Images/UK.png"));
        Image exit_Image = new Image(getClass().getResourceAsStream("/Images/exit.png"));
        Image gitHub_Image = new Image(getClass().getResourceAsStream("/Images/gitHub.png"));
        Image language_Image = new Image(getClass().getResourceAsStream("/Images/language_2.png"));
        Image startTimer_Image = new Image(getClass().getResourceAsStream("/Images/startTimer_3.png"));
        Image stopTimer_Image = new Image(getClass().getResourceAsStream("/Images/stopTimer_3.png"));
        Image delay_Image = new Image(getClass().getResourceAsStream("/Images/delay_1.png"));
        Image timerType_Image = new Image(getClass().getResourceAsStream("/Images/timerType.png"));
        Image createTemplate_Image = new Image(getClass().getResourceAsStream("/Images/plus.png"));
        Image removeAllTimeTemplates_Image = new Image(this.getClass().getResourceAsStream("/Images/eraser.png"));
        info_Image = new Image(this.getClass().getResourceAsStream("/Images/info.png"));
        deleteTemplate_Image = new Image(this.getClass().getResourceAsStream("/Images/delete_active.png"));
        deleteUnActive_Image = new Image(this.getClass().getResourceAsStream("/Images/delete_unActive.png"));
        Image animation_Image = new Image(this.getClass().getResourceAsStream("/Images/Animation.png"));
        Image loop_Image = new Image(getClass().getResourceAsStream("/Images/Loop.png"));


        startTimer_MenuItem = new MenuItem();
        startTimer_MenuItem.setGraphic(fxGui.createImageView(startTimer_Image, ICON_SIZE));
        startTimer_MenuItem.setOnAction(this::startTimer_Action);
        startTimer_MenuItem.acceleratorProperty().set(new KeyCodeCombination(KeyCode.ENTER, KeyCodeCombination.CONTROL_DOWN));
        //startTimer_MenuItem.setDisable(true);

        stopTimer_MenuItem = new MenuItem();
        stopTimer_MenuItem.setGraphic(fxGui.createImageView(stopTimer_Image, ICON_SIZE));
        stopTimer_MenuItem.setOnAction(this::stopTimerButton_Action);
        stopTimer_MenuItem.acceleratorProperty().set(new KeyCodeCombination(KeyCode.SPACE, KeyCodeCombination.CONTROL_DOWN));
        stopTimer_MenuItem.setDisable(true);

        //Нужен для того, чтобы благополучно разместить в одном пункте меню такие
        //элементы как CheckBox, Spinner и Label
        delayCustomMenuItem_HBox = new HBox(fxGui.rem * 0.6D);

        CustomMenuItem delay_CustomMenuItem = new CustomMenuItem(delayCustomMenuItem_HBox);
        //delay_CustomMenuItem.setGraphic(delay_ImageView);

        delayBeforeAction_CheckBox = new CheckBox();
        delayBeforeAction_CheckBox.setTextFill(Color.BLACK);
        delayBeforeAction_CheckBox.setGraphic(fxGui.createImageView(delay_Image, ICON_SIZE));
        delayBeforeAction_CheckBox.setSelected(true);
        delayBeforeAction_CheckBox.selectedProperty().addListener(event ->
        {
            if (delayBeforeAction_CheckBox.isSelected())
            {
                YALtools.printDebugMessage("Задержка включена");
                delayCustomMenuItem_HBox.getChildren().add(delayBeforeAction_Spinner);
            }
            else
            {
                delayCustomMenuItem_HBox.getChildren().remove(delayBeforeAction_Spinner);
            }
            saveSettings();
        });

        delayBeforeAction_Spinner = new Spinner<>(1, 59, 15);
        delayBeforeAction_Spinner.setOnScroll(event ->
        {
            YALtools.printDebugMessage("getDeltaY: " + event.getDeltaY());

            if (event.getDeltaY() > 10)
            {
                delayBeforeAction_Spinner.increment();
            }
            else if (event.getDeltaY() < 0)
            {
                delayBeforeAction_Spinner.decrement();
            }
        });

        delayBeforeAction_Spinner.setMinWidth(fxGui.rem * 1.2D);
        delayBeforeAction_Spinner.setPrefWidth(fxGui.rem * 4.0D);

        delayCustomMenuItem_HBox.getChildren().addAll(delayBeforeAction_CheckBox, delayBeforeAction_Spinner);

        exit_menuItem = new MenuItem();
        exit_menuItem.setGraphic(fxGui.createImageView(exit_Image, ICON_SIZE));
        exit_menuItem.acceleratorProperty().set(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        exit_menuItem.setOnAction(event -> exit());

        gitHubRepository_MenuItem = new MenuItem();
        gitHubRepository_MenuItem.setGraphic(fxGui.createImageView(gitHub_Image, ICON_SIZE));
        gitHubRepository_MenuItem.setOnAction(event ->
                getHostServices().showDocument("https://github.com/YALdysse/Advanced_TimerFX"));

        russianLanguage_MenuItem = new MenuItem("Русский",
                fxGui.createImageView(russianFlag_Image, ICON_SIZE));
        russianLanguage_MenuItem.setOnAction(event ->
                initLocalization("Russian"));
        ukrainianLanguage_MenuItem = new MenuItem("Українська",
                fxGui.createImageView(ukraineFlag_Image, ICON_SIZE));
        ukrainianLanguage_MenuItem.setOnAction(event ->
                initLocalization("Ukrainian"));

        englishLanguage_MenuItem = new MenuItem("English",
                fxGui.createImageView(unitedKingdom_Image, ICON_SIZE));
        englishLanguage_MenuItem.setOnAction(event ->
                initLocalization("English"));

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

        createTimeTemplate_MenuItem = new MenuItem();
        createTimeTemplate_MenuItem.setOnAction(this::createTimeTemplate_Action);
        createTimeTemplate_MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        createTimeTemplate_MenuItem.setGraphic(fxGui.createImageView(createTemplate_Image,
                ICON_SIZE));

        removeAllTimeTemplates_MenuItem = new MenuItem();
        removeAllTimeTemplates_MenuItem.setOnAction(this::removeAllTimeTemplates_Action);
        removeAllTimeTemplates_MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.J, KeyCombination.CONTROL_DOWN));
        removeAllTimeTemplates_MenuItem.setGraphic(fxGui.createImageView(removeAllTimeTemplates_Image, ICON_SIZE));

        playAnimation_MenuItem = new CheckMenuItem();
        playAnimation_MenuItem.setSelected(true);
        playAnimation_MenuItem.setGraphic(fxGui.createImageView(animation_Image,
                ICON_SIZE));

        loopTimer_CheckBox = new CheckBox();
        loopTimer_CheckBox.setGraphic(fxGui.createImageView(loop_Image, ICON_SIZE));
        loopTimer_CheckBox.setTextFill(Color.DODGERBLUE);
        loopTimer_CheckBox.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD,
                13.0D));
        loopTimer_CheckBox.setGraphic(fxGui.createImageView(loop_Image, ICON_SIZE));

        Tooltip timerLoop_ToolTip = new Tooltip();
        timerLoop_ToolTip.setShowDelay(Duration.millis(600));
        timerLoop_ToolTip.setMaxWidth(PREFERRED_WIDTH);
        timerLoop_ToolTip.setWrapText(true);

        loopTimer_CheckBox.setTooltip(timerLoop_ToolTip);

        loopTimer_CustomMenuItem = new CustomMenuItem(loopTimer_CheckBox);

        language_Menu.setGraphic(fxGui.createImageView(language_Image, ICON_SIZE));
        timerType_Menu.setGraphic(fxGui.createImageView(timerType_Image, ICON_SIZE));
        timerType_Menu.getItems().addAll(countdownTimer_MenuItem, specifiedTimeTimer_MenuItem);
        language_Menu.getItems().addAll(russianLanguage_MenuItem, ukrainianLanguage_MenuItem, englishLanguage_MenuItem);
        general_menu.getItems().addAll(playAnimation_MenuItem, language_Menu, gitHubRepository_MenuItem, exit_menuItem);
        timer_Menu.getItems().addAll(timerType_Menu, new SeparatorMenuItem(), startTimer_MenuItem, stopTimer_MenuItem,
                new SeparatorMenuItem(), delay_CustomMenuItem, loopTimer_CustomMenuItem);
        timeTemplates_Menu.getItems().addAll(createTimeTemplate_MenuItem, removeAllTimeTemplates_MenuItem, new SeparatorMenuItem());
        menuBar.getMenus().addAll(general_menu, timer_Menu, timeTemplates_Menu);
    }

    private void initializeComponents()
    {
        timerTemplates = new ArrayList<>();
        radioButtons = new ArrayList<>();
        radioButtonsBoxes = new ArrayList<>();
        actionDescriptionStrings = new ArrayList<>();
        brightnessStep = 1;
        currentLanguage_str = "English";

        final double TIME_TEXT_OPACITY = 0.55D;
        hours_text = new Text();
        hours_text.setOpacity(TIME_TEXT_OPACITY);
        minutes_text = new Text();
        minutes_text.setOpacity(TIME_TEXT_OPACITY);
        seconds_text = new Text();
        seconds_text.setOpacity(TIME_TEXT_OPACITY);

        wingterBackground = new Background(new BackgroundFill(Color.rgb(234, 246, 251),
                CornerRadii.EMPTY, Insets.EMPTY));
        whiteSmokeBackground = new Background(new BackgroundFill(Color.WHITESMOKE,
                CornerRadii.EMPTY, Insets.EMPTY));
        springBackground = new Background(new BackgroundFill(Color.web("C6ECEA"),
                CornerRadii.EMPTY, Insets.EMPTY));

        initializeTimerConfigurationControls();

        try
        {
            applicationIcon_Image = new Image(this.getClass().getClassLoader().getResource("Images/timer_1.png").openStream());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }

        rootPrivilege_ToolTip = new Tooltip();
        rootPrivilege_ToolTip.setShowDelay(Duration.millis(200));

        if(System.getProperty("os.name").contains("Linux"))
        {
            checkingBrightnessSettingAvailableLinux();
        }
        //=========================================

        YALtools.printDebugMessage("superRoot-getBoundsInParent " + superRoot.getBoundsInParent().getHeight());
        YALtools.printDebugMessage("superRoot-getBoundsInLocal " + superRoot.getBoundsInLocal().getHeight());
        YALtools.printDebugMessage("gp: " + gp.getLayoutBounds().getHeight());
        YALtools.printDebugMessage("PREFERRED_HEIGHT: " + PREFERRED_HEIGHT);
    }

    /**
     * Ініціалізує компоненти графічного інтерфейсу, що відносяться до першої 'сцени'
     * з налаштуванням часу та дії таймеру.
     */
    private void initializeTimerConfigurationControls()
    {
        initializingSpinnersControls();
        initializingActionRadioButtons();
        initializingRadioButtonsBoxes();

        customCommand_textField = new TextField();
        customCommand_textField.setPromptText(customCommandPromtText_str);

        timerName_textFiels = new TextField();

        HBox timerName_Box = new HBox(fxGui.rem * 0.1, timerName_textFiels);
        timerName_Box.setAlignment(Pos.CENTER);
        HBox.setHgrow(timerName_textFiels, Priority.ALWAYS);

        timerType_Label = new Label();
        timerType_Label.setFont(Font.font(timerType_Label.getFont().getName(), FontPosture.ITALIC,
                timerType_Label.getFont().getSize() - 2.0D));
        HBox timerType_HBox = new HBox(timerType_Label);
        timerType_HBox.setAlignment(Pos.CENTER);

        brightness = 50;
        brightnessValue = new Button("" + brightness);
        brightnessValue.setContentDisplay(ContentDisplay.TEXT_ONLY);
        brightnessValue.setOpacity(0.9D);
        brightnessValue.setOnScroll(this::brightnessScroll_Action);
        brightnessValue.setBackground(new Background(new BackgroundFill(
                Color.rgb(173, 255, 47, (double) brightness / 100.0D),
                new CornerRadii(fxGui.rem * 0.5D), Insets.EMPTY)));
        brightnessValue.setPadding(new Insets(fxGui.rem * 0.4D));
        brightnessValue.setFont(Font.loadFont(getClass().getResourceAsStream(
                "/Fonts/arcadeclassic.ttf"), 16.0D));
        brightnessValue.setMinWidth(fxGui.calculateTextWidth(brightnessValue.getFont(),
                "512=="));
        brightnessValue.setAlignment(Pos.CENTER);
        brightnessValue.setOnKeyPressed(this::brightness_KeyPressed);
        brightnessValue.focusedProperty().addListener(this::brightness_focusedProperty);

        brightnessFocusedBorder = new Border(new BorderStroke(
                Color.DEEPSKYBLUE,
                BorderStrokeStyle.SOLID,
                brightnessValue.getBackground().getFills().get(0).getRadii(),
                BorderStroke.THIN));
        brighnessDropShadow = new DropShadow(fxGui.rem * 1.2D, Color.DEEPSKYBLUE);

        Image search_Image = new Image(getClass().getResourceAsStream("/Images/Search.png"));

        openProcessSelector_Button = new Button();
        openProcessSelector_Button.setGraphic(fxGui.createImageView(search_Image, 20.0D));
        openProcessSelector_Button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        openProcessSelector_Button.setOnAction(this::openProcessSelector_Action);

        killProcessWithChildren_CheckBox = new CheckBox();
        killProcessWithChildren_CheckBox.setPadding(new Insets(0.0D, 0.0D,
                0.0D, fxGui.rem * 1.2D));

        radioGroup_Box = new VBox(fxGui.rem * 0.6D);
        radioGroup_Box.setPadding(new Insets(0.0D, 0.0D,
                fxGui.rem * 0.3D, fxGui.rem * 1.2D));
        for (Pane radioHBox : radioButtonsBoxes)
        {
            radioGroup_Box.getChildren().add(radioHBox);
        }

        actionRadioButtonToggleGroup = new ToggleGroup();
        for (RadioButton radioButton : radioButtons)
        {
            actionRadioButtonToggleGroup.getToggles().add(radioButton);
        }

        startTimer_button = new Button();
        startTimer_button.setPrefWidth(PREFERRED_WIDTH * 0.6D);
        startTimer_button.setOnAction(this::startTimer_Action);

        HBox startTimer_Box = new HBox(fxGui.rem * 1.1D, startTimer_button);
        startTimer_Box.setAlignment(Pos.CENTER);


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
        //gp.setGridLinesVisible(true);
        gp.setHgap(fxGui.rem * 1.1);

        performActionAfterTimerWentOut_checkBox = new CheckBox();
        performActionAfterTimerWentOut_checkBox.setWrapText(true);
        //performActionAfterTimerWentOut_checkBox.setBackground(new Background(bImage));
        performActionAfterTimerWentOut_checkBox.setOnAction(this::performActionAfterTimerWentOut_CheckBox_Action);


        root = new VBox(fxGui.rem * 1.05D, timerName_Box, gp);
        //root.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        root.setPadding(new Insets(fxGui.rem * 1.05D));
        root.getChildren().addAll(startTimer_Box, new Separator(), performActionAfterTimerWentOut_checkBox);
        root.setOnRotate(event -> timerWentOutAction());

        superRoot = new VBox(root);
        superRoot.setMaxWidth(PREFERRED_WIDTH + fxGui.rem * 0.7D);
    }

    private void openProcessSelector_Action(ActionEvent actionEvent)
    {
        try
        {
            long millis = System.currentTimeMillis();
            ProcessList pl = new ProcessList();
            System.out.println("Часу на ProcessList: " + (System.currentTimeMillis() - millis));

            millis = System.currentTimeMillis();
            ProcessSelector ps = new ProcessSelector();
            ps.setProcessesList(pl.getProcesses());
            System.out.println("Часу на ProcessSelector: " + (System.currentTimeMillis() - millis));

            ps.showAndWait();
            processToKill = ps.getSelectedProcess();
            if (processToKill == null)
            {
                startTimer_MenuItem.setDisable(true);
                startTimer_button.setDisable(true);
            }
            else
            {
                System.out.println("Selected: " + processToKill.getPid() + "\t" + processToKill.getShortCommand());
                startTimer_MenuItem.setDisable(false);
                startTimer_button.setDisable(false);
            }
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    private void initializingSpinnersControls()
    {
        hours_Spinner = new Spinner<>(-1, 23, 0);
        //hours_Spinner.setMaxWidth(10);
        hours_Spinner.setMaxWidth(70);
        hours_Spinner.setOnScroll(event -> spinnersScrollAction(event,
                hours_Spinner, 0, 23));
        hours_Spinner.valueProperty().addListener(event -> spinnersValueListener(hours_Spinner,
                0, 23, null, null));
        hours_Spinner.setEditable(true);
        hours_Spinner.getEditor().textProperty().addListener((event, oldValue, newValue) ->
                timeSpinnerVerification(oldValue, newValue, 0, 23, hours_Spinner));

        minutes_Spinner = new Spinner<>(-1, 60, 0);
        minutes_Spinner.setMaxWidth(70);
        minutes_Spinner.setOnScroll(event -> spinnersScrollAction(event,
                minutes_Spinner, 0, 59));
        minutes_Spinner.valueProperty().addListener(event -> spinnersValueListener(minutes_Spinner,
                0, 59, hours_Spinner, hours_Spinner));
        minutes_Spinner.setEditable(true);
        minutes_Spinner.getEditor().textProperty().addListener((event, oldValue, newValue) ->
                timeSpinnerVerification(oldValue, newValue, 0, 59, minutes_Spinner));

        seconds_Spinner = new Spinner<>(-1, 60, 1);
        seconds_Spinner.setMaxWidth(70);
        seconds_Spinner.setOnScroll(event -> spinnersScrollAction(event,
                seconds_Spinner, 0, 59));
        seconds_Spinner.getValueFactory().valueProperty().addListener(event -> spinnersValueListener(
                seconds_Spinner, 0, 59, minutes_Spinner, minutes_Spinner));
        seconds_Spinner.setEditable(true);
        seconds_Spinner.getEditor().textProperty().addListener((event, oldValue, newValue) ->
                timeSpinnerVerification(oldValue, newValue, 0, 59, seconds_Spinner));
    }

    private void spinnersScrollAction(ScrollEvent event, final Spinner<Integer> spinner, final int minValue,
                                      final int maxValue)
    {
        YALtools.printDebugMessage("getDeltaY: " + event.getDeltaY());

        if (event.getDeltaY() > 10.0D)
        {
            spinner.increment();
        }
        else if (event.getDeltaY() < 0.0D)
        {
            spinner.decrement();
        }
    }

    /**
     * Обробник подій зміни значення для Spinner часу.
     *
     * @param increaseValueSpinnerWhenMax Якщо при досягненні максимального значення
     *                                    необхідно збільшити значення якогось Spinner,
     *                                    то його необхідно передати.
     * @param decreaseValueSpinnerWhenMin Якщо при досягненні мінімального значення
     *                                    необхідно зменшити значення якогось Spinner,
     *                                    то його необхідно передати.
     */
    private void spinnersValueListener(final Spinner<Integer> spinner, final int minValue,
                                       final int maxValue, final Spinner<Integer> increaseValueSpinnerWhenMax,
                                       final Spinner<Integer> decreaseValueSpinnerWhenMin)
    {
        if (spinner.getValue() < minValue)
        {
            spinner.getValueFactory().setValue(maxValue);

            if (decreaseValueSpinnerWhenMin != null)
            {
                decreaseValueSpinnerWhenMin.decrement();
            }
        }
        if (spinner.getValue() > maxValue)
        {
            spinner.getValueFactory().setValue(minValue);

            if (increaseValueSpinnerWhenMax != null)
            {
                increaseValueSpinnerWhenMax.increment();
            }
        }
    }

    private void brightnessScroll_Action(ScrollEvent event)
    {
        if (event.getDeltaY() > 10.0D && brightness < 100)
        {
            brightness += brightnessStep;
        }
        else if (event.getDeltaY() < -10.0D && brightness > 0)
        {
            brightness -= brightnessStep;
        }

        changingBrightnessButtonViewByBrightnessValue();

//        if(brightness > 50)
//        {
//            brightnessValue_Label.setTextFill(Color.LIGHTCORAL);
//        }
//        else
//        {
//            brightnessValue_Label.setTextFill(Color.GRAY);
//        }
    }

    private void initializingActionRadioButtons()
    {
        ActionRadioButton shutdown_radio = new ActionRadioButton(TimerAction.SHUTDOWN);
        shutdown_radio.setOnAction(this::actionRadioButton_Action);

        ActionRadioButton suspend_radio = new ActionRadioButton(TimerAction.SUSPEND);
        suspend_radio.setOnAction(this::actionRadioButton_Action);

        ActionRadioButton custom_command = new ActionRadioButton(TimerAction.CUSTOM_COMMAND);
        custom_command.setOnAction(event ->
        {
            actionRadioButton_Action(new ActionEvent());
            radioGroup_Box.getChildren().add(radioGroup_Box.getChildren().indexOf(
                            findRadioButtonContainerByRadioButtonAction(TimerAction.CUSTOM_COMMAND)
                    ) + 1,
                    customCommand_textField);
            stage.sizeToScene();
        });

        ActionRadioButton reboot_radio = new ActionRadioButton(TimerAction.REBOOT);
        reboot_radio.setOnAction(this::actionRadioButton_Action);

        ActionRadioButton killProcess_RadioButton = new ActionRadioButton(TimerAction.KILL_PROCESS_BY_PID);
        killProcess_RadioButton.setOnAction(event ->
        {
            actionRadioButton_Action(new ActionEvent());
            findRadioButtonContainerByRadioButtonAction(TimerAction.KILL_PROCESS_BY_PID)
                    .getChildren().addAll(openProcessSelector_Button,
                            killProcessWithChildren_CheckBox);

            startTimer_button.setDisable(true);
            startTimer_MenuItem.setDisable(true);
            stage.sizeToScene();
        });

        ActionRadioButton logOut_RadioButton = new ActionRadioButton(TimerAction.LOGOUT);
        logOut_RadioButton.setOnAction(this::actionRadioButton_Action);

        ActionRadioButton brightness_RadioButton = new ActionRadioButton(TimerAction.BRIGHTNESS);
        brightness_RadioButton.setOnAction(event ->
        {
            actionRadioButton_Action(new ActionEvent());

            findRadioButtonContainerByRadioButtonAction(TimerAction.BRIGHTNESS).getChildren()
                    .add(brightnessValue);
            stage.sizeToScene();
        });

        radioButtons.add(shutdown_radio);
        radioButtons.add(suspend_radio);
        radioButtons.add(reboot_radio);
        radioButtons.add(logOut_RadioButton);
        radioButtons.add(custom_command);
        radioButtons.add(killProcess_RadioButton);
        radioButtons.add(brightness_RadioButton);
    }

    /**
     * Ініціалізує об'єкти компонуваня для кожного з RadioButton. Тому
     * він повинен бути виклаканий після {@link #initializingActionRadioButtons()}
     */
    private void initializingRadioButtonsBoxes()
    {
        HBox hBox;

        for (int k = 0; k < radioButtons.size(); k++)
        {
            hBox = new HBox(fxGui.rem * 0.7D);
            hBox.getChildren().add(radioButtons.get(k));
            hBox.setAlignment(Pos.CENTER_LEFT);

            if (radioButtons.get(k).getAction() == TimerAction.KILL_PROCESS_BY_PID)
            {
                FlowPane fp = new FlowPane(fxGui.rem * 0.6D, fxGui.rem * 0.3D);
                fp.setAlignment(Pos.CENTER_LEFT);
                //fp.setPadding(new Insets(0.0D, 0.0D, 0.0D, fxGui.rem * 0.4D));
                fp.getChildren().add(radioButtons.get(k));
                radioButtonsBoxes.add(fp);
                continue;
            }

            radioButtonsBoxes.add(hBox);
        }
    }

    private void performActionAfterTimerWentOut_CheckBox_Action(ActionEvent event)
    {
        if (performActionAfterTimerWentOut_checkBox.isSelected())
        {
            if (!root.getChildren().contains(radioGroup_Box))
            {
                root.getChildren().add(radioGroup_Box);
            }

            YALtools.printDebugMessage("getLayoutBounds:" + root.getLayoutBounds().getHeight());
            YALtools.printDebugMessage("getBoundsInParent:" + root.getBoundsInParent().getHeight());
            YALtools.printDebugMessage("getBoundsInLocal:" + root.getBoundsInLocal().getHeight());
            YALtools.printDebugMessage("AllPaddings:" + (15 * root.getChildren().size()));

            if (actionRadioButtonToggleGroup.getSelectedToggle() == null)
            {
                startTimer_MenuItem.setDisable(true);
                startTimer_button.setDisable(true);
            }
            else
            {
                startTimer_MenuItem.setDisable(false);
                startTimer_button.setDisable(false);
            }
        }
        else
        {
            root.getChildren().remove(radioGroup_Box);

            startTimer_MenuItem.setDisable(false);
            startTimer_button.setDisable(false);
        }
        System.out.println("Height before: " + scene.getWindow().getHeight());
        scene.getWindow().sizeToScene();
        System.out.println("Height after: " + scene.getWindow().getHeight());
        //stage.setWidth(PREFERRED_WIDTH);
    }

    /**
     * Вмикає або вимикає кнопки старту та зупинки таймеру в
     * тому випадку, коли жоден з RadioButton не обрано.
     * true - вимикає
     */
    private void enableOrDisableButtonsIfRadioButtonsNotSelected(final boolean value)
    {
        boolean oneSelected = false;

        for (RadioButton radioButton : radioButtons)
        {
            if (radioButton.isSelected())
            {
                oneSelected = true;
            }
        }

        if (!oneSelected)
        {
            startTimer_button.setDisable(value);
            startTimer_MenuItem.setDisable(value);
            stopTimer_MenuItem.setDisable(value);
        }
    }

    private void timerWentOutAction()
    {
        stage.setIconified(false);
        //stage.setAlwaysOnTop(true);

        String message;
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
                createAndShowTimerDelayAlert();
            }
            else
            {
                stage.setOpacity(1.0D);
                stage.setAlwaysOnTop(false);
                performAction();
            }
        }

        stoppingTimer();
        checkKillActionReusing();
    }

    private void checkKillActionReusing()
    {
        ActionRadioButton selected = findSelectedActionRadioButtonUsingToggleGroup();
        if (selected != null &&
                selected.getAction() == TimerAction.KILL_PROCESS_BY_PID)
        {
            processToKill = null;
            startTimer_button.setDisable(true);
            startTimer_MenuItem.setDisable(true);
        }
    }

    public void showNotification(final String aTitle, final String aMessage)
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
    }

    private void startTimer_Action(ActionEvent event)
    {
        superRoot.getChildren().remove(menuBar);
        timerIsRunning_pane_superRoot.getChildren().remove(menuBar);
        timerIsRunning_pane_superRoot.getChildren().add(0, menuBar);

        System.out.println("startTimer_MenuItem and button: " + startTimer_MenuItem.isDisable() + "\t" + startTimer_button.isDisable());
        long startTime = System.currentTimeMillis();
        saveSettings();

        if (notify != null && !loopTimer_CheckBox.isSelected())
        {
            notify.hideNotification();
        }

        LocalTime countdownTimer = calculateCountdownTime();
        timerTimeInSeconds = countdownTimer.get(ChronoField.SECOND_OF_DAY);
        System.out.println("Время таймера в секундах: " + timerTimeInSeconds);

        stopTimer_MenuItem.setDisable(false);
        startTimer_button.setDisable(true);
        startTimer_MenuItem.setDisable(true);
        exit_menuItem.setDisable(true);
        timeTemplates_Menu.setDisable(true);
        createTimeTemplate_MenuItem.setDisable(true);

        timer_obj = new Timeline(new KeyFrame(Duration.seconds(timerTimeInSeconds), ev ->
                timerWentOutAction()));
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
        prepareTimerInfoToAppearance(countdownTimer);
        removeOrAddTimerInfoNodesIfNeeded();


        scene.setRoot(timerIsRunning_pane_superRoot);
        scene.getWindow().sizeToScene();

        stage.setMinHeight(scene.getWindow().getHeight() - 1);

        startTimerToUpdatingTimeAppearance();
        System.out.println("Время после нажатия кнопки старт: " + (System.currentTimeMillis() - startTime));

        startTimer_button.setDisable(true);
        //startTimer_MenuItem.setDisable(true);
        checkDateAndAddAnimation();
    }


    private void checkDateAndAddAnimation()
    {
        if (!playAnimation_MenuItem.isSelected())
        {
            timerIsRunning_pane_superRoot.setBackground(whiteSmokeBackground);
            return;
        }
        LocalDate currentDate = LocalDate.now();
        //LocalDate currentDate = LocalDate.of(2023, 2, 15);
        //LocalDate currentDate = LocalDate.of(2023, 4, 26);
        if (currentDate.getMonth() == Month.DECEMBER
                || currentDate.getMonth() == Month.JANUARY
                || currentDate.getMonth() == Month.FEBRUARY)
        {
            System.out.println("FallingSnowflakes");
            if (fallingSnowflakes == null)
            {
                initializeFallingSnowflakesAnimation();
            }
            timerIsRunning_pane_superRoot.setBackground(wingterBackground);
            fallingSnowflakes.play();
        }
//        else if (currentDate.getMonthValue() >= 3 &&
//                currentDate.getMonthValue() <= 5)
//        {
//            System.out.println("FlowersGrowing");
//            if (flowersGrowing == null)
//            {
//                initializeFlowersGrowingAnimation();
//            }
//            //timerIsRunning_pane_superRoot.setBackground(wingterBackground);
//            //flowersGrowing.setMaxFlowerSize(40.0D);
//            timerIsRunning_pane_superRoot.setBackground(springBackground);
//            flowersGrowing.play();
//        }
    }

    private VBox timerInfoPane;

    private LocalTime calculateCountdownTime()
    {
        LocalTime countdownTimer;

        int timerHours = Integer.parseInt(hours_Spinner.getValue().toString());
        int timerMinutes = Integer.parseInt(minutes_Spinner.getValue().toString());
        int timerSeconds = Integer.parseInt(seconds_Spinner.getValue().toString());

        if (specifiedTimeTimer_MenuItem.isSelected())
        {
            LocalTime time = LocalTime.now();

            LocalTime destinationTime = LocalTime.of(timerHours, timerMinutes, timerSeconds);
            countdownTimer = destinationTime.minusHours(time.getHour());
            countdownTimer = countdownTimer.minusMinutes(time.getMinute());
            countdownTimer = countdownTimer.minusSeconds(time.getSecond());
        }
        else
        {
            if (timerHours == 0 && timerMinutes == 0 && timerSeconds == 0)
            {
                timerSeconds = 1;
            }

            countdownTimer = LocalTime.of(timerHours, timerMinutes, timerSeconds);
        }

        return countdownTimer;
    }

    /**
     * Здесь происходит инициализация компонентов, которые отображаются после старта таймера.
     */
    private void initializeTimerIsRunningComponents()
    {
        //Инициализация новых компонентов для отображения информации о запущенном таймере
        timerIsRunning_pane_superRoot = new VBox();
        timerIsRunning_pane_superRoot.setMaxWidth(PREFERRED_WIDTH + 3);
        timerIsRunning_pane_superRoot.setPrefWidth(PREFERRED_WIDTH);
        timerIsRunning_pane_superRoot.setMinWidth(PREFERRED_WIDTH - 2);

        timerIsRunning_pane = new VBox(fxGui.rem * 1.01D);
        timerIsRunning_pane.setPadding(new Insets(fxGui.rem * 1.20D));

        Label colon_Label = new Label(":");
        Label colon_Label2 = new Label(":");

        hoursAppearance_Label = new Label();
        minutesAppearance_Label = new Label();
        secondsAppearance_Label = new Label();

        HBox secondMinutesHoursAppearance_Box = new HBox(fxGui.rem * 0.25D, hoursAppearance_Label,
                colon_Label, minutesAppearance_Label, colon_Label2, secondsAppearance_Label);
        secondMinutesHoursAppearance_Box.setAlignment(Pos.CENTER);
        secondMinutesHoursAppearance_Box.setPadding(new Insets(
                fxGui.rem * 1.2D, 0.0D,
                fxGui.rem * 1.2D, 0.0D));

        HBox stopTimer_Box = new HBox();
        stopTimer_Box.setAlignment(Pos.CENTER);


        timerInformation_Label = new Label(info_str);

        timerInformation_Box = new HBox(timerInformation_Label);
        timerInformation_Box.setAlignment(Pos.CENTER);

        Font timerInformationFont = null;
        try
        {
            fontForLabels = YALtools.createFontFXFromResources("Fonts/madelikesslab.ttf", 16.0D);
            fontForLabelsValues = YALtools.createFontFXFromResources("Fonts/adana-script.ttf", 31.0D);
            fontForLabelsValues = Font.font(Font.getDefault().getName(), FontWeight.BOLD,
                    12.0D);
            numbers_Font = YALtools.createFontFXFromResources("Fonts/a_lcdnova.ttf",
                    38.0D);
            timerInformationFont = YALtools.createFontFXFromResources("Fonts/appetite-italic.ttf", 24.0D);

        }
        catch (IOException ioExc)
        {
            ioExc.printStackTrace();
        }

        timerInformation_Label.setFont(timerInformationFont);
        hoursAppearance_Label.setFont(numbers_Font);
        minutesAppearance_Label.setFont(numbers_Font);
        secondsAppearance_Label.setFont(numbers_Font);
        colon_Label.setFont(numbers_Font);
        colon_Label2.setFont(numbers_Font);

        final Color keyColor = Color.FIREBRICK;
        final Color valueColor = Color.MEDIUMSLATEBLUE;

        actionInfo_Label = new Label(action_str);
        actionInfo_Label.setFont(fontForLabels);
        actionInfo_Label.setTextFill(keyColor);

        actionValue_Label = new Label(actionDescription);
        actionValue_Label.setFont(fontForLabelsValues);
        actionValue_Label.setWrapText(true);
        actionValue_Label.setTextFill(valueColor);

        timerName_Label = new Label(timerName_str);
        timerName_Label.setFont(fontForLabels);
        timerName_Label.setTextFill(keyColor);

        timerNameValue_Label = new Label(timerName_textFiels.getText());
        timerNameValue_Label.setFont(fontForLabelsValues);
        timerNameValue_Label.setWrapText(true);//sienna
        timerNameValue_Label.setTextFill(valueColor);

        //timerInfoAppearance_GridPane = new GridPane();
        //timerInfoAppearance_GridPane.setHgap(rem * 0.5D);
        //timerInfoAppearance_GridPane.setAlignment(Pos.BASELINE_CENTER);'
        timerInfoPane = new VBox(fxGui.rem * 0.4D);
        timerNameInfo_Pane = new VBox(timerName_Label, timerNameValue_Label);
        timerActionInfo_Pane = new VBox(actionInfo_Label, actionValue_Label);

        //timerInfoPane.getChildren().addAll(timerNameInfo_Pane, timerActionInfo_Pane);


        timerIsRunning_pane.getChildren().addAll(secondMinutesHoursAppearance_Box,
                new Separator(), timerInformation_Box, timerInfoPane);


        timerIsRunning_pane_superRoot.getChildren().addAll(timerIsRunning_pane);

        HBox rootPopup_HBox = new HBox();
        rootPopup_HBox.setBackground(new Background(new BackgroundFill(Color.BLANCHEDALMOND, new CornerRadii(15.0D), Insets.EMPTY)));
        rootPopup_HBox.setPadding(new Insets(fxGui.rem * 0.45D));
        rootPopup_HBox.setBorder(new Border(new BorderStroke(Color.CHOCOLATE, BorderStrokeStyle.SOLID,
                new CornerRadii(15.0D), new BorderWidths(fxGui.rem * 0.09D))));

        Label textPopup = new Label(popup_str);
        textPopup.setFont(Font.font(textPopup.getFont().getName(), FontWeight.BOLD, 12.0D));

        rootPopup_HBox.getChildren().add(textPopup);

        popup = new Popup();
        popup.getContent().add(rootPopup_HBox);
        popup.setAutoHide(true);

        System.out.println("Поток инициализации компонентов обратного отчета таймера закончился.");
    }

    private void performAction()
    {
        boolean windows = System.getProperty("os.name").contains("Windows");
        boolean linux = System.getProperty("os.name").contains("Linux");

        if (windows)
        {
            performWindowsAction(findSelectedActionRadioButtonUsingToggleGroup().getAction());
        }
        else if (linux)
        {
            performLinuxAction(findSelectedActionRadioButtonUsingToggleGroup().getAction());
        }
    }

    private void performLinuxAction(final TimerAction timerActionType)
    {
        YALtools.printDebugMessage("Виконання операції для ОС: GNU/Linux... Тип: " + timerActionType.name());

        Action timerAction = null;
        try
        {
            YALtools.printDebugMessage("Имя таймера: " + timerName_textFiels.getText());

            if (timerActionType == TimerAction.SHUTDOWN)
            {
                timerAction = new Shutdown();

            }
            else if (timerActionType == TimerAction.SUSPEND)
            {
                timerAction = new Suspend();
            }
            else if (timerActionType == TimerAction.CUSTOM_COMMAND)//------------------------- Реалізація
            {
                timerAction = new Command(customCommand_textField.getText());
            }
            else if (timerActionType == TimerAction.REBOOT)
            {
                timerAction = new Reboot();
                //commandArray[2] = "firefox mail.yahoo.com & audacious /media/yaroslav/Freedom/1.\\ Audio/1996\\ Pure\\ Instinct\\ [1996\\ Japan\\ AMCE-950\\ EW]/1996\\ -\\ Pure\\ Instinct\\ (AMCE-950)/10\\ -\\ You\\ And\\ I.flac & gnome-terminal";
            }
            else if (timerActionType == TimerAction.KILL_PROCESS_BY_PID)
            {
                if (killProcessWithChildren_CheckBox.isSelected())
                {
                    timerAction = new KillProcessByPid(processToKill.getShortCommand(),
                            true);
                }
                else
                {
                    timerAction = new KillProcessByPid(processToKill.getPid());
                }
            }
            else if (timerActionType == TimerAction.LOGOUT)
            {
                timerAction = new Logout();
            }
            else if (timerActionType == TimerAction.BRIGHTNESS)
            {
                timerAction = new Brightness(BrightnessMethod.BACKLIGHT_CONFIG_FILE, brightness);
            }

            timerAction.perform();
        }
        catch (IOException ioExc)
        {
            YALtools.printDebugMessage("Возникла ошибка ввода-вывода при выполнении команды: \n" + ioExc);
        }
    }

    private void performWindowsAction(final TimerAction timerActionType)
    {
        Action timerAction = null;
        System.out.println("TiimerActionType: " + timerActionType.name());
        if (timerActionType == TimerAction.SHUTDOWN)
        {
            timerAction = new org.yaldysse.atfx.action.windows.Shutdown();
        }
        else if (timerActionType == TimerAction.SUSPEND)
        {
            timerAction = new org.yaldysse.atfx.action.windows.Suspend();
        }
        else if (timerActionType == TimerAction.CUSTOM_COMMAND)//--------------------- Implementation
        {
            timerAction = new Command(customCommand_textField.getText());
        }
        else if (timerActionType == TimerAction.REBOOT)
        {
            timerAction = new org.yaldysse.atfx.action.windows.Reboot();
        }
        else if (timerActionType == TimerAction.KILL_PROCESS_BY_PID)
        {
            timerAction = new org.yaldysse.atfx.action.windows.KillProcessByPid(
                    processToKill.getPid(), killProcessWithChildren_CheckBox.isSelected());
        }
        else if (timerActionType == TimerAction.LOGOUT)
        {
            timerAction = new org.yaldysse.atfx.action.windows.Logout();
        }
        else if (timerActionType == TimerAction.BRIGHTNESS)
        {
            timerAction = new org.yaldysse.atfx.action.windows.Brightness(brightness);
        }

        try
        {
            timerAction.perform();
        }
        catch (IOException ioException)
        {
            YALtools.printDebugMessage("Ошибка ввода-вывода при выполнении команды под Windows: " + ioException);
            YALtools.printDebugMessage("================================");
            ioException.printStackTrace();
            YALtools.printDebugMessage("================================\n\n");
        }
        YALtools.printDebugMessage("Выполнение команды Wind завершено.");
    }

    private void startTimerToUpdatingTimeAppearance()
    {
        if (timeline == null)
        {
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev ->
            {
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
        stoppedByUser = true;
        stoppingTimer();
    }

    /**
     * Викликається, щоб зупинити таймер та перейти на першу сцену.
     */
    private void stoppingTimer()
    {
        timerIsRunning_pane_superRoot.getChildren().remove(menuBar);
        superRoot.getChildren().add(0, menuBar);

        timeline.stop();
        timer_obj.stop();
        YALtools.printDebugMessage("Нажата клавиша остановки таймера.");
        stopTimer_MenuItem.setDisable(true);
        startTimer_button.setDisable(false);
        startTimer_MenuItem.setDisable(false);
        exit_menuItem.setDisable(false);
        timeTemplates_Menu.setDisable(false);
        createTimeTemplate_MenuItem.setDisable(false);

        if (fallingSnowflakes != null && fallingSnowflakes.isRunning())
        {
            fallingSnowflakes.stop();
        }

        YALtools.printDebugMessage("Кол-во узлов superRoot после остановки: " + superRoot.getChildren().size());

        scene.setRoot(superRoot);
        scene.getWindow().sizeToScene();

        stage.setMinHeight(scene.getWindow().getHeight() - 1);

        if (loopTimer_CheckBox.isSelected() && !stoppedByUser)
        {
            startTimer_Action(new ActionEvent());
        }
        stoppedByUser = false;
    }

    private void initLocalization(final String aLocale)
    {
        Properties language_properties = new Properties();
        try
        {
            language_properties.loadFromXML(this.getClass().getResourceAsStream("/Localizations/Language_" + aLocale + ".lang"));

            startButton_str = language_properties.getProperty("startTimer_button", "Start");
            inTimerType_str = language_properties.getProperty("timerType_In_Label", "in");
            atTimerType_str = language_properties.getProperty("timerType_At_Label", "at");
            notify_str = language_properties.getProperty("notify_str", "Notify");

            if (countdownTimer_MenuItem.isSelected())
            {
                startTimer_button.setText(notify_str + " " + inTimerType_str + " (" + startButton_str + ")");
            }
            else
            {
                startTimer_button.setText(notify_str + " " + atTimerType_str + " (" + startButton_str + ")");
            }

            startTimer_MenuItem.setText(language_properties.getProperty("startTimer_MenuItem", "Start"));
            stopTimer_MenuItem.setText(language_properties.getProperty("stopTimer_MenuItem", "Start"));
            performActionAfterTimerWentOut_checkBox.setText(language_properties.getProperty("performActionAfterTimerWentOut_checkBox", "Start"));
            timerType_Menu.setText(language_properties.getProperty("timerType_Menu", "Timer type"));
            removeAllTimeTemplates_MenuItem.setText(language_properties.getProperty("removeAllTimeTemplates_MenuItem", "Remove All"));
            timeTemplates_Menu.setText(language_properties.getProperty("timeTemplates_Menu", "Templates"));
            createTimeTemplate_MenuItem.setText(language_properties.getProperty("createTimeTemplate_MenuItem", "Create"));
            specifiedTimeTimer_MenuItem.setText(language_properties.getProperty("specifiedTime_MenuItem", "at specified time"));
            countdownTimer_MenuItem.setText(language_properties.getProperty("countdownTimer_MenuItem", "in specified time"));
            playAnimation_MenuItem.setText(language_properties.getProperty("animations_MenuItem", "Animations"));
            timerName_textFiels.setPromptText(language_properties.getProperty("TimerName_PromtText", "Name of timer (optional)"));
            timerName_str = language_properties.getProperty("timerName_Label", "Name of timer:");
            action_str = language_properties.getProperty("timerAction_Label", "Action: ");
            info_str = language_properties.getProperty("info_text", "Info");
            gitHubRepository_MenuItem.setText(language_properties.getProperty("gitHubRepository_MenuItem", "Repository on GitHub"));
            customCommandPromtText_str = language_properties.getProperty("customCommandPromtText", "Enter here command that will be performed after timer went out");
            delayBeforeAction_CheckBox.setText(language_properties.getProperty("delayBeforeAction_CheckBox", "Delay before action"));
            doYouWantToContinue_str = language_properties.getProperty("doYouWantToContinue_str", "Do you want to continue ?");
            warning_str = language_properties.getProperty("warning_str", "Warning!");
            popup_str = language_properties.getProperty("popup_str", "To stop timer click left button twice.");
            customCommand_textField.setPromptText(customCommandPromtText_str);
            theTimerHasExpired_str = language_properties.getProperty("theTimerHasExpired_str", "The timer has expired.");
            timerTemplateCreated_str = language_properties.getProperty("timerTemplateCreated_str", "Timer Template has been created.");
            timerTemplateApplied_str = language_properties.getProperty("timerTemplateApplied_str", "Timer template has been applied.");
            allTimerTemplatesRemoved_str = language_properties.getProperty("allTemplatesRemoved_str", "All Timer Templates have been removed.");
            needRootPrivilege_str = language_properties.getProperty("needRootPrivilege_str", "To perform this operation Root privilege needed.");
            killProcessWithChildren_CheckBox.setText(language_properties.getProperty("killProcessWithChildren_CheckBox",
                    "Kill children processes"));
            loopTimer_CheckBox.setText(language_properties.getProperty("loopTimer_MenuItem", "Cyclic timer"));
            loopTimer_CheckBox.getTooltip().setText(language_properties.getProperty("loopTimer_toolTip",
                    "<Not Found>"));

            rootPrivilege_ToolTip.setText(needRootPrivilege_str);

            actionDescriptionStrings.clear();

            String key;
            //ActionRadioButtons
            for (int k = 0; k < radioButtons.size(); k++)
            {
                key = radioButtons.get(k).getAction() + "_Button";
                radioButtons.get(k).setText(language_properties.getProperty(key, "<Not Found: >" + key));

                key = radioButtons.get(k).getAction().name() + "_actionDescription";
                actionDescriptionStrings.add(language_properties.getProperty(key, "<Not Found: <" + key));

            }


            ActionRadioButton selectedRadioButton = findSelectedActionRadioButtonUsingToggleGroup();
            if (selectedRadioButton != null)
            {
                actionDescription = findActionDescriptionStringByTimerTypeAction(
                        findSelectedActionRadioButtonUsingToggleGroup().getAction());
                actionValue_Label.setText(actionDescription);
            }

            if (timerInformation_Label != null)
            {
                timerInformation_Label.setText(info_str);
            }

            if (timerName_Label != null)
            {
                timerName_Label.setText(language_properties.getProperty("timerName_Label", "Start"));
            }

            if (actionInfo_Label != null &&
                    selectedRadioButton != null)
            {
                actionInfo_Label.setText(language_properties.getProperty("timerAction_Label", "Action:"));
                actionDescription = findActionDescriptionStringByTimerTypeAction(
                        findSelectedActionRadioButtonUsingToggleGroup().getAction());
                actionValue_Label.setText(actionDescription);
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

            general_menu.setText(language_properties.getProperty("general_menu", "General"));
            language_Menu.setText(language_properties.getProperty("language_Menu", "Language"));
            timer_Menu.setText(language_properties.getProperty("timer_Menu", "Timer"));
            exit_menuItem.setText(language_properties.getProperty("exit_MenuItem", "Exit"));

            if (hours_text != null)
            {
                hours_text.setText(language_properties.getProperty("hours_Text", "Hours"));
                minutes_text.setText(language_properties.getProperty("minutes_Text", "Minutes"));
                seconds_text.setText(language_properties.getProperty("seconds_Text", "Seconds"));
            }

            if (popup != null)
            {
                HBox temporary = (HBox) popup.getContent().get(0);
                Label temporaryLabel = (Label) temporary.getChildren().get(0);
                temporaryLabel.setText(popup_str);
            }

            ObservableList<MenuItem> menuItems = timeTemplates_Menu.getItems();
            TemplateMenuItem tmi;

            for (int k = 3; k < menuItems.size(); k++)
            {
                tmi = (TemplateMenuItem) menuItems.get(k);

                tmi.requestUpdateLocalization("Language_"
                        + aLocale + ".lang_templateTip");

            }

            scene.getWindow().sizeToScene();
        }
        catch (IOException ioException)
        {
            YALtools.printDebugMessage("Возникла ошибка при импорте узла локализации." + ioException);
            return;
        }
        currentLanguage_str = aLocale;
    }

    private void saveSettings()
    {
        Properties properties = new Properties();
        properties.put("locX", String.valueOf(stage.getX()));
        properties.put("locY", String.valueOf(stage.getY()));
        properties.put("Language", currentLanguage_str);
        properties.put("delayBeforeAction_CheckBox", String.valueOf(delayBeforeAction_CheckBox.isSelected()));
        properties.put("delayBeforeAction", String.valueOf(delayBeforeAction_Spinner.getValue()));
        properties.put("playSeasonAnimation", String.valueOf(playAnimation_MenuItem.isSelected()));

        try
        {
            TimerTemplate.exportTimerTemplatesToFile(YALtools.getJarLocation().toPath()
                            .getParent().resolve(TIMER_TEMPLATES_FILE_NAME),
                    timerTemplates);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        try
        {
            YALtools.printDebugMessage(YALtools.getJarLocation().getAbsolutePath());
            properties.storeToXML(Files.newOutputStream(
                            YALtools.getJarLocation().toPath().getParent().resolve(PROPERTY_FILE_NAME)),
                    "");
        }
        catch (Exception ioExc)
        {
            YALtools.printDebugMessage("Возникла ошибка ввода-вывода узла настроек.\n" + ioExc);
        }
    }

    private void importSettings()
    {
        long startTime = System.currentTimeMillis();

        Properties properties = new Properties();

        try
        {
            properties.loadFromXML(Files.newInputStream(YALtools.getJarLocation().toPath()
                    .getParent().resolve(PROPERTY_FILE_NAME)));

            currentLanguage_str = properties.getProperty("Language", "English");
            locX = (int) Double.parseDouble(properties.getProperty("locX", "100"));
            locY = (int) Double.parseDouble(properties.getProperty("locY", "0"));
            delayBeforeAction_CheckBox.setSelected(Boolean.parseBoolean(
                    properties.getProperty("delayBeforeAction_CheckBox", "true")));
            delayBeforeAction_Spinner.getValueFactory().setValue(Integer.parseInt(
                    properties.getProperty("delayBeforeAction", "15")));
            playAnimation_MenuItem.setSelected(Boolean.parseBoolean(properties.getProperty("playSeasonAnimation", "true")));

            if (delayBeforeAction_CheckBox.isSelected() &&
                    !delayCustomMenuItem_HBox.getChildren().contains(delayBeforeAction_Spinner))
            {
                delayCustomMenuItem_HBox.getChildren().add(delayBeforeAction_Spinner);
            }
            else if (!delayBeforeAction_CheckBox.isSelected() &&
                    delayCustomMenuItem_HBox.getChildren().contains(delayBeforeAction_Spinner))
            {
                delayCustomMenuItem_HBox.getChildren().remove(delayBeforeAction_Spinner);
            }

            timerTemplates = TimerTemplate.importTimerTemplatesFromFile(YALtools.getJarLocation().toPath().getParent().resolve(TIMER_TEMPLATES_FILE_NAME));
            updateTimeTemplatesMenu();

        }
        catch (FileNotFoundException fileNFExc)
        {
            YALtools.printDebugMessage("Возникла ошибка при обнаружении каталога сохранения узла.\n" + fileNFExc);
        }
        catch (Exception ioExc)
        {
            YALtools.printDebugMessage("Возникла ошибка ввода-вывода узла настроек.\n" + ioExc);
        }
        System.out.println("Settings: " + (System.currentTimeMillis() - startTime));
    }

    /**
     * Тут використовуєтться універсальний варіант збереженя обраноної діх,
     * що оснований на методі {@link #findSelectedActionRadioButtonUsingToggleGroup()}.
     * Таким чином він буде правювати коректно до тих пір, поки для вибору дії
     * використовується елемент {@link RadioButton}.
     */
    private void createTimeTemplate_Action(ActionEvent event)
    {
        TimerTemplate timerTemplate = new TimerTemplate(timerName_textFiels.getText(),
                LocalTime.of(hours_Spinner.getValue(), minutes_Spinner.getValue(),
                        seconds_Spinner.getValue()), countdownTimer_MenuItem.isSelected(),
                delayBeforeAction_CheckBox.isSelected(), delayBeforeAction_Spinner.getValue());

        if (performActionAfterTimerWentOut_checkBox.isSelected())
        {
            ArrayList<TimerAction> actions = new ArrayList<>();

            ActionRadioButton selectedRadio = findSelectedActionRadioButtonUsingToggleGroup();

            if (selectedRadio != null)
            {
                actions.add(selectedRadio.getAction());

                if (selectedRadio.getAction() == TimerAction.BRIGHTNESS)
                {
                    timerTemplate.setBrightness(brightness);
                }

                if (selectedRadio.getAction() == TimerAction.KILL_PROCESS_BY_PID)
                {
                    timerTemplate.setProcessToKill(processToKill);
                }

                if (selectedRadio.getAction() == TimerAction.CUSTOM_COMMAND)
                {
                    timerTemplate.setCustomCommand_str(customCommand_textField.getText());
                }
            }
            timerTemplate.setActions(actions);
        }

        timerTemplates.add(timerTemplate);

        showAutoHideTooltip(timerTemplateCreated_str, Duration.millis(1500),
                timerName_textFiels);

        updateTimeTemplatesMenu();
    }

    public void updateTimeTemplatesMenu()
    {
        timeTemplates_Menu.getItems().remove(3, timeTemplates_Menu.getItems().size());
        for (int k = 0; k < timerTemplates.size(); k++)
        {
            TemplateMenuItem temporaryTemplate;
            //if (timeTemplate_MenuItemsArray.get(k) == null)
            if (timeTemplate_MenuItemsArray.size() < k + 1)
            {
//                temporaryTemplate = new MenuItem(timerTemplates.get(k).getTime().format(
//                        DateTimeFormatter.ISO_TIME) + "   [" + timerTemplates.get(k).getName() + "]");
                temporaryTemplate = new TemplateMenuItem(timerTemplates.get(k), this);
                int finalK = k;
                temporaryTemplate.setOnAction(event -> applyTimerTemplate(timerTemplates.get(finalK)));
                timeTemplate_MenuItemsArray.add(temporaryTemplate);
                temporaryTemplate.setAccelerator(KeyCodeCombination.valueOf("Alt+" + (k + 1)));
            }
            else
            {
                temporaryTemplate = timeTemplate_MenuItemsArray.get(k);
                temporaryTemplate.setOnAction(null);
                int finalK1 = k;
                temporaryTemplate.setOnAction(event -> applyTimerTemplate(timerTemplates.get(finalK1)));
                temporaryTemplate.setName(timerTemplates.get(k).getTime().format(
                        DateTimeFormatter.ISO_TIME) + "   [" + timerTemplates.get(k).getName() + "]");
                temporaryTemplate.setTimerTemplate(timerTemplates.get(k));
                temporaryTemplate.updateInfoToolTip();
            }

            ImageView info_ImageView = new ImageView(info_Image);
            info_ImageView.setSmooth(true);
            info_ImageView.setPreserveRatio(true);
            info_ImageView.setFitWidth(ICON_SIZE);

            ImageView deleteActive_ImageView = new ImageView(deleteTemplate_Image);
            deleteActive_ImageView.setSmooth(true);
            deleteActive_ImageView.setPreserveRatio(true);
            deleteActive_ImageView.setFitWidth(ICON_SIZE);

            ImageView deleteUnActive_ImageView = new ImageView(deleteUnActive_Image);
            deleteUnActive_ImageView.setSmooth(true);
            deleteUnActive_ImageView.setPreserveRatio(true);
            deleteUnActive_ImageView.setFitWidth(ICON_SIZE);

            temporaryTemplate.setInfoImageView(info_ImageView);
            temporaryTemplate.setDeleteActiveImageView(deleteActive_ImageView);
            temporaryTemplate.setDeleteUnActiveImageView(deleteUnActive_ImageView);

            timeTemplates_Menu.getItems().add(temporaryTemplate);
        }

    }

    private void removeAllTimeTemplates_Action(ActionEvent event)
    {
        timerTemplates.clear();
        showAutoHideTooltip(allTimerTemplatesRemoved_str, Duration.millis(1500),
                timerName_textFiels);
        updateTimeTemplatesMenu();
    }

    private void showAutoHideTooltip(final String message, Duration showDuration,
                                     Node node)
    {
        Tooltip timerTemplateCreated = new Tooltip(message);
        timerTemplateCreated.setOnShowing(event ->
        {
            Timeline timeline1 = new Timeline(new KeyFrame(showDuration,
                    event2 -> timerTemplateCreated.hide()));
            timeline1.play();
        });
        timerTemplateCreated.show(node, node.localToScreen(1, 1).getX(),
                node.localToScreen(1, 1).getY());
    }

    private void applyTimerTemplate(TimerTemplate template)
    {
        timerName_textFiels.setText(template.getName());

        countdownTimer_MenuItem.setSelected(template.isCountdownTimer());
        specifiedTimeTimer_MenuItem.setSelected(!template.isCountdownTimer());

        if (template.isCountdownTimer())
        {
            Event.fireEvent(countdownTimer_MenuItem, new ActionEvent());
        }
        else
        {
            Event.fireEvent(specifiedTimeTimer_MenuItem, new ActionEvent());
        }

        hours_Spinner.getValueFactory().setValue(template.getTime().getHour());
        minutes_Spinner.getValueFactory().setValue(template.getTime().getMinute());
        seconds_Spinner.getValueFactory().setValue(template.getTime().getSecond());

        delayBeforeAction_CheckBox.setSelected(template.hasActionDelay());
        Event.fireEvent(delayBeforeAction_CheckBox, new ActionEvent());
        delayBeforeAction_Spinner.getValueFactory().setValue(template.getActionDelayValue());


        ArrayList<TimerAction> actions = template.getActions();
        ActionRadioButton actionRadioButton = null;

        if (actions.size() > 0)
        {
            performActionAfterTimerWentOut_checkBox.setSelected(true);
            Event.fireEvent(performActionAfterTimerWentOut_checkBox, new ActionEvent());

            for (TimerAction ta : actions)
            {
                actionRadioButton = findActionRadioButtonByRadioButtonAction(radioButtons, ta);
                actionRadioButton.setSelected(true);
            }
            actionRadioButton.fireEvent(new ActionEvent());
        }

        if (actions.contains(TimerAction.KILL_PROCESS_BY_PID))
        {
            processToKill = template.getProcessToKill();
        }
        else if (actions.contains(TimerAction.CUSTOM_COMMAND))
        {
            customCommand_textField.setText(template.getCustomCommand());
        }
        else if (actions.contains(TimerAction.BRIGHTNESS))
        {
            brightness = template.getBrightness();
            brightnessValue.setText(String.valueOf(brightness));
        }


        showAutoHideTooltip(timerTemplateApplied_str, Duration.millis(1500),
                timerName_textFiels);
    }

    public static String getCurrentLanguageName()
    {
        return currentLanguage_str;
    }


    private void timeSpinnerVerification(String oldValue, String newValue,
                                         final int minValue, final int maxValue, Spinner<Integer> spinner)
    {
        if (newValue.equals(""))
        {
            return;
        }

        try
        {
            int value = Integer.parseInt(newValue);
            if (value > maxValue || value < minValue)
            {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException numberFormatException)
        {
            System.out.println("Введено некорректное значение в часы.");
            spinner.getEditor().setText(oldValue);
        }
    }

    public ArrayList<TimerTemplate> getTimerTemplates()
    {
        return timerTemplates;
    }

    /**
     * Знаходить об'єкт компонування, що відповідає вказаній дії та видаляє з неї
     * вказаний вузол.
     */
    private void removeNodeFromRadioPaneByRadioButtonAction(final TimerAction timerAction,
                                                            final Node... nodes)
    {
        Pane pane = findRadioButtonContainerByRadioButtonAction(timerAction);
        for (Node currentNode : nodes)
        {
            pane.getChildren().remove(currentNode);
        }
    }

    /**
     * Шукає об'єкт компонування, що використовується для вказаної дії.
     * Важливою особливістю є те, що порядок RadioButton в колекції повинен
     * свівпадати з порядком об'єктів компонуваня в своїй колекції.
     */
    private Pane findRadioButtonContainerByRadioButtonAction(final TimerAction timerAction)
    {
        Pane pane = null;
        for (int k = 0; k < radioButtons.size(); k++)
        {
            if (radioButtons.get(k).getAction() == timerAction)
            {
                pane = radioButtonsBoxes.get(k);
                break;
            }
        }

        return pane;
    }

    private ActionRadioButton findActionRadioButtonByRadioButtonAction(final ArrayList<ActionRadioButton>
                                                                               aRadioButtons, final TimerAction timerAction)
    {
        for (ActionRadioButton current : aRadioButtons)
        {
            if (current.getAction() == timerAction)
            {
                return current;
            }
        }
        throw new IllegalArgumentException("Відповідний об'єект для дії " + timerAction.name() + " не знайдено.");
    }

    /**
     * Обробник події під час натискання на ActionRadioButton.
     * Вмикає кнопки запуску та зупинки таймеру, підганяє розміри вікна під
     * сцену. В цей метод винесені загаліні операція для кожної кнопки.
     * Видаляє елементи для компонентів компонування операциї вбивства процесу та
     * яскравості. Видаляє текстове поле для введення власної команди.
     */
    private void actionRadioButton_Action(ActionEvent event)
    {
        removeNodeFromRadioPaneByRadioButtonAction(TimerAction.KILL_PROCESS_BY_PID,
                openProcessSelector_Button);
        removeNodeFromRadioPaneByRadioButtonAction(TimerAction.KILL_PROCESS_BY_PID,
                killProcessWithChildren_CheckBox);
        removeNodeFromRadioPaneByRadioButtonAction(TimerAction.BRIGHTNESS,
                brightnessValue);

        actionDescription = findActionDescriptionStringByTimerTypeAction(
                findSelectedActionRadioButtonUsingToggleGroup().getAction());
        actionValue_Label.setText(actionDescription);

        radioGroup_Box.getChildren().remove(customCommand_textField);

        startTimer_button.setDisable(false);
        startTimer_MenuItem.setDisable(false);
        scene.getWindow().sizeToScene();
    }

    private ActionRadioButton findSelectedActionRadioButtonUsingToggleGroup()
    {
        return (ActionRadioButton) actionRadioButtonToggleGroup.getSelectedToggle();
    }

    private void createAndShowTimerDelayAlert()
    {
        int tmpDelayTime = delayBeforeAction_Spinner.getValue();
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
                YALtools.printDebugMessage("Таймер отменен.");
            }
            else
            {
                performAction();
                YALtools.printDebugMessage("Starting: stopTimerButton_Action");
            }
        });
    }

    /**
     * Підготовує елементи, що відображають дані про таймер, коли той запущений.
     * Оновлює текст в лейблах та приховує їх за необхідністю.
     */
    private void prepareTimerInfoToAppearance(final LocalTime countdownTimer)
    {
        if (timerName_textFiels.getText() != null &&
                !timerName_textFiels.getText().equals(""))
        {
            timerNameValue_Label.setText(timerName_textFiels.getText());
            timerName_Label.setMinWidth(fxGui.calculateTextWidth(fontForLabels,
                    timerName_Label.getText()));
        }


        if (performActionAfterTimerWentOut_checkBox.isSelected())
        {
            actionValue_Label.setText(actionDescription);
            if (processToKill != null)
            {
                actionValue_Label.setText(actionValue_Label.getText() + " "
                        + processToKill.getPid() + " [" + processToKill.getShortCommand() + "]");
            }
            actionInfo_Label.setMinWidth(fxGui.calculateTextWidth(fontForLabels, actionInfo_Label.getText()));
            YALtools.printDebugMessage("Ширина текста 'Action:': " +
                    actionInfo_Label.getMinWidth());
        }

        hoursAppearance_Label.setText(String.valueOf(countdownTimer.getHour()));
        minutesAppearance_Label.setText(String.valueOf(countdownTimer.getMinute()));
        secondsAppearance_Label.setText(String.valueOf(countdownTimer.getSecond()));
    }

    /**
     * Приховує або показує компоненти графічного інтерфейсу, що відображають інформацію по таймер, якщо вони
     * пусті. Наприклад, користувач не дав таймеру ім'я.
     */
    private void removeOrAddTimerInfoNodesIfNeeded()
    {
        timerInfoPane.getChildren().clear();
        timerIsRunning_pane.getChildren().remove(timerInfoPane);
        timerIsRunning_pane.getChildren().remove(timerInformation_Box);


        boolean separator = false;
        if (timerName_textFiels.getText() != null &&
                !timerName_textFiels.getText().equals(""))
        {
            timerInfoPane.getChildren().add(0, timerNameInfo_Pane);
            separator = true;
        }

        if (performActionAfterTimerWentOut_checkBox.isSelected())
        {
            timerInfoPane.getChildren().add(timerActionInfo_Pane);
            separator = true;
        }

        if (separator)
        {
            timerIsRunning_pane.getChildren().addAll(timerInformation_Box, timerInfoPane);

            timerInfoPane.getChildren().add(new Separator());
        }
    }

    /**
     * Знаходить рядок з описом дії таймеру на основі елементу переліку з типами операції.
     * Заснований на тому, що порядок елементів ActionRadioButton буде відповідати
     * рядкам опису дій.
     */
    private String findActionDescriptionStringByTimerTypeAction(final TimerAction timerAction)
    {
        int index = -1;
        ActionRadioButton actionRadioButton;

        for (int k = 0; k < radioButtons.size(); k++)
        {
            actionRadioButton = radioButtons.get(k);

            if (actionRadioButton.getAction() == timerAction)
            {
                index = k;
                break;
            }
        }
        return actionDescriptionStrings.get(index);
    }

    /**
     * Загальний метод, що повинен викликатися для завершення роботи програми.
     */
    private void exit()
    {
        stage.hide();
        saveSettings();
    }

    private Tooltip rootPrivilege_ToolTip;

    /**
     * Перевіряє чи має користувач, що запустив програму вносити зміни до відповідного
     * файлу.
     */
    private void checkingBrightnessSettingAvailableLinux()
    {
        try
        {
            Brightness brightness1 = new Brightness(BrightnessMethod.BACKLIGHT_CONFIG_FILE,
                    (byte) 32);
            System.out.println("Доступ:" + brightness1.isBrightnessConfigWritableCurrentUser());

            ActionRadioButton actionRadioButton = findActionRadioButtonByRadioButtonAction(radioButtons, TimerAction.BRIGHTNESS);
            actionRadioButton.setDisable(!brightness1.isBrightnessConfigWritableCurrentUser());

            if (actionRadioButton.isDisable())
            {
                ImageView warning = fxGui.createImageView((new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/warning.png")))),
                        22.0D);

                Label warningLabel = new Label();
                warningLabel.setGraphic(warning);
                warningLabel.setTooltip(rootPrivilege_ToolTip);

                findRadioButtonContainerByRadioButtonAction(TimerAction.BRIGHTNESS).getChildren()
                        .add(warningLabel);
            }
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    private void brightness_KeyPressed(KeyEvent event)
    {
        KeyCode keyCode = event.getCode();

        switch (keyCode)
        {
            case UP:
                brightnessValue.requestFocus();
                if (brightness >= 100)
                {
                    break;
                }
                brightness++;
                break;

            case DOWN:
                brightnessValue.requestFocus();
                if (brightness <= 0)
                {
                    break;
                }
                brightness--;
                break;

            case LEFT:
                brightnessValue.requestFocus();
                if (brightness <= 1)
                {
                    brightness = 0;
                    break;
                }
                brightness -= 2;
                break;

            case RIGHT:
                brightnessValue.requestFocus();
                if (brightness >= 99)
                {
                    brightness = 100;
                    break;
                }
                brightness += 2;
                break;

            case PAGE_UP:
                brightnessValue.requestFocus();
                if (brightness >= 98)
                {
                    brightness = 100;
                    break;
                }
                brightness += 3;
                break;

            case PAGE_DOWN:
                brightnessValue.requestFocus();
                if (brightness <= 2)
                {
                    brightness = 0;
                    break;
                }
                brightness -= 3;
                break;
        }

        brightnessValue.setText(String.valueOf(brightness));
        changingBrightnessButtonViewByBrightnessValue();
    }

    private void brightness_focusedProperty(Observable observableValue,
                                            boolean oldValue, boolean newValue)
    {
        if (newValue)
        {
            brightnessValue.setBorder(brightnessFocusedBorder);
            brightnessValue.setEffect(brighnessDropShadow);
        }
        else
        {
            brightnessValue.setBorder(Border.EMPTY);
            brightnessValue.setEffect(null);
        }
    }

    private void changingBrightnessButtonViewByBrightnessValue()
    {
        double opacity = (double) brightness / 100.0D;
        if (brightness < 25)
        {
            opacity = 0.25D;
        }

        brightnessValue.setText(String.valueOf(brightness));
        brightnessValue.setBackground(new Background(new BackgroundFill(
                Color.rgb(173, 255, 47, opacity),
                new CornerRadii(fxGui.rem * 0.4D), Insets.EMPTY)));
    }
}
//22 24