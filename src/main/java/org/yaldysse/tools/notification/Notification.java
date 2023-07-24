package org.yaldysse.tools.notification;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.InputStream;


/**
 * Реализовывает платформенно-независимую поддержку уведомлений используя инструменты JavaFX.
 * Прототипом послужил инструмент notify-send в Linux.
 *
 * @author YALdysse
 * @version 2 (23.03.2022)
 */
public class Notification extends Stage
{
    private double SIZE_TO_SCREEN_RATIO;
    private double WIDTH_TO_SCREEN_RATIO;
    private double HEIGHT_TO_SCREEN_RATIO;

    private double preferred_width;
    private double preferred_height;

    private Stage stage;
    private Scene scene;
    private HBox root;

    //Линия монотонного цвета в левой части
    private VBox leftLine_VBox;
    //Введен, чтобы расположить все надписи сверху вниз
    private VBox content_VBox;
    //Предназначен для текста с названием и кнопки закрытия окна
    private HBox upLevel_HBox;
    //Предназначен для расположения названия слева
    private HBox title_HBox;
    private Label title_Label;
    //Предназначен для расположения псевдо-кнопки закрытия справа
    private HBox close_HBox;
    private Label close_Label;

    //Предназначен для содержания, основной части уведомления
    private Label message_Label;

    private VBox icon_VBox;
    private ImageView icon_ImageView;
    private static Image error_Image;
    private static Image information_Image;
    private static Image warning_Image;
    private double widthOfLeftVerticalLine;
    private double spaceBetweenNotifications_ratio;

    //удаление уведомление от правого края относительно всей ширины экрана
    private double spaceXGap_ratio;
    //удаление уведомление от нижнего края относительно всей высоты экрана
    private double spaceYGap_ratio = 0.05D;

    //определяют положение
    private double x;
    private double y;
    private boolean leftLineColorSameAsIcon = true;

    private Duration displayDuration;
    private Timeline display_Timeline;
    private Animation disappear_Animation;
    private AnimationType disappearanceAnimationType;


    /**
     * Создает уведомление по-умолчанию:
     * время отображения = 4 сек.
     * анимация исчезновения = FADE,
     * длительность анимации исчезновения = 3 сек.
     */
    public Notification(final String titleText, final String messageText)
    {
        initialize();

        stage.setWidth(preferred_width);
        stage.setHeight(preferred_height);
        stage.setX(x);
        stage.setY(y);

        displayDuration = Duration.seconds(4.0D);

        setMessage(messageText);
        setNotificationTitle(titleText);
        setIcon(NotificationType.INFORMATION);
        setDisappearanceAnimation(AnimationType.FADE, Duration.seconds(1.4D));

        display_Timeline = new Timeline(new KeyFrame(displayDuration, event ->
        {
            System.out.println("Старт анимации исчезновения.");
            disappear_Animation.play();
        }));
    }

    /**
     * @param titleText                   Название уведомления
     * @param messageText                 Текст уведомления
     * @param aNotificationIcon           Иконка уведомления
     * @param aDisplayDuration            Длительность отображения уведомления
     * @param aDisappearanceAnimationType Анимация исчезания
     * @param aDisappearanceDuration      Длительность исчезания уведомления
     */
    public Notification(final String titleText, final String messageText, NotificationType aNotificationIcon,
                        Duration aDisplayDuration, AnimationType aDisappearanceAnimationType,
                        Duration aDisappearanceDuration)
    {
        initialize();

        stage.setWidth(preferred_width);
        stage.setHeight(preferred_height);
        stage.setX(x);
        stage.setY(y);

        displayDuration = aDisplayDuration;

        setMessage(messageText);
        setNotificationTitle(titleText);
        setIcon(aNotificationIcon);
        setDisappearanceAnimation(aDisappearanceAnimationType, aDisappearanceDuration);

        display_Timeline = new Timeline(new KeyFrame(displayDuration, event ->
        {
            System.out.println("Старт анимации исчезновения.");
            disappear_Animation.play();
        }));
    }


