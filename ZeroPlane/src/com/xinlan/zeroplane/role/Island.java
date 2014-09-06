package com.xinlan.zeroplane.role;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.view.MainView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Island {
	public static final float speed = 0.3f;
	private MainView context;
	private float x, y, dx, dy;
	private int width, height;
	private Bitmap mBitmap;
	private Rect src;
	private RectF dst;

	public Island(MainView context, int init_x, int init_y) {
		this.context = context;
		x = init_x;
		y = init_y;
		mBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.island);
		width = mBitmap.getWidth();
		height = mBitmap.getHeight();
		src = new Rect(0, 0, width, height);
		dst = new RectF(x, y, x + width, y + height);
	}

	/**
	 * Ìá½»Âß¼­
	 */
	public void logic() {
		y += speed;
		if (y > MainView.screenH) {
			y = -height;
		}
		dst.set(x, y, x + width, y + height);
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(mBitmap, src, dst, null);
	}
}// end class
