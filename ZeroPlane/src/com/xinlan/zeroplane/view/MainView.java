package com.xinlan.zeroplane.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.xinlan.zeroplane.role.BgmPlayer;
import com.xinlan.zeroplane.role.CloudBackground;
import com.xinlan.zeroplane.role.Controller;
import com.xinlan.zeroplane.role.EnemyGen;
import com.xinlan.zeroplane.role.Player;
import com.xinlan.zeroplane.role.SoundPlayer;

public class MainView extends SurfaceView implements Callback, Runnable {
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	private Context context;
	public static int screenW, screenH;
	private Resources res = this.getResources();

	private long pertime = 0;
	private int fps = 0;
	public int loadprogress = 0;

	public static final int LOADING = 1;
	public static final int RUN = 2;

	public int GAME_STATE = LOADING;
	public int REFRESH_COLOR = Color.rgb(113, 175, 224);
	public CloudBackground mBackground;
	public Player mPlayer;
	public EnemyGen mEnemyGen;
	public Controller mController;
	public BgmPlayer mBgmPlayer;//播放背景音乐
	public SoundPlayer mSoundPlayer;//音效播放

	public MainView(Context context) {
		super(context);
		this.context = context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		sfh.setType(SurfaceHolder.SURFACE_TYPE_HARDWARE);
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		this.setKeepScreenOn(true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		flag = true;
		th = new Thread(this);
		th.start();
		// init();
		new LoadThread().start();
	}

	/**
	 */
	public void init(MainView context) {
		loadprogress = 0;
		mBackground = new CloudBackground(context);
		mController = new Controller(context);
		mPlayer = new Player(context);
		mEnemyGen = new EnemyGen(context);
		mBgmPlayer = new BgmPlayer(context);
		mSoundPlayer = new SoundPlayer(context);
		mSoundPlayer.loadRes();
		loadprogress = 100;
		
		mBgmPlayer.playBmg();
	}

	private final class LoadThread extends Thread {// 载入资源线程
		public void run() {
			init(MainView.this);
		}
	}// end inner class

	public void draw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(REFRESH_COLOR);
				mBackground.draw(canvas);
				mEnemyGen.draw(canvas);
				mPlayer.draw(canvas);
				mController.draw(canvas);
			}// end if
		} catch (Exception e) {
		} finally {
			if (canvas != null) {
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	public void logic() {
		mBackground.logic();
		mController.logic();
		mPlayer.logic();
		mEnemyGen.logic();
	}

	public void run() {
		while (flag) {
			long start = System.currentTimeMillis();
			if (start - pertime >= 1000) {
				System.out.println("fps=" + fps);
				pertime = start;
				fps = 0;
			}
			fps++;
			switch (GAME_STATE) {
			case LOADING:
				loadInit();
				break;
			case RUN:
				logic();
				draw();
				break;
			}// end switch
			long end = System.currentTimeMillis();
			try {
				if (end - start < 10) {
					Thread.sleep(10 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}// end while
	}

	private void loadInit() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				canvas.drawText("进度" + loadprogress + "%",
						MainView.screenW / 2, MainView.screenH / 2, paint);
			}// end if
		} catch (Exception e) {
		} finally {
			if (canvas != null) {
				sfh.unlockCanvasAndPost(canvas);
			}
		}
		if (loadprogress >= 100) {
			GAME_STATE = RUN;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (GAME_STATE == RUN) {
			mController.onTouch(event);
		}
		return true;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		if(mBgmPlayer!=null)
		{
		    mBgmPlayer.stopBgm();
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
}// end class
