package com.xinlan.zeroplane.role.enemy;

import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.xinlan.zeroplane.R;
import com.xinlan.zeroplane.role.EnemyGen;
import com.xinlan.zeroplane.view.MainView;

public class MissileGroup
{
    public EnemyGen parent;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;

    public static final int ONE_WAVE_NUM = 5;
    public static final int TWO_WAVE_NUM = 10;
    public static final int THREE_WAVE_NUM = 8;

    private final int oneLastIndex = ONE_WAVE_NUM - 1;
    
    public Missile[] oneWaveMissiles = new Missile[ONE_WAVE_NUM];
    public LinkedList<SmartMissile> twoWaveMissiles = new LinkedList<SmartMissile>();
    private Bitmap missileBitmap;
    public Bitmap boomBitmap;

    public int status = ONE;

    public MissileGroup(EnemyGen parent)
    {
        this.parent = parent;
        missileBitmap = BitmapFactory.decodeResource(
                parent.context.getResources(), R.drawable.missile_ennemy);
        boomBitmap = BitmapFactory.decodeResource(
                parent.context.getResources(), R.drawable.boomb);
        int each = (MainView.screenW-40)/ONE_WAVE_NUM;
        for (int i = 0; i < oneWaveMissiles.length; i++)
        {
            oneWaveMissiles[i] = new Missile(this, missileBitmap, each * i + 20,
                    -missileBitmap.getHeight());
//            oneWaveMissiles[i].curState = STATE.DEAD;
        }// end for i

        // 实例化第二波进攻
        for(int i=0;i<TWO_WAVE_NUM;i++)
        {
            twoWaveMissiles.add(new SmartMissile(this, missileBitmap, 80*i  + 15,
                    -missileBitmap.getHeight()-20-missileBitmap.getHeight()*i));
        }//end for i
        
        
    }

    public void logic()
    {
        switch (status)
        {
            case ONE:
                for (int i = 0; i < oneWaveMissiles.length; i++)
                {
                    oneWaveMissiles[i].logic();
                }// end for i
                //System.out.println(isOneWaveAllDead());
                if(isOneWaveAllDead())
                {
                    status = TWO;//跳转状态至第二波进攻
                }
                break;
            case TWO:
                //System.out.println("第二波进攻展开....");
                for (SmartMissile missile:twoWaveMissiles)
                {
                    missile.logic();
                }// end for i
                break;
            case THREE:
                break;
        }// end switch

    }

    /**
     * 第一波飞弹攻击全部结束
     * 
     * @return
     */
    private boolean isOneWaveAllDead()
    {
        for (int i = 0, size = oneWaveMissiles.length; i < size; i++)
        {
            if (oneWaveMissiles[i].curState != Missile.STATE.DEAD)
            {
                return false;
            }
        }// end for i
        return true;
    }

    public void draw(Canvas canvas)
    {
        switch (status)
        {
            case ONE:
                for (int i = 0; i < oneWaveMissiles.length; i++)
                {
                    oneWaveMissiles[i].draw(canvas);
                }// end for i
                break;
            case TWO:
                for (SmartMissile missile:twoWaveMissiles)
                {
                    missile.draw(canvas);
                }// end for i
                break;
            case THREE:
                break;
        }// end switch
    }
}// end class
