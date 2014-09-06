package com.xinlan.zeroplane.role;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.view.MainView;

import android.media.MediaPlayer;

public class BgmPlayer
{
    private MediaPlayer mediaPlayer;// 声明一个音乐播放器

    public BgmPlayer(MainView context)
    {
        mediaPlayer = MediaPlayer.create(context.getContext(), R.raw.flee);
        mediaPlayer.setVolume(0.6f, 0.6f);
        mediaPlayer.setLooping(true);// 循环播放
    }

    /**
     * 播放背景音乐
     */
    public void playBmg()
    {
        mediaPlayer.start();// 开始播放
    }

    public void pauseBgm()
    {
        mediaPlayer.pause();
    }

    public void stopBgm()
    {
        mediaPlayer.stop();
    }

    public void releaseBgm()
    {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }
}// end class
