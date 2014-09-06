package com.xinlan.zeroplane.role;

import com.xinlan.zeroplane.role.particles.BulletParticle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Playerbullet {
	public static final int SHOW = 1;
	public static final int NONE = 0;

	public static final int hide_x = -500;
	public static final int hide_y = -500;

	public float x, y;
	public int width, height;
	private float dy = 5.0f;
	private Rect srcRect;
	public RectF dstRect;
	public int status = NONE;
	private int bound_y;
	private Bitmap bitmap;
	public int damage=20;
	private BulletParticle particles;

	public Playerbullet(Bitmap bitmap, Rect srcRect) {
		x = hide_x;
		y = hide_y;
		this.bitmap = bitmap;
		this.srcRect = srcRect;
		bound_y = -height;
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		dstRect = new RectF(x, y, x + width, y + height);
		particles = new BulletParticle(this);
	}

	public void draw(Canvas canvas) {
		if (status == SHOW){
			canvas.drawBitmap(bitmap, srcRect, dstRect, null);
			particles.draw(canvas);
		}
	}

	public void logic() {
		y -= dy;
		if (y < bound_y) {
			status = NONE;
			x = hide_x;
			y = hide_y;
			return;
		}
		dstRect.set(x, y, x + width, y + height);
		particles.logic();
	}
}// end class
