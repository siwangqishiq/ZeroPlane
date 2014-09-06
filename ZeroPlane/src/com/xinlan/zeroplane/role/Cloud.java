package com.xinlan.zeroplane.role;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.utils.Common;
import com.xinlan.zeroplane.view.MainView;

public class Cloud {
	public static final float max_speed = 2.0f;
	public static final float min_speed = 1.0f;
	
	public static final float max_pos= 10.0f;
	public static final float min_pos = -10.0f;
	private MainView context;
	private Bitmap mBitmap;
	private int width, height;
	private float x, y;
	private Rect src;
	private RectF dst;
	private float speed;
	private float initPos;

	public Cloud(MainView context, float init_x, float init_y) {
		this.context = context;
		initPos=x = init_x;
		y = init_y;
		speed = Common.genRand(min_speed,max_speed);
		mBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.cloud);
		width = mBitmap.getWidth();
		height = mBitmap.getHeight();
		src = new Rect(0, 0, width, height);
		dst = new RectF(x, y, x + width, y + height);
	}

	public void logic() {
		y+=speed;
		if(y>MainView.screenH){
			y = -height;
			speed = Common.genRand(min_speed,max_speed);
			x = initPos+Common.genRand(min_pos,max_pos);
		}
		dst.set(x, y, x + width, y + height);
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(mBitmap, src, dst, null);
	}
}
