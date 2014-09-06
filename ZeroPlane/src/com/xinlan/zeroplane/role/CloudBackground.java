package com.xinlan.zeroplane.role;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.xinlan.zeroplane.view.MainView;

public class CloudBackground {
	private MainView context;

	public Cloud cloud1, cloud2, cloud3;
	public Island land1, land2;
	
	public CloudBackground(MainView context) {
		this.context = context;//ssss
		land1 = new Island(context, 0, MainView.screenH);
		land2 = new Island(context, MainView.screenW - 80, MainView.screenH / 2);
		cloud1 = new Cloud(context, 10, MainView.screenH / 3);
		cloud2 = new Cloud(context, MainView.screenW / 2 + 10, 10);
		cloud3 = new Cloud(context, MainView.screenW - 90,
				MainView.screenH / 2 + 100);
	}

	public void logic() {
		land1.logic();
		land2.logic();
		cloud1.logic();
		cloud2.logic();
		cloud3.logic();
	}

	public void draw(Canvas canvas) {
		land1.draw(canvas);
		land2.draw(canvas);
		cloud1.draw(canvas);
		cloud2.draw(canvas);
		cloud3.draw(canvas);
	}
}// end class
