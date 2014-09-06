package com.xinlan.zeroplane.role;

import java.util.LinkedList;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.view.MainView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * 火控系统
 * 
 * @author Panyi
 * 
 */
public class PlayerGun {
	public static final int MAX_BULLET_NUM = 30;
	public Playerbullet[] bullets = new Playerbullet[MAX_BULLET_NUM];
	public Player player;
	private Bitmap bulletBitmap;
	private Rect bulletSrcRect;

	private long preFiretime = 0;
	private int firebulletIndex = 0;

	public static final int icon_pad_right = 10;
	public static final int icon_pad_top = 15;
	public static final int icon_margin = 3;
	public static final int each_gun_panel_width = 5;
	private Bitmap gunIconBitmap;
	private int gunPanel_x, gunPanel_y;
	private int gunPanel_width, gunPanel_height;
	private int gun_bullet_max = 10;
	private int gun_curBullet_num = gun_bullet_max;
	private Rect gunIconSrc;
	private RectF gunIconDst;
	private int loadgunCount = 0;
	private Rect gunNumRect;
	private int gunRectHeight;
	private Paint gunIconPaint;

	public static final int missile_panel_pad = 5;
	public static final int LOAD_MISSILE_MAX = 100;
	private Bitmap missileIconBitmap;
	private Rect missileIconRect;
	private RectF missileIconDst;
	private int missilePanel_x, missilePanel_y;
	private float missilePanelProgress_x, missilePanelProgress_y;
	private RectF missileReloadProgress = new RectF();
	private int loadMissileProgress = LOAD_MISSILE_MAX;
	private int missilePanel_width;
	private Paint missilePanelPaint;
	public static final int MAX_MISSILE_NUM = 4;
	public PlayerMissile[] missiles = new PlayerMissile[MAX_MISSILE_NUM];
	private Bitmap missileBitmap;
	private int missileIndex = 0;

	public PlayerGun(Player player, Bitmap bitmap) {
		this.player = player;
		this.bulletBitmap = bitmap;
		gunIconBitmap = BitmapFactory.decodeResource(
				player.context.getResources(), R.drawable.gun_icon);
		bulletSrcRect = new Rect(0, 0, bulletBitmap.getWidth(),
				bulletBitmap.getHeight());
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new Playerbullet(bulletBitmap, bulletSrcRect);
		}// end for

		gunPanel_y = icon_pad_top;
		gunPanel_x = MainView.screenW - gun_bullet_max * each_gun_panel_width
				- gunIconBitmap.getWidth() - icon_margin - icon_pad_right;
		gunIconSrc = new Rect(0, 0, gunIconBitmap.getWidth(),
				gunIconBitmap.getHeight());
		gunIconDst = new RectF(gunPanel_x, gunPanel_y, gunPanel_x
				+ gunIconBitmap.getWidth(), gunPanel_y
				+ gunIconBitmap.getHeight());
		gunIconPaint = new Paint();
		gunIconPaint.setColor(Color.RED);
		gunNumRect = new Rect();
		gunNumRect.left = (int) gunIconDst.left + gunIconBitmap.getWidth()
				+ icon_margin;
		gunNumRect.top = (int) gunIconDst.top;
		gunRectHeight = gunIconBitmap.getHeight();
		gunNumRect.right = gunNumRect.left + gun_curBullet_num
				* each_gun_panel_width;
		gunNumRect.bottom = gunNumRect.top + gunRectHeight;

