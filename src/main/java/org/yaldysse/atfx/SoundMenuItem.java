package org.yaldysse.atfx;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class SoundMenuItem extends CustomMenuItem
{
    private Label name;
    private Button playStopButton;
    private ImageView play_ImageView;
    private ImageView stop_ImageView;
    private RadioButton select_RadioButton;
    private Sound sound;
    private HBox content_HBox;

    public SoundMenuItem(final Sound aSound)
    {
        super();
        sound = aSound;
        initializeComponents();
    }

    private void initializeComponents()
    {
        name = new Label();
        name.setTextFill(Color.BLACK);
        name.setText(sound.getName());

        select_RadioButton = new RadioButton(sound.getName());
        select_RadioButton.setTextFill(Color.BLACK);
        select_RadioButton.setFont(Font.font(Font.getDefault().getName(),
                FontWeight.MEDIUM,13.0D));


        playStopButton = new Button();
        playStopButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        playStopButton.setOnAction(this::playStopButton_Action);
        playStopButton.setBackground(Background.EMPTY);

        HBox button_HBox = new HBox(playStopButton);
        button_HBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(button_HBox,Priority.ALWAYS);

        content_HBox = new HBox(fxGui.rem * 0.7D, select_RadioButton, button_HBox);
        content_HBox.setAlignment(Pos.CENTER_LEFT);
        //content_HBox.setPrefWidth(fxGui.rem * 13.3D);
        //content_HBox.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox.setHgrow(name, Priority.ALWAYS);
        setContent(content_HBox);
        //sound.getMediaPlayer().setStartTime(Duration.ZERO);
        //System.out.println("totalDuratioN: " + sound.getMediaPlayer().getMedia().getDuration());
        //sound.getMediaPlayer().setStopTime(Duration.seconds(1));
        //sound.getMediaPlayer().setCycleCount(5);
        sound.getMediaPlayer().setOnEndOfMedia(() ->
        {
            playStopButton.setGraphic(play_ImageView);
////            sound.getMediaPlayer().stop();
//            sound.getMediaPlayer().seek(Duration.ZERO);
//            sound.getMediaPlayer().play();
        });
    }

    private void playStopButton_Action(ActionEvent event)
    {
        MediaPlayer mediaPlayer = sound.getMediaPlayer();
        System.out.println("playStopButton_Action");

         if (mediaPlayer != null)
        {
            System.out.println("MediaPlayer status: " + mediaPlayer.getStatus().name());
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
            {
                mediaPlayer.stop();
                playStopButton.setGraphic(play_ImageView);
            } else if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING)
            {
                mediaPlayer.play();
                playStopButton.setGraphic(stop_ImageView);
            }
        }
    }

    public void setPlay_ImageView(final ImageView aImageView)
    {
        play_ImageView = aImageView;
        playStopButton.setGraphic(play_ImageView);
    }

    public void setStop_ImageView(final ImageView aImageView)
    {
        stop_ImageView = aImageView;
    }

    public RadioButton getSelect_RadioButton()
    {
        return select_RadioButton;
    }

    public Sound getSound()
    {
        return sound;
    }

    public void setPrefWidth(final double maxWidth)
    {
        content_HBox.setPrefWidth(maxWidth);
    }

    public void setContentPreferredWidth(final double preferredWidth)
    {
        content_HBox.setPrefWidth(preferredWidth);
    }

    public void stopPlaying()
    {
        if(sound.getMediaPlayer()!=null)
        {
            sound.getMediaPlayer().stop();
            playStopButton.setGraphic(play_ImageView);
        }
    }

    public void setFont(final Font newFont)
    {
        select_RadioButton.setFont(newFont);
    }

    public void setPrefHeight(final double maxHeight)
    {
        content_HBox.setPrefHeight(maxHeight);
    }
}
