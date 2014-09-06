package com.xinlan.zeroplane.role.particles;

import com.xinlan.zeroplane.role.Player;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ParticleCloud {
	public static final int PARTICLE_NUM = 50;
	private Player player;
	private Particle[] particles = new Particle[PARTICLE_NUM];
	private Paint paint;
	private int particlesIndex = 0;

	public ParticleCloud(Player player) {
		this.player = player;
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle();
		}// end for is
		paint = new Paint();
		paint.setColor(Color.BLACK);
	}

	public void logic() {
		float gen_x = player.x + player.width / 2-3;
		float gen_y = player.y + player.height ;
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
			dy += 3*Math.random();
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
}// end class
