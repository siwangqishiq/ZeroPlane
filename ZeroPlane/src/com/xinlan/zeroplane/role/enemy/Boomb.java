package com.xinlan.zeroplane.role.enemy;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * ±¬Õ¨Ð§¹û
 * @author Panyi
 *
 */
public class Boomb {
	public static final int FRAME_NUM=6;
	private Bitmap bitmap;
	public int width,height;
	private Rect src;
	private RectF dst;
	private int frame;
	
	public Boomb(Bitmap bitmap){
		this.bitmap = bitmap;
		height = bitmap.getHeight();
		width = bitmap.getWidth()/FRAME_NUM;
		src = new Rect(0,0,width,height);
		dst = new RectF();
	}
	
	public void showBoomb(){
		
	}
}//end class
