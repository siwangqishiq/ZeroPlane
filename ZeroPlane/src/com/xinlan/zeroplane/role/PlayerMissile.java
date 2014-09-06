package com.xinlan.zeroplane.role;

import com.xinlan.zeroplane.role.particles.MissileParticle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class PlayerMissile {
	public static final float DEFAULT_X = -100f;
	public static final float DEFAULT_Y = -100f;
	public static final float INIT_SPEED=1f;
	public static final float da = 0.15f;

	public boolean isShow = false;
	private PlayerGun playerGun;
	private Bitmap bitmap;
	public float x, y, dx, dy;
	public int width, height;
	private Rect srcRect;
	public RectF dstRect;
	public int damage=100;
	private MissileParticle particle;

	public PlayerMissile(PlayerGun playerGun, Bitmap bitmap) {
		this.playerGun = playerGun;
		this.bitmap = bitmap;
		dy=INIT_SPEED;
		x = DEFAULT_X;
		y = DEFAULT_Y;
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		srcRect = new Rect(0, 0, width, height);
		dstRect = new RectF();
		particle = new MissileParticle(this);
	}

	public void setFireMissile(float init_x, float init_y) {
		x = init_x;
		y = init_y;
		dy = INIT_SPEED;
		isShow = true;
	}

	public void logic() {
		if (isShow) {
			dy += da;
			y -= dy;
			dstRect.set(x, y, x + width, y + height);
			if (y < -height) {
				isShow = false;
				x = DEFAULT_X;
				y = DEFAULT_Y;
			}
			particle.logic();
		}// end if
	}

	public void draw(Canvas canvas) {
		if (!isShow) {
			return;
		}
		canvas.drawBitmap(bitmap, srcRect, dstRect, null);
		particle.draw(canvas);
	}
}// end class
