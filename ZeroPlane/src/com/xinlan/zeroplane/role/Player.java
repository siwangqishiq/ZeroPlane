package com.xinlan.zeroplane.role;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.view.MainView;

public class Player {
	public static final byte STATUS_NORMAL = 1;
	public static final byte STATUS_LEFT = 2;
	public static final byte STATUS_RIGHT = 3;
	public static final byte STATUS_UP = 4;
	public static final byte STATUS_DOWN = 5;
	public static final byte STATUS_LEFT_UP = 6;
	public static final byte STATUS_RIGHT_UP = 7;
	public static final byte STATUS_LEFT_DOWN = 8;
	public static final byte STATUS_RIGHT_DOWN = 9;

	public float x, y, dx = 3.0f, dy = 3.0f;
	public int width, height;

	private int frameNums = 3;
	private Bitmap mBitmap;
	public MainView context;
	private Rect src;
	private Rect leftSrc, rightSrc;
	private RectF dst;
	private int bound_x;
	private int bound_y;

	public int planeStatus = STATUS_NORMAL;
	private Bitmap bulletBitmap;
	public PlayerGun gun;
	//public ParticleCloud particleCloud;

	public Player(MainView context) {
		this.context = context;
		mBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.plane);
		bulletBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.zero_bullet);
		width = mBitmap.getWidth() / frameNums;
		height = mBitmap.getHeight();
		src = new Rect(0, 0, width, height);
		rightSrc = new Rect(width, 0, width + width, height);
		leftSrc = new Rect(2 * width, 0, 2 * width + width, height);
		y = MainView.screenH / 2 + height * 3;
		x = (MainView.screenW - width) / 2;
		dst = new RectF(x, y, x + width, y + height);
		bound_x = MainView.screenW - width;
		bound_y = MainView.screenH - height;
		
		gun = new PlayerGun(this, bulletBitmap);//战机火控系统
		//particleCloud = new ParticleCloud(this);
	}

	public void logic() {
		switch (planeStatus) {
		case STATUS_NORMAL:
			break;
		case STATUS_LEFT:
			x -= dx;
			break;
		case STATUS_RIGHT:
			x += dx;
			break;
		case STATUS_UP:
			y -= dy;
			break;
		case STATUS_DOWN:
			y += dy;
			break;
		case STATUS_LEFT_UP:
			x -= dx;
			y -= dy;
			break;
		case STATUS_LEFT_DOWN:
			x -= dx;
			y += dy;
			break;
		case STATUS_RIGHT_UP:
			x += dx;
			y -= dy;
			break;
		case STATUS_RIGHT_DOWN:
			x += dx;
			y += dy;
			break;
		}// end switch
		if (x < 0) {
			x = 0;
		} else if (x > bound_x) {
			x = bound_x;
		}

		if (y < 0) {
			y = 0;
		} else if (y > bound_y) {
			y = bound_y;
		}

		dst.set(x, y, x + width, y + height);
		gun.logic();
	}

	public void gotoUp() {
		planeStatus = STATUS_UP;
	}

	public void gotoDown() {
		planeStatus = STATUS_DOWN;
	}

	/**
	 * 往右飞
	 */
	public void gotoRight() {
		planeStatus = STATUS_RIGHT;
	}

	public void gotoLeft() {
		planeStatus = STATUS_LEFT;
	}

	public void gotoLeftUp() {
		planeStatus = STATUS_LEFT_UP;
	}

	public void gotoLeftDown() {
		planeStatus = STATUS_LEFT_DOWN;
	}

	public void gotoRightUp() {
		planeStatus = STATUS_RIGHT_UP;
	}

	public void gotoRightDown() {
		planeStatus = STATUS_RIGHT_DOWN;
	}

	public void reset() {
		planeStatus = STATUS_NORMAL;
	}
	
	public void pressBulletFire(){
		gun.fire();
	}
	
	public void pressMissileFire(){
		gun.fireMissile();
	}

	public void draw(Canvas canvas) {
		gun.draw(canvas);
		
		switch (planeStatus) {
		case STATUS_NORMAL:
			canvas.drawBitmap(mBitmap, src, dst, null);
			break;
		case STATUS_LEFT:
			canvas.drawBitmap(mBitmap, leftSrc, dst, null);
			break;
		case STATUS_RIGHT:
			canvas.drawBitmap(mBitmap, rightSrc, dst, null);
			break;
		case STATUS_LEFT_UP:
			canvas.drawBitmap(mBitmap, leftSrc, dst, null);
			break;
		case STATUS_LEFT_DOWN:
			canvas.drawBitmap(mBitmap, leftSrc, dst, null);
			break;
		case STATUS_RIGHT_UP:
			canvas.drawBitmap(mBitmap, rightSrc, dst, null);
			break;
		case STATUS_RIGHT_DOWN:
			canvas.drawBitmap(mBitmap, rightSrc, dst, null);
			break;
		default:
			canvas.drawBitmap(mBitmap, src, dst, null);
			break;
		}// end switch
	}
}// end class