		missileIconBitmap = BitmapFactory.decodeResource(
				player.context.getResources(), R.drawable.missile_icon);
		missileIconRect = new Rect(0, 0, missileIconBitmap.getWidth(),
				missileIconBitmap.getHeight());
		missilePanel_x = gunPanel_x;
		missilePanel_y = (int) gunIconDst.bottom + missile_panel_pad;
		missileIconDst = new RectF(missilePanel_x, missilePanel_y,
				missilePanel_x + missileIconBitmap.getWidth(), missilePanel_y
						+ missileIconBitmap.getHeight());
		missilePanelProgress_x = missilePanel_x + missileIconDst.width()
				+ missile_panel_pad;
		missilePanelProgress_y = missilePanel_y;
		missilePanel_width = gun_curBullet_num * each_gun_panel_width;
		missileReloadProgress.set(missilePanelProgress_x,
				missilePanelProgress_y, missilePanelProgress_x
						+ (loadMissileProgress / LOAD_MISSILE_MAX)
						* missilePanel_width, missilePanelProgress_y
						+ missileIconBitmap.getHeight());
		missilePanelPaint = new Paint();
		missilePanelPaint.setColor(Color.YELLOW);
		missileBitmap = BitmapFactory.decodeResource(
				player.context.getResources(), R.drawable.missile);
		for (int i = 0; i < missiles.length; i++) {
			missiles[i] = new PlayerMissile(this, missileBitmap);
		}// end for i

	}

	public void fire() {
		if (gun_curBullet_num <= 0) {
			return;
		}
		if (System.currentTimeMillis() - preFiretime < 200) {// 控制发射频率
			return;
		}
		preFiretime = System.currentTimeMillis();
		firebulletIndex = (firebulletIndex + 1) % MAX_BULLET_NUM;
		Playerbullet bulletLeft = bullets[firebulletIndex];
		bulletLeft.status = Playerbullet.SHOW;
		bulletLeft.x = player.x + 4;
		bulletLeft.y = player.y;
		firebulletIndex = (firebulletIndex + 1) % MAX_BULLET_NUM;
		Playerbullet bulletRight = bullets[firebulletIndex];
		bulletRight.status = Playerbullet.SHOW;
		bulletRight.x = player.x + player.width - 4;
		bulletRight.y = player.y;
		
		//播放子弹发射音效
		player.context.mSoundPlayer.playSound(R.raw.gun_sound);

		gun_curBullet_num--;
	}

	public void fireMissile() {
		if (loadMissileProgress >= LOAD_MISSILE_MAX) {
			loadMissileProgress = 0;
			missileIndex = (missileIndex + 1) % MAX_MISSILE_NUM;
			PlayerMissile missile = missiles[missileIndex];
			missile.setFireMissile(player.x + player.width / 2 - missile.width
					/ 2, player.y);
			player.context.mSoundPlayer.playSound(R.raw.missile_fire_sound);//播放导弹发射音效
		}
	}

	public void logic() {
		for (int i = 0; i < bullets.length; i++) {
			Playerbullet bullet = bullets[i];
			if (bullet.status == Playerbullet.NONE)
				continue;
			bullet.logic();
		}// end for i

		for (int i = 0; i < MAX_MISSILE_NUM; i++) {
			missiles[i].logic();
		}

		if ((loadgunCount++) % 40 == 0) {
			gun_curBullet_num++;
			if (gun_curBullet_num > gun_bullet_max) {
				gun_curBullet_num = gun_bullet_max;
			}
		}// end if

		if (loadMissileProgress < LOAD_MISSILE_MAX) {
			loadMissileProgress++;
		}

		gunNumRect.right = gunNumRect.left + gun_curBullet_num
				* each_gun_panel_width;

		missileReloadProgress.set(missilePanelProgress_x,
				missilePanelProgress_y, missilePanelProgress_x
						+ ((float) loadMissileProgress / LOAD_MISSILE_MAX)
						* missilePanel_width, missilePanelProgress_y
						+ missileIconBitmap.getHeight());
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(gunIconBitmap, gunIconSrc, gunIconDst, null);
		canvas.drawRect(gunNumRect, gunIconPaint);

		canvas.drawBitmap(missileIconBitmap, missileIconRect, missileIconDst,
				null);
		canvas.drawRect(missileReloadProgress, missilePanelPaint);

		for (int i = 0; i < bullets.length; i++) {
			Playerbullet bullet = bullets[i];
			bullet.draw(canvas);
		}// end for i

		for (int i = 0; i < MAX_MISSILE_NUM; i++) {
			missiles[i].draw(canvas);
		}
	}
}// end class
