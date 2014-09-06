package com.xinlan.zeroplane.role;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.role.enemy.MissileGroup;
import com.xinlan.zeroplane.view.MainView;

/**
 * 管理敌军类
 * 
 * @author Panyi
 * 
 */
public class EnemyGen {
	public static final int WAIT_DELTA = 200;
	public static final int STATUS_WAIT = 1;
	public static final int STATUS_MISSILEATTACK = 2;

	public MainView context;
	public int distance;

	public int status = STATUS_WAIT;
	
	public MissileGroup missileGroup;

	public EnemyGen(MainView context) {
		this.context = context;
		initEnemies();
	}

	public void initEnemies() {
		missileGroup = new MissileGroup(this);
	}

	public void logic() {
		switch (status) {
		case STATUS_WAIT:
			initLogic();
			break;
		case STATUS_MISSILEATTACK:
			missileAttackLogic();
			break;
		}// end switch
	}

	public void draw(Canvas canvas) {
		switch (status) {
		case STATUS_WAIT:
			break;
		case STATUS_MISSILEATTACK:
			missileGroup.draw(canvas);
			break;
		}// end switch
	}

	private void initLogic() {
		distance++;
		if (distance > WAIT_DELTA) {
			status = STATUS_MISSILEATTACK;
		}
	}

	private void missileAttackLogic() {
		missileGroup.logic();
	}
}// end class
