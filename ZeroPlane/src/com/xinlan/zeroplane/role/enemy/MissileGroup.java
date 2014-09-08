package com.xinlan.zeroplane.role.enemy;

import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.role.EnemyGen;
import com.xinlan.zeroplane.view.MainView;

public class MissileGroup {
	public EnemyGen parent;
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;

	public static final int ONE_WAVE_NUM = 5;
	public static final int TWO_WAVE_NUM = 10;
	public static final int THREE_WAVE_NUM = 8;

	private final int oneLastIndex = ONE_WAVE_NUM - 1;

	public Missile[] oneWaveMissiles = new Missile[ONE_WAVE_NUM];
	public LinkedList<SmartMissile> twoWaveMissiles = new LinkedList<SmartMissile>();
	public LinkedList<ThridMissile> thridWaveMissile = new LinkedList<ThridMissile>();// 第三波攻击对象

	private Bitmap missileBitmap;
	public Bitmap boomBitmap;

	public int status = ONE;

	public MissileGroup(EnemyGen parent) {
		this.parent = parent;
		missileBitmap = BitmapFactory.decodeResource(
				parent.context.getResources(), R.drawable.missile_ennemy);
		boomBitmap = BitmapFactory.decodeResource(
				parent.context.getResources(), R.drawable.boomb);
		int each = (MainView.screenW - 40) / ONE_WAVE_NUM;
		for (int i = 0; i < oneWaveMissiles.length; i++) {
			oneWaveMissiles[i] = new Missile(this, missileBitmap,
					each * i + 20, -missileBitmap.getHeight());
			// oneWaveMissiles[i].curState = STATE.DEAD;
		}// end for i

		// 实例化第二波进攻
		for (int i = 0; i < TWO_WAVE_NUM; i++) {
			twoWaveMissiles.add(new SmartMissile(this, missileBitmap,
					missileBitmap.getWidth() + 2 * i, -missileBitmap
							.getHeight() - 20 - missileBitmap.getHeight() * i));
		}// end for i

		// 第三波
		for (int i = 0; i < THREE_WAVE_NUM; i++) {
			thridWaveMissile
					.add(new ThridMissile(this, missileBitmap, MainView.screenW
							/ 2 - missileBitmap.getWidth() / 2+this.getXOffset(i), -missileBitmap
							.getHeight() - missileBitmap.getHeight() * i-20*i));
		}// end for i
	}
	
	private float getXOffset(int index)
	{
		float expect = index * 20;
//		if(expect >MainView.screenW - missileBitmap.getWidth()-30)
//		{
//			expect = expect -missileBitmap.getWidth()-30;
//		}
		return expect;
	}

	public void logic() {
		switch (status) {
		case ONE:
			for (int i = 0; i < oneWaveMissiles.length; i++) {
				oneWaveMissiles[i].logic();
			}// end for i
				// System.out.println(isOneWaveAllDead());
			if (isOneWaveAllDead()) {
				status = TWO;// 跳转状态至第二波进攻
			}
			break;
		case TWO:
			// System.out.println("第二波进攻展开....");
			for (SmartMissile missile : twoWaveMissiles) {
				missile.logic();
			}// end for i
			if (isTwoWaveAllDead()) {
				status = THREE;
			}
			break;
		case THREE:
			for (ThridMissile missile : thridWaveMissile) {
				missile.logic();
			}// end for i
			if (isThridWaveDead()) {
				status = FOUR;
			}
			break;
		case FOUR:
			// System.out.println("Boss coming!");
			break;
		}// end switch

	}

	/**
	 * 第一波飞弹攻击全部结束
	 * 
	 * @return
	 */
	private boolean isOneWaveAllDead() {
		for (int i = 0, size = oneWaveMissiles.length; i < size; i++) {
			if (oneWaveMissiles[i].curState != Missile.STATE.DEAD) {
				return false;
			}
		}// end for i
		return true;
	}

	private boolean isTwoWaveAllDead() {
		for (int i = 0, size = twoWaveMissiles.size(); i < size; i++) {
			if (twoWaveMissiles.get(i).curState != SmartMissile.STATE.DEAD) {
				return false;
			}
		}// end for i
		return true;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isThridWaveDead() {
		for (int i = 0, size = thridWaveMissile.size(); i < size; i++) {
			if (thridWaveMissile.get(i).curState != ThridMissile.STATE.DEAD) {
				return false;
			}
		}// end for i
		return true;
	}

	public void draw(Canvas canvas) {
		switch (status) {
		case ONE:
			for (int i = 0; i < oneWaveMissiles.length; i++) {
				oneWaveMissiles[i].draw(canvas);
			}// end for i
			break;
		case TWO:
			for (SmartMissile missile : twoWaveMissiles) {
				missile.draw(canvas);
			}// end for i
			break;
		case THREE:
			for (ThridMissile missile : thridWaveMissile) {
				missile.draw(canvas);
			}// end for i
			break;
		case FOUR:
			break;
		}// end switch
	}
}// end class
