package com.xinlan.zeroplane.role;

import java.util.HashMap;

import android.media.AudioManager;
import android.media.SoundPool;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.view.MainView;

/**
 * 音效播放器
 * @author panyi
 *
 */
public class SoundPlayer {
	private MainView mContext;
	private SoundPool soundPool;
	private HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
	
	public SoundPlayer(MainView context)
	{
		this.mContext = context;
		soundPool = new SoundPool(6,AudioManager.STREAM_MUSIC,0);
	}
	
	/**
	 * 载入音效资源
	 */
	public void loadRes()
	{
		this.addSound(R.raw.gun_sound);
		this.addSound(R.raw.explode_sound);//爆炸音效
		this.addSound(R.raw.missile_fire_sound);//导弹发射音效
	}
	
	/**
	 * 载入一个音效
	 * @param resId
	 */
	public void addSound(int resId) {
		int index = soundPool.load(mContext.getContext(), resId, 0);
		map.put(resId, index);
	}

	/**
	 * 播放音效
	 * @param resId
	 */
	public void playSound(int resId) {
		int streamID = map.get(resId);
		soundPool.stop(streamID);
		soundPool.play(streamID, 1, 1, 0, 0, 1);
	}
	
}//end class
