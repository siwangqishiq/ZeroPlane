package com.xinlan.zeroplane.role.enemy;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.role.PlayerMissile;
import com.xinlan.zeroplane.role.Playerbullet;
import com.xinlan.zeroplane.utils.Common;
import com.xinlan.zeroplane.view.MainView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Missile {
	enum STATE {
		ALIVE, BOOMING, DEAD
	};

	public STATE curState = STATE.ALIVE;
	// public boolean isLive = true;
	public float x, y, dx, dy;
	public int width, height;
	private Bitmap mBitmap;
	private Rect src;
	private RectF dst;
	private int bound_y;

	private float init_y;
	public MissileGroup group;
	public Playerbullet[] bullets;
	public PlayerMissile[] missiles;
	private int hp = 100;
	private Bitmap boomBit;
	private int boomAnimationIndex = 0;
	private int subBoomAnimationIndex = 0;
	private static final int BOOM_MAX_FRAME = 6;
	private int boom_width;
	private int boom_height;
	private Rect boomSrcRect;
	private RectF boomDstRect;

	public Missile(MissileGroup group, Bitmap bitmap, float init_x, float init_y) {
		this.group = group;
		mBitmap = bitmap;
		boomBit = group.boomBitmap;
		x = init_x;
		this.init_y = y = init_y;
		dy = 2f;
		width = mBitmap.getWidth();
		height = mBitmap.getHeight();
		src = new Rect(0, 0, width, height);
		dst = new RectF(x, y, x + width, y + height);
		bound_y = MainView.screenH + height;
		boom_width = boomBit.getWidth() / BOOM_MAX_FRAME;
		boom_height = boomBit.getHeight();
		boomSrcRect = new Rect(0, 0, boom_width, boom_height);
		boomDstRect = new RectF(x, y, x + boom_width, y + boom_height);
	}

	public void logic() {
		// System.out.println(curState);
		switch (curState) {
		case ALIVE:
			y += dy;
			dst.set(x, y, x + width, y + height);
			bullets = group.parent.context.mPlayer.gun.bullets;

			for (int i = 0; i < bullets.length; i++) {
				if (bullets[i].status == Playerbullet.SHOW) {
					if (Common.isRectFHit(dst, bullets[i].dstRect)) {
						bullets[i].status = Playerbullet.NONE;
						hp -= bullets[i].damage;
						if (hp <= 0) {
							// isLive = false;
							curState = STATE.BOOMING;// 飞船被打爆
							boomAnimationIndex = 0;
							
							this.playDeadSound();
						}
						return;
					}
				}
			}// end for i

			missiles = group.parent.context.mPlayer.gun.missiles;
			for (int i = 0; i < missiles.length; i++) {
				if (missiles[i].isShow) {
					if (Common.isRectFHit(dst, missiles[i].dstRect)) {
						missiles[i].isShow = false;
						hp -= missiles[i].damage;
						if (hp <= 0) {
							// isLive = false;
							curState = STATE.BOOMING;
							boomAnimationIndex = 0;
							
							this.playDeadSound();
						}
						return;
					}
				}
			}// end for i

			if (y > MainView.screenH) {
				y = init_y;
			}
			break;
		case BOOMING:
			// y += dy;
			if (boomAnimationIndex < BOOM_MAX_FRAME) {
				int left = boomAnimationIndex * boom_width;
				boomSrcRect.set(left, 0, left + boom_width, boom_height);
				if (subBoomAnimationIndex >= 4) {
					boomAnimationIndex++;
					subBoomAnimationIndex = 0;
				} else {
					subBoomAnimationIndex++;
				}
			} else {// >=MAX_FRAME
				this.curState = STATE.DEAD;
			}
			boomDstRect = new RectF(x, y, x + boom_width, y + boom_height);
			break;
		case DEAD:
			this.curState = STATE.DEAD;
			x = -200;
			y = -200;
			break;
		}// end switch

	}

	public void draw(Canvas canvas) {
		switch (curState) {
		case ALIVE:
			canvas.drawBitmap(mBitmap, src, dst, null);
			break;
		case BOOMING:
			canvas.drawBitmap(boomBit, boomSrcRect, boomDstRect, null);
			break;
		case DEAD:
			break;
		}// end switch
	}

	/**
	 * 播放爆炸时的音效
	 */
	protected void playDeadSound() {
		group.parent.context.mSoundPlayer.playSound(R.raw.explode_sound);
	}
}// end class
