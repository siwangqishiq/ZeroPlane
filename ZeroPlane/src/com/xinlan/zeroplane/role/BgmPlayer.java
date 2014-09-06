package com.xinlan.zeroplane.role;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.view.MainView;

import android.media.MediaPlayer;

public class BgmPlayer
{
    private MediaPlayer mediaPlayer;// ����һ�����ֲ�����

    public BgmPlayer(MainView context)
    {
        mediaPlayer = MediaPlayer.create(context.getContext(), R.raw.flee);
        mediaPlayer.setVolume(0.6f, 0.6f);
        mediaPlayer.setLooping(true);// ѭ������
    }

    /**
     * ���ű�������
     */
    public void playBmg()
    {
        mediaPlayer.start();// ��ʼ����
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
