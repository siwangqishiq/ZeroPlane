package com.xinlan.zeroplane.role;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.utils.MathUtils;
import com.xinlan.zeroplane.view.MainView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

public class Controller {
	public static final int TOUCH_NONE = 0;// û�нӴ�
	public static final int ONLY_DIRECTION = 1;// �����������
	public static final int ONLY_BUTTON = 2;// ����ť��
	public static final int BOTH_DIRECTION_BOTTON = 3;// ���򼰰�ť��

	public static int status = TOUCH_NONE;

	private MainView context;

	private int padLeft = 20;
	private int padTop = 30;

	private boolean isBarFocus = false;
	private float down_x, down_y;

	private Bitmap mBottomBmp, mBarBmp;
	private float bottom_x, bottom_y, bottom_width, bottom_height;
	private float bar_x, bar_y, bar_width, bar_height;
	private Rect srcBottomRect, srcBarRect;
	private RectF dstBottomRect, dstBarRect;
	private RectF barBoundRect;// bar���Χ
	private float barRadius, bottomRadius;
	private float middleScreen;

	public float screenMiddle;
	private boolean isAPressed = false;
	private boolean isBPressed = false;
	private int button_pad_right = 20;
	private int button_pad_bottom = 30;
	private Bitmap mButtonBmp;
	private float buttonWidth;
	private float buttonA_x, buttonA_y, buttonB_x, buttonB_y;
	private float buttonA_left, buttonA_top, buttonB_left, buttonB_top;
	private Rect srcButtonA, srcButtonB;
	private RectF dstButtonA, dstButtonB;
	private Rect srcButtonPressedA, srcButtonPressedB;
	private float buttonRadius;

