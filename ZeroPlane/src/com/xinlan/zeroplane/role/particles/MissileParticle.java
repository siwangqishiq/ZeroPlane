package com.xinlan.zeroplane.role.particles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.xinlan.zeroplane.role.PlayerMissile;

public class MissileParticle {
	public static final int PARTICLE_NUM = 32;
	private PlayerMissile missile;
	private Particle[] particles = new Particle[PARTICLE_NUM];
	private Paint paint;
	private int particlesIndex = 0;

	public MissileParticle(PlayerMissile missile) {
		this.missile = missile;
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle();
		}// end for i
		paint = new Paint();
		paint.setColor(Color.WHITE);
	}

	public void logic() {
		float gen_x = missile.x + missile.width / 2-3;
		float gen_y = missile.y + missile.height ;
		particlesIndex = (particlesIndex + 1) % PARTICLE_NUM;
		particles[particlesIndex].set(gen_x, gen_y);
		particlesIndex = (particlesIndex + 1) % PARTICLE_NUM;
		particles[particlesIndex].set(gen_x + 1, gen_y);
		particlesIndex = (particlesIndex + 1) % PARTICLE_NUM;
		particles[particlesIndex].set(gen_x + 2, gen_y);
		particlesIndex = (particlesIndex + 1) % PARTICLE_NUM;
		particles[particlesIndex].set(gen_x + 3, gen_y);
		particlesIndex = (particlesIndex + 1) % PARTICLE_NUM;
		particles[particlesIndex].set(gen_x + 4, gen_y);
		particlesIndex = (particlesIndex + 1) % PARTICLE_NUM;
		particles[particlesIndex].set(gen_x + 5, gen_y);
		particlesIndex = (particlesIndex + 1) % PARTICLE_NUM;
		particles[particlesIndex].set(gen_x + 6, gen_y);
		for (int i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			p.logic();
		}// end for
	}
	
	public void draw(Canvas canvas) {
		for (int i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			if (p.isShow) {
				canvas.drawPoint(p.x, p.y, paint);
			}
		}// end for
	}
	
	
	private final class Particle {
		float x, y;
		float dx, dy;
		boolean isShow = false;
		int frame;
		
		public void set(float init_x, float init_y) {
			isShow = true;
			frame = 0;
			dx = dy = 1;
			x = init_x;
			y = init_y;
		}

		public void logic() {
			if (!isShow) {
				return;
			}
			dy += Math.random();
			if (Math.random() > 0.5f) {
				x +=  Math.random();
			} else {
				x -= Math.random();
			}
			y += dy;
			frame++;
			if (frame > 100) {
				isShow = false;
			}
		}
	} // end inner class
}//end class