    private void initialize()
    {
        stage = this;

        root = new HBox();
        root.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));

        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);

        WIDTH_TO_SCREEN_RATIO = 0.2D;
        HEIGHT_TO_SCREEN_RATIO = 0.1125D;
        widthOfLeftVerticalLine = 0.08D;
        spaceBetweenNotifications_ratio = 0.05D;
        spaceXGap_ratio = 0.014D;
        SIZE_TO_SCREEN_RATIO = 0.2D;

        preferred_width = Screen.getPrimary().getBounds().getWidth() * WIDTH_TO_SCREEN_RATIO;
        preferred_height = Screen.getPrimary().getBounds().getHeight() * HEIGHT_TO_SCREEN_RATIO;

        System.out.println("notification size: " + preferred_width + "x" + preferred_height);


        leftLine_VBox = new VBox();
        //leftLine_VBox.setBackground(new Background(new BackgroundFill(Color.DARKTURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        //leftLine_VBox.setPrefWidth(preferred_width * widthOfLeftVerticalLine);
        leftLine_VBox.setMinWidth(preferred_width * widthOfLeftVerticalLine);
        //leftLine_VBox.setPrefHeight(preferred_height);
        //leftLine_VBox.setFillWidth(true);

        content_VBox = new VBox();
        HBox.setHgrow(content_VBox, Priority.ALWAYS);

        title_Label = new Label("Title");
        title_Label.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 18.0D));
        //title_Label.setAlignment(Pos.BASELINE_LEFT);

        title_HBox = new HBox(title_Label);
        title_HBox.setAlignment(Pos.TOP_LEFT);
        //title_HBox.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        //HBox.setHgrow(title_HBox,Priority.ALWAYS);
        HBox.setHgrow(title_Label, Priority.ALWAYS);

        close_Label = new Label("X");
        close_Label.setAlignment(Pos.TOP_RIGHT);
        close_Label.setFont(Font.font(close_Label.getFont().getName(), FontWeight.BOLD, 14.0D));
        close_Label.setOpacity(0.8D);
        close_Label.setOnMouseClicked(event ->
        {
            System.out.println("Окно будет скрыто.");
            stage.hide();
        });
        close_Label.hoverProperty().addListener((event, oldValue, newValue) ->
        {
            if(newValue)
            {
                close_Label.setTextFill(Color.CRIMSON);
            }
            else
            {
                close_Label.setTextFill(Color.BLACK);
            }
        });

        close_HBox = new HBox(close_Label);
        close_HBox.setAlignment(Pos.TOP_RIGHT);


        upLevel_HBox = new HBox(title_HBox, close_HBox);
        //upLevel_HBox.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        upLevel_HBox.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(title_HBox, Priority.ALWAYS);


        message_Label = new Label("This is notification's message.");
        message_Label.setWrapText(true);

        icon_ImageView = new ImageView();

        icon_VBox = new VBox(icon_ImageView);
        icon_VBox.setAlignment(Pos.CENTER);

        content_VBox.setPadding(new Insets(preferred_height * 0.07D));
        //content_VBox.setBackground(new Background(new BackgroundFill(Color.SALMON, CornerRadii.EMPTY, Insets.EMPTY)));
        content_VBox.setSpacing(preferred_height * 0.1D);
        content_VBox.getChildren().addAll(upLevel_HBox, message_Label);

        x = Screen.getPrimary().getBounds().getWidth() - preferred_width - (Screen.getPrimary().getBounds().getWidth() * spaceXGap_ratio);
        y = Screen.getPrimary().getBounds().getHeight() - preferred_height - (Screen.getPrimary().getBounds().getHeight() * spaceYGap_ratio);

        System.out.println("X: " + x + "\tY: " + y);

        root.setSpacing(preferred_width * 0.03D);
        root.getChildren().addAll(leftLine_VBox, icon_VBox, content_VBox);
    }

