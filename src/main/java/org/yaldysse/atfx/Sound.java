package org.yaldysse.atfx;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

import java.io.Serializable;

public class Sound implements Serializable
{
    private String name;
    private String path;
    private long size;
    private MediaPlayer mediaPlayer;
    //private AudioClip audioClip;
    private boolean internal;

    public Sound(final String aSoundName, final long aSize,
                 final String aPath, MediaPlayer aMediaPlayer, final boolean aInternal)
    {
        name = aSoundName;
        size = aSize;
        path = aPath;
        mediaPlayer = aMediaPlayer;
        internal = aInternal;
    }

//    public Sound(final String aSoundName, final long aSize,
//                 final String aPath, final AudioClip aAudioClip, final boolean aInternal)
//    {
//        name = aSoundName;
//        size = aSize;
//        path = aPath;
//        audioClip = aAudioClip;
//        internal = aInternal;
//    }

    public String getName()
    {
        return name;
    }

    public String getPath()
    {
        return path;
    }

    public long getSize()
    {
        return size;
    }

    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }

//    //public AudioClip getAudioClip()
//    {
//        return audioClip;
//    }

    public boolean isInternal()
    {
        return internal;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
    }

//    public void setAudioClip(AudioClip audioClip)
//    {
//        this.audioClip = audioClip;
//    }
}
