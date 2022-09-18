package com.axiom.gameengine.main;

import com.axiom.gameengine.Utils.*;
import com.axiom.gameengine.main.Scene.*;

public class Game implements Runnable{
	
	public static Scene scene;
	
	private Thread thread;
	private boolean running = false;

	public Game() {
		// TODO Auto-generated constructor stub
	}

	public void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		while (running) {
			Long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			Time.deltaTime = (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				Time.fixedDeltaTime = delta;
				fixedUpdate();
				delta--;
			}
			update();
		}
	}

	private void update() {
		scene.update();
	}
	
	private void fixedUpdate() {
		scene.fixedUpdate();
	}
	
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