	public Controller(MainView context) {
		this.context = context;
		screenMiddle = MainView.screenW / 2;
		middleScreen = MainView.screenW / 2;
		mBottomBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.controller_bottom);// ����ҡ�˵���ͼƬ
		mBarBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.controller_top);// ����ҡ��ͼƬ
		mButtonBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.cotroller_button);// ��ť
		int cube_len = MainView.screenW > MainView.screenH ? MainView.screenH
				: MainView.screenW;
		bottom_width = bottom_height = cube_len / 3;// �趨�����߶�����
		buttonWidth = bar_width = bar_height = bottom_width / 2;// ҡ�˿��
																// �������1:2�ı���
		// Դͼ�����
		srcBottomRect = new Rect(0, 0, mBottomBmp.getWidth(),
				mBottomBmp.getHeight());
		srcBarRect = new Rect(0, 0, mBarBmp.getWidth(), mBarBmp.getHeight());
		padTop = (int) (MainView.screenH-bottom_width-20);
		// Ŀ�����
		dstBottomRect = new RectF(padLeft, padTop, padLeft + bottom_width,
				padTop + bottom_height);
		bar_x = bottom_x = dstBottomRect.centerX();
		bar_y = bottom_y = dstBottomRect.centerY();
		dstBarRect = new RectF(bar_x - bar_width / 2, bar_y - bar_height / 2,
				bar_x + bar_width / 2, bar_y + bar_height / 2);
		float bar_width_pad = bar_width - 20;
		barBoundRect = new RectF(bar_x - bar_width_pad / 2, bar_y
				- bar_width_pad / 2, bar_x + bar_width_pad / 2, bar_y
				+ bar_width_pad / 2);
		barRadius = dstBarRect.width() / 2;
		bottomRadius = dstBarRect.width() / 2;

		srcButtonA = new Rect(0, 0, mButtonBmp.getWidth() / 2,
				mButtonBmp.getHeight());
		srcButtonB = new Rect(0, 0, mButtonBmp.getWidth() / 2,
				mButtonBmp.getHeight());
		srcButtonPressedA = new Rect(mButtonBmp.getWidth() / 2, 0,
				mButtonBmp.getWidth(), mButtonBmp.getHeight());
		srcButtonPressedB = new Rect(mButtonBmp.getWidth() / 2, 0,
				mButtonBmp.getWidth(), mButtonBmp.getHeight());
		buttonRadius = buttonWidth / 2;
		buttonB_x = MainView.screenW - (buttonRadius + button_pad_right);
		buttonB_y = MainView.screenH - (buttonRadius + button_pad_bottom);
		buttonB_left = buttonB_x - buttonRadius;
		buttonB_top = buttonB_y - buttonRadius;
		dstButtonB = new RectF(buttonB_left, buttonB_top, buttonB_left
				+ buttonWidth, buttonB_top + buttonWidth);
		buttonA_x = buttonB_left - buttonRadius;
		buttonA_y = buttonB_top - buttonRadius;
		buttonA_left = buttonA_x - buttonRadius;
		buttonA_top = buttonA_y - buttonRadius;
		dstButtonA = new RectF(buttonA_left, buttonA_top, buttonA_left
				+ buttonWidth, buttonA_top + buttonWidth);
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(mBottomBmp, srcBottomRect, dstBottomRect, null);
		dstBarRect.set(bar_x - bar_width / 2, bar_y - bar_height / 2, bar_x
				+ bar_width / 2, bar_y + bar_height / 2);
		canvas.drawBitmap(mBarBmp, srcBarRect, dstBarRect, null);

		if (isAPressed) {
			canvas.drawBitmap(mButtonBmp, srcButtonPressedA, dstButtonA, null);
		} else {
			canvas.drawBitmap(mButtonBmp, srcButtonA, dstButtonA, null);
		}
		if (isBPressed) {
			canvas.drawBitmap(mButtonBmp, srcButtonPressedB, dstButtonB, null);
		} else {
			canvas.drawBitmap(mButtonBmp, srcButtonB, dstButtonB, null);
		}
	}

	public void logic() {
		if (isBarFocus) {
			float delta_y = bar_y - bottom_y;
			float delta_x = bar_x - bottom_x;
			if (delta_y > -delta_x && delta_y > delta_x) {// �·���
				// sb.append("��");
				context.mPlayer.gotoDown();
			}
			if (delta_y < -(delta_x) && delta_y > delta_x) {// ����
				// sb.append("��");
				context.mPlayer.gotoLeft();
			}
			if (delta_y < -delta_x && delta_y < delta_x) {// �Ϸ���
				// sb.append("��");
				context.mPlayer.gotoUp();
			}
			if (delta_y > -delta_x && delta_y < delta_x) {// ��
				// sb.append("��");
				context.mPlayer.gotoRight();
			}
			// б�����
			if (delta_y == delta_x) {
				if (delta_x > 0) {// ����
					// sb.append("����");
					context.mPlayer.gotoRightDown();
				} else if (delta_x < 0) {// ����
					// sb.append("����");
					context.mPlayer.gotoLeftUp();
				}
			}
			if (delta_y == -delta_x) {
				if (delta_x > 0) {//
					// sb.append("����");
					context.mPlayer.gotoRightUp();
				} else if (delta_x < 0) {
					// sb.append("����");
					context.mPlayer.gotoLeftDown();
				}
			}
		}else{
			context.mPlayer.reset();
		}
		if (isAPressed) {
			// sb.append(" A��");
			context.mPlayer.pressBulletFire();
		}

		if (isBPressed) {
			// sb.append(" B��");
			context.mPlayer.pressMissileFire();
		}
		// System.out.println(sb.toString());
	}

	public void onTouch(MotionEvent event) {
		int fingerNum = event.getPointerCount();
		if (fingerNum < 2) {// ==1
			OneFinger(event);
		} else {// ==2
			MoreFingers(event);
		}
	}

	private void OneFinger(MotionEvent event) {
		float x1 = event.getX(0);
		float y1 = event.getY(0);
		int motionStatus = MotionEventCompat.getActionMasked(event);
		if (x1 < screenMiddle) {// ����
			switch (motionStatus) {
			case MotionEvent.ACTION_DOWN:
				if (MathUtils.isInCircle(x1, y1, dstBarRect.centerX(),
						dstBarRect.centerY(), barRadius)) {
					isBarFocus = true;
					down_x = x1;
					down_y = y1;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (isBarFocus) {
					bar_x = bottom_x + (x1 - down_x);
					bar_y = bottom_y + (y1 - down_y);
					if (bar_x > barBoundRect.right)
						bar_x = barBoundRect.right;
					if (bar_x < barBoundRect.left)
						bar_x = barBoundRect.left;
					if (bar_y < barBoundRect.top)
						bar_y = barBoundRect.top;
					if (bar_y > barBoundRect.bottom)
						bar_y = barBoundRect.bottom;
				}
				break;
			case MotionEvent.ACTION_UP:
				isBarFocus = false;
				bar_x = bottom_x;
				bar_y = bottom_y;
				break;
			}
		} else {// ����
			isBarFocus = false;
			bar_x = bottom_x;
			bar_y = bottom_y;
			switch (motionStatus) {
			case MotionEvent.ACTION_DOWN:
				if (dstButtonA.contains(x1, y1)) {
					isBPressed = false;
					isAPressed = true;
				}
				if (dstButtonB.contains(x1, y1)) {
					isBPressed = true;
					isAPressed = false;
				}
				break;
			case MotionEvent.ACTION_UP:
				isBPressed = false;
				isAPressed = false;
				break;
			}
		}
	}

	private void MoreFingers(MotionEvent event) {
		float x1 = event.getX(0);
		float y1 = event.getY(0);
		float x2 = event.getX(1);
		float y2 = event.getY(1);
		if (Math.abs(x1 - x2) < 100) {
			isBPressed = false;
			isAPressed = false;
			return;
		}
		int motionStatus = MotionEventCompat.getActionMasked(event);
		if (x1 < screenMiddle) {
			switch (motionStatus) {
			case MotionEvent.ACTION_MOVE:
				if (isBarFocus) {
					bar_x = bottom_x + (x1 - down_x);
					bar_y = bottom_y + (y1 - down_y);
					if (bar_x > barBoundRect.right)
						bar_x = barBoundRect.right;
					if (bar_x < barBoundRect.left)
						bar_x = barBoundRect.left;
					if (bar_y < barBoundRect.top)
						bar_y = barBoundRect.top;
					if (bar_y > barBoundRect.bottom)
						bar_y = barBoundRect.bottom;
				}
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				if (dstButtonA.contains(x2, y2)) {
					isBPressed = false;
					isAPressed = true;
				}
				if (dstButtonB.contains(x2, y2)) {
					isBPressed = true;
					isAPressed = false;
				}

				if (MathUtils.isInCircle(x2, y2, dstBarRect.centerX(),
						dstBarRect.centerY(), barRadius)) {
					isBarFocus = true;
					down_x = x2;
					down_y = y2;
				}
				break;
			case MotionEvent.ACTION_POINTER_UP:
				isBPressed = false;
				isAPressed = false;

				break;
			}// end switch
		} else {
			// x2,y2Ϊ����ٿ�
			switch (motionStatus) {
			case MotionEvent.ACTION_MOVE:
				if (isBarFocus) {
					bar_x = bottom_x + (x2 - down_x);
					bar_y = bottom_y + (y2 - down_y);
					if (bar_x > barBoundRect.right)
						bar_x = barBoundRect.right;
					if (bar_x < barBoundRect.left)
						bar_x = barBoundRect.left;
					if (bar_y < barBoundRect.top)
						bar_y = barBoundRect.top;
					if (bar_y > barBoundRect.bottom)
						bar_y = barBoundRect.bottom;
				}
				break;

			case MotionEvent.ACTION_POINTER_DOWN:
				if (dstButtonA.contains(x1, y1)) {
					isBPressed = false;
					isAPressed = true;
				}
				if (dstButtonB.contains(x1, y1)) {
					isBPressed = true;
					isAPressed = false;
				}

				if (MathUtils.isInCircle(x2, y2, dstBarRect.centerX(),
						dstBarRect.centerY(), barRadius)) {
					isBarFocus = true;
					down_x = x2;
					down_y = y2;
				}
				break;
			case MotionEvent.ACTION_POINTER_UP:
				isBPressed = false;
				isAPressed = false;
				break;
			}// end switch
		}
	}
}// end class