//    public static void main(String[] args)
//    {
//        new Notification();
//    }

    /**
     * Задает название уведомления.
     * В случае если передаваемый текст имеет значение null или пустой,
     * название не изменяется.
     */
    public void setNotificationTitle(final String titleText)
    {
        if (titleText == null
                || titleText.equals(""))
        {
            return;
        }

        title_Label.setText(titleText);
    }

    /**
     * Возвращает текст, указанный в названии уведомления.
     */
    public String getNotificationTitle()
    {
        return title_Label.getText();
    }

    /**
     * Задает текст уведомления.
     * В случае если передаваемый текст имеет значение null или пустой,
     * название не изменяется.
     */
    public void setMessage(final String messageText)
    {
        if (messageText == null
                || messageText.equals(""))
        {
            return;
        }

        message_Label.setText(messageText);
    }


    /**
     * Возвращает текст уведомления
     */
    public String getNotificationMessage()
    {
        return message_Label.getText();
    }

    /**
     * Задает иконку изображения.
     * Устанавливает размер иконки по умолчанию = 35% от высоты уведомления.
     */
    public void setIcon(NotificationType type)
    {

        if (type == NotificationType.ERROR)
        {
            if (error_Image == null)
            {
                error_Image = new Image(Notification.class.getResourceAsStream("/Icons/Notification/error_1.png"));
            }

            if (icon_ImageView == null)
            {
                icon_ImageView = new ImageView(error_Image);
            }
            else
            {
                icon_ImageView.setImage(error_Image);
            }
        }

        if (type == NotificationType.WARNING)
        {
            if (warning_Image == null)
            {
                warning_Image = new Image(Notification.class.getResourceAsStream("/Icons/Notification/warning.png"));
            }

            if (icon_ImageView == null)
            {
                icon_ImageView = new ImageView(warning_Image);
            }
            else
            {
                icon_ImageView.setImage(warning_Image);
            }
        }

        if (type == NotificationType.INFORMATION)
        {
            if (information_Image == null)
            {
                information_Image = new Image(Notification.class.getResourceAsStream("/Icons/Notification/information_2.png"));
            }

            if (icon_ImageView == null)
            {
                icon_ImageView = new ImageView(information_Image);
            }
            else
            {
                icon_ImageView.setImage(information_Image);
            }
        }

        if (type == NotificationType.NONE)
        {
            if (icon_VBox.getChildren().size() > 0)
            {
                icon_VBox.getChildren().clear();
            }

            root.getChildren().remove(icon_VBox);

        }

        adaptiveLeftLineColor();
        icon_ImageView.setPreserveRatio(true);
        setIconSizePercent(0.35D);
        //icon_ImageView.setFitWidth(64.0D);
    }


    /**
     * Указывает иконку уведомления, размер иконки при этом используется
     * по умолчанию = 35% от высоты уведомления.
     * Если же объект ссылается на значение null, метод возвращает управление.
     */
    public void setIcon(InputStream imageInputStream)
    {
        if (imageInputStream == null)
        {
            return;
        }

        icon_ImageView.setImage(new Image(imageInputStream));
        icon_ImageView.setPreserveRatio(true);
        setIconSizePercent(0.35D);
    }

    /**
     * Указывает размер иконки относительно высоты уведомления в процентах.
     * Если же объект ссылается на значение null, метод возвращает управление.
     * При этом размер иконки не может превышать 60% от высоты уведомления.
     *
     * @param newSize_ratioToHeight Значение размера в процентах по отношению к высоте
     *                              уведомления.
     */
    public void setIconSizePercent(final double newSize_ratioToHeight) throws IllegalArgumentException
    {
        if (newSize_ratioToHeight < 0.05D
                || newSize_ratioToHeight > 0.65D)
        {
            throw new IllegalArgumentException("Размер иконки не должен быть меньше 5% или превышать 65% высоты уведомления.\n" +
                    "Желаемый размер: " + newSize_ratioToHeight + " (" + (newSize_ratioToHeight * preferred_height) + ")");
        }

        if (icon_ImageView != null)
        {
            icon_ImageView.setFitWidth(preferred_height * newSize_ratioToHeight);
        }
    }


    /**
     * Задает время, в течении которого будет отображаться уведомление.
     */
    public void setDisplayDuration(Duration duration)
    {
        if (duration == null)
        {
            return;
        }
        displayDuration = duration;
        display_Timeline = new Timeline(new KeyFrame(displayDuration, event ->
        {
            disappear_Animation.play();
        }));
    }

    /**
     * Возвращает время, установленное для отображения уведомления
     */
    public Duration getDisplayDuration()
    {
        return displayDuration;
    }


    /**
     * Позволяет установить границу для корневого узла
     */
    public void setBorder(Border newBorder)
    {
        if (newBorder == null)
        {
            return;
        }
        root.setBorder(newBorder);
    }


    /**
     * Устанавливает анимацию исчезновения и ее длительность.
     *
     * @param type     Анимация, которая будет применена.
     * @param duration Длительность воспроизведения анимации
     */
    public void setDisappearanceAnimation(AnimationType type, Duration duration)
    {
        if (type == null)
        {
            return;
        }

        if (type.equals(AnimationType.FADE))
        {
            FadeTransition fade = new FadeTransition(duration, root);
            fade.setFromValue(1.0D);
            fade.setToValue(0.02D);
            fade.setOnFinished(event ->
            {
                stage.hide();
            });
            disappear_Animation = fade;
            disappearanceAnimationType = AnimationType.FADE;
        }
    }


    /**
     * Возвращает текущий тип анимации исчезновения.
     */
    public AnimationType getDisappearanceAnimationType()
    {
        return disappearanceAnimationType;
    }

    /**
     * Позволяет указать новое значение продолжительности анимации исчезновения,
     * посредством создания анимации того же типа заново, но уже с другой
     * продолжительностью.
     */
    public void setDisappearanceDuration(Duration newDisappearanceDuration)
    {
        if (newDisappearanceDuration == null)
        {
            return;
        }

        setDisappearanceAnimation(getDisappearanceAnimationType(), newDisappearanceDuration);
    }

    /**
     * Возвращает длительность воспроизведения операции исчезновения уведомления.
     */
    public Duration getDisappearanceDuration()
    {
        return disappear_Animation.getTotalDuration();
    }

    /**
     * Позволяет задать ширину уведомления в процентах от ширины экрана
     */
    public void setWidthPercent(final double newWidthToScreen_ratio)
    {
        if (newWidthToScreen_ratio < 0.1D
                || newWidthToScreen_ratio > 0.5D)
        {
            throw new IllegalArgumentException("Ширина уведомление не может составлять менее 10 % или" +
                    "не более 50% от ширины экрана.");
        }
        preferred_width = Screen.getPrimary().getBounds().getWidth() * newWidthToScreen_ratio;
        stage.setWidth(preferred_width);
        leftLine_VBox.setMinWidth(preferred_width * widthOfLeftVerticalLine);
        leftLine_VBox.setPrefWidth(preferred_width * widthOfLeftVerticalLine);
        x = Screen.getPrimary().getBounds().getWidth() - preferred_width - (Screen.getPrimary().getBounds().getWidth() * spaceXGap_ratio);
        y = Screen.getPrimary().getBounds().getHeight() - preferred_height - (Screen.getPrimary().getBounds().getHeight() * spaceYGap_ratio);

        stage.setX(x);
        stage.setY(y);
    }

    /**
     * Позволяет задать ширину уведомления в процентах от ширины экрана
     */
    public void setHeightPercent(final double newHeightToScreen_ratio)
    {
        if (newHeightToScreen_ratio < 0.1D
                || newHeightToScreen_ratio > 0.5D)
        {
            throw new IllegalArgumentException("Высота уведомление не может составлять менее 10 % или" +
                    "не более 50% от высоты экрана.");
        }
        preferred_height = Screen.getPrimary().getBounds().getHeight() * newHeightToScreen_ratio;
        stage.setHeight(preferred_height);
        x = Screen.getPrimary().getBounds().getWidth() - preferred_width - (Screen.getPrimary().getBounds().getWidth() * spaceXGap_ratio);
        y = Screen.getPrimary().getBounds().getHeight() - preferred_height - (Screen.getPrimary().getBounds().getHeight() * spaceYGap_ratio);
        stage.setX(x);
        stage.setY(y);
    }

    /**
     * Подбирает цвет левой линии под цвет иконки
     */
    private void adaptiveLeftLineColor()
    {
        if (leftLineColorSameAsIcon && icon_ImageView != null
                && icon_ImageView.getImage() != null)
        {
            leftLine_VBox.setBackground(new Background(
                    new BackgroundFill(icon_ImageView.getImage().getPixelReader().getColor(
                            (int) (icon_ImageView.getImage().getWidth() * 0.25D), (int) (icon_ImageView.getImage().getHeight() * 0.25D)),
                            CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    /**
     * Отображает уведомление
     */
    public void showNotification()
    {
        stage.show();
        display_Timeline.play();
    }

    /**
     * Отображает уведомление и ожидает пока оно не будет закрыто.
     * Разумеется, что при этом уведомление не будет скрыто автоматически.
     */
    public void showAndWaitNotification()
    {
        stage.showAndWait();
    }

    /**Скрывает уведомление, останавливая при этом обьект. что отсчитывает
     * время до исчезания уведомления, а также саму анимацию исчезания.*/
    public void hideNotification()
    {
        display_Timeline.stop();
        disappear_Animation.stop();
        stage.hide();
    }

}
