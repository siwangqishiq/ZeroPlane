package com.xinlan.zeroplane.role;

import java.util.HashMap;

import android.media.AudioManager;
import android.media.SoundPool;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.view.MainView;

/**
 * ��Ч������
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
	 * ������Ч��Դ
	 */
	public void loadRes()
	{
		this.addSound(R.raw.gun_sound);
		this.addSound(R.raw.explode_sound);//��ը��Ч
		this.addSound(R.raw.missile_fire_sound);//����������Ч
	}
	
	/**
	 * ����һ����Ч
	 * @param resId
	 */
	public void addSound(int resId) {
		int index = soundPool.load(mContext.getContext(), resId, 0);
		map.put(resId, index);
	}

	/**
	 * ������Ч
	 * @param resId
	 */
	public void playSound(int resId) {
		int streamID = map.get(resId);
		soundPool.stop(streamID);
		soundPool.play(streamID, 1, 1, 0, 0, 1);
	}
	
}//end class
