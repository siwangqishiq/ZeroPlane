package com.xinlan.zeroplane.utils;

public class MathUtils {
	
	
	/**
	 * 判断指定点是否在圆内
	 * 
	 * @param x
	 * @param y
	 * @param centerX
	 * @param centerY
	 * @param radius
	 * @return
	 */
	public static boolean isInCircle(int x, int y, int centerX, int centerY,
			int radius) {
		int temp_x = x - centerX;
		int temp_y = y - centerY;
		if (temp_x * temp_x + temp_y * temp_y < radius * radius) {
			return true;
		}
		return false;
	}

	public static boolean isInCircle(float x, float y, float centerX,
			float centerY, float barRadius) {
		float temp_x = x - centerX;
		float temp_y = y - centerY;
		if (temp_x * temp_x + temp_y * temp_y < barRadius * barRadius) {
			return true;
		}
		return false;
	}

	public static boolean isInRect(float x, float y, float rectLeft,
			float rectTop, float width, float height) {
		if (x > rectLeft && x < rectLeft + width && y > rectTop
				&& y < rectTop + height) {
			return true;
		}
		return false;
	}
}// end class
